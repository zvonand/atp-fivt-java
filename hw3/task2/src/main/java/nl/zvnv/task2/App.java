package nl.zvnv.task2;

public class App {
    static final int NODES = 10;
    static final int PACKAGES = 100;

    public static void main(String[] args) {
        RingProcessor processor;
        try {
            processor = new RingProcessor(NODES, PACKAGES, "ring.log");
            processor.startProcessing();
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        processor.logAvgNetworkDelay();
        processor.logAvgBufferDelay();
    }
}
