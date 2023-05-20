package nl.zvnv.objects;
import lombok.Getter;

public final class Piece {
    @Getter private Color color;
    private boolean dame;

    Piece(Color color) {
        this.color = color;
        dame = false;
    }

    Piece(Color color, boolean dame) {
        this.color = color;
        this.dame = dame;
    }

    public boolean isDame() {
        return dame;
    }

    public boolean isNone() {
        return color == Color.NONE;
    }

    public void makeDame() {
        dame = true;
    }
}
