package nl.zvnv.task2;

public class DataPackage {
    private final int destinationNode;
    private final String data;
    private final long startTime;
    private long endTime;
    private long totalBufferTime;
    private long bufferStart;

    public DataPackage(int destinationNode, String data) {
        this.destinationNode = destinationNode;
        this.data = data;
        this.totalBufferTime = 0;
        startTime = System.nanoTime();
    }
    public int getDestinationNode() {
        return destinationNode;
    }
    public long getStartTime() {
        return this.startTime;
    }
    public String getData() {
        return this.data;
    }
    public long getEndTime() {
        return endTime;
    }
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
    public long getBufferStart() {
        return bufferStart;
    }
    public void setBufferStart(long bufferStart) {
        this.bufferStart = bufferStart;
    }
    public long getTotalBufferTime() {
        return totalBufferTime;
    }
    public void addTotalBufferTime(long added) {
        this.totalBufferTime += added;
    }
}
