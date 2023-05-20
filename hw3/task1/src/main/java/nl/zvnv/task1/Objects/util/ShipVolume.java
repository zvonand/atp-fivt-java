package nl.zvnv.task1.Objects.util;

public enum ShipVolume {
    SMALL(10),
    MEDIUM(50),
    LARGE(100);

    private final int volumeValue;

    ShipVolume(int volumeValue) {
        this.volumeValue = volumeValue;
    }

    public int getVolumeValue() {
        return volumeValue;
    }
}