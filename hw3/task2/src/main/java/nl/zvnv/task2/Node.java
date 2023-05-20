package nl.zvnv.task2;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

public class Node implements Runnable {
    static final int PACKAGES_AT_NODE = 3;
    private final int nodeId;
    private Node next;
    private final ConcurrentLinkedQueue<DataPackage> bufferStack;
    private final AtomicInteger numPackages;
    private final DataCounter dataCounter;
    private final Logger logger;
    private Node coordinator;

    @Override
    public void run() {
        while (!dataCounter.isAllDataReceived()) {
            int packages = numPackages.get();
            if (!bufferStack.isEmpty() && packages < PACKAGES_AT_NODE) {
                if (numPackages.compareAndSet(packages, packages + 1)) {
                    processData();
                    numPackages.decrementAndGet();
                }
            }
        }
    }

    Node(int nodeId, DataCounter dataCounter, Logger logger) {
        this.nodeId = nodeId;
        this.dataCounter = dataCounter;
        this.logger = logger;
        bufferStack = new ConcurrentLinkedQueue<>();
        numPackages = new AtomicInteger(0);
    }

    public void addData(DataPackage dataPackage) {
        bufferStack.add(dataPackage);
        dataPackage.setBufferStart(System.nanoTime());
    }
    public Collection<DataPackage> getBuffer() {
        return bufferStack;
    }
    public void setNext(Node next) {
        this.next = next;
    }
    public int getNodeId() {
        return nodeId;
    }
    public void setCoordinator(Node coordinator) {
        this.coordinator = coordinator;
    }
    public void writeDate(DataPackage data) {
        logger.info(String.format("Node %-2d: written package %-2s", nodeId, data.getData()));
        data.setEndTime(System.nanoTime());
    }

    private void processData() {
        DataPackage data = bufferStack.poll();
        if (data == null) {
            return;
        }

        data.addTotalBufferTime(System.nanoTime() - data.getBufferStart());

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (data.getDestinationNode() == this.nodeId) {
            long packageReceivedTimestamp = System.nanoTime();
            logger.info(String.format("Node %-2d: received package in %d ms",
                    nodeId, (packageReceivedTimestamp - data.getStartTime()) / 1000000));
            coordinator.writeDate(data);
            dataCounter.incrementCounter();
        } else {
            next.addData(data);
            logger.info(String.format("Package %s : %d -> %d", data.getData(), nodeId, next.getNodeId()));
        }
    }
}
