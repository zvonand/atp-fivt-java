package nl.zvnv.objects;
import lombok.Getter;

class Cell {
    private int rank;
    private int file;
    @Getter private Piece piece;
    private Color color;

    Cell(int rank, int file) {
        this.rank = rank;
        this.file = file;
        color = (rank + file) % 2 == 0 ? Color.BLACK : Color.WHITE;
        piece = new Piece(Color.NONE);
    }

    void deletePiece() {
        piece = new Piece(Color.NONE);
    }

    boolean isEmpty() {
        return piece.isNone();
    }

    boolean isWhite() {
        return color == Color.WHITE;
    }

    void placePiece(Color pieceColor, boolean dame) {
        piece = new Piece(pieceColor, dame);
    }

    void placePiece(Piece newPiece) {
        this.piece = newPiece;
    }
}
