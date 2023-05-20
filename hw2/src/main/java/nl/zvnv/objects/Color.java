package nl.zvnv.objects;
public enum Color {
    WHITE,
    BLACK,
    NONE;

    public Color oppositeColor() {
        if (this == WHITE) {
            return BLACK;
        }
        if (this == BLACK) {
            return WHITE;
        }
        return NONE;
    }
}
