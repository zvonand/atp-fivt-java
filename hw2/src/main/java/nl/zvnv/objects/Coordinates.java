package nl.zvnv.objects;
import lombok.Getter;

public class Coordinates {
    @Getter private int rank;
    @Getter private int file;

    Coordinates(int rank, int file) {
        this.rank = rank;
        this.file = file;
    }
}
