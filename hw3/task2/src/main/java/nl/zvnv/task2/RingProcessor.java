package nl.zvnv.task2;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class RingProcessor {
    static DecimalFormat decimalFormat = new DecimalFormat("#.###");
    private final int numNodes;
    private final int totalPackages;
    private final List<Node> nodeList;
    private final Logger logger;
    private final List<DataPackage> dataPackages;

    RingProcessor(int numNodes, int totalPackages, String logs) {
        this.numNodes = numNodes;
        this.totalPackages = totalPackages;
        this.nodeList = new ArrayList<>();
        this.dataPackages = new ArrayList<>();
        logger = Logger.getLogger("ringLogger");
        try {
            logger.addHandler(new FileHandler(logs));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        init();
    }

    private void init() {
        DataCounter dataCounter = new DataCounter(totalPackages);

        for (int i = 0; i < numNodes; ++i) {
            Node node = new Node(i, dataCounter, logger);
            nodeList.add(node);
            if (i > 0) {
                nodeList.get(i - 1).setNext(node);
            }
        }
        nodeList.get(numNodes - 1).setNext(nodeList.get(0));

        int coordId = (int) (Math.random() * numNodes);
        nodeList.get((numNodes + coordId - 1) % numNodes)
                .setNext(nodeList.get((coordId + 1) % numNodes));

        for (int i = 0; i < numNodes; ++i) {
            nodeList.get(i).setCoordinator(nodeList.get(coordId));
        }

        for (int i = 0; i < totalPackages; ++i) {
            String data = "Frame_" + i;
            int dest = (int) (Math.random() * numNodes);

            while (dest == coordId) {
                dest = (int) (Math.random() * numNodes);
            }

            DataPackage dataPackage = new DataPackage(dest, data);
            int distanceToCoord = (coordId + i) % (numNodes - 1) + 1;
            nodeList.get((coordId + distanceToCoord) % numNodes).addData(dataPackage);

            dataPackages.add(dataPackage);
        }

        logger.info(String.format("Nodes: %s; coordinator: %d", numNodes, coordId));
        for (int i = 0; i < numNodes; ++i) {
            logger.info(String.format("Node %2d has %-2d packages" , i, nodeList.get(i).getBuffer().size()));
        }
    }

    public void startProcessing() {
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < numNodes; ++i)
            service.execute(nodeList.get(i));
        service.shutdown();
    }

    public void logAvgNetworkDelay() {
        Optional<Long> delay = dataPackages.stream()
                .map(it -> (it.getEndTime() - it.getStartTime()))
                .reduce(Long::sum);

        if (delay.isPresent()) {
            String res = decimalFormat.format((double) delay.get() / (double) (totalPackages * 1_000_000));
            logger.info(String.format("Avg network delay: %15s ms", res));
        } else {
            throw new RuntimeException("Unable to count delay");
        }
    }

    public void logAvgBufferDelay() {
        Optional<Long> delay = dataPackages.stream()
                .map(DataPackage::getTotalBufferTime)
                .reduce(Long::sum);

        if (delay.isPresent()) {
            String res = decimalFormat.format((double) delay.get() / (double) (totalPackages * 1_000_000));
            logger.info(String.format("Avg buffer delay: %15s ms", res));
        } else {
            throw new RuntimeException("Unable to count delay");
        }
    }
}
