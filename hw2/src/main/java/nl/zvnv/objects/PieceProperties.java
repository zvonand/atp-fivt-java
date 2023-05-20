package nl.zvnv.objects;
import lombok.Getter;
import lombok.Setter;
import nl.zvnv.exceptions.GeneralErrorException;

import java.util.ArrayList;

public final class PieceProperties {
    @Getter private int rank;
    @Getter private int file;
    @Getter private Color cellColor;
    @Getter private Color pieceColor;
    @Setter private boolean dame;

    public PieceProperties(String coordsNotation, Color pieceColor) {
        rank = coordsNotation.charAt(1) - '1';
        char fileLetter = coordsNotation.charAt(0);
        file = Character.toLowerCase(fileLetter) - 'a';
        dame = Character.isUpperCase(fileLetter);
        cellColor = (rank + file) % 2 == 0 ? Color.BLACK : Color.WHITE;
        this.pieceColor = pieceColor;
    }

    public PieceProperties(int rank, int file, Color pieceColor) {
        this.rank = rank;
        this.file = file;
        this.pieceColor = pieceColor;
    }

    /**
     * Check if the destination coordinates are accessible within one move.
     * @param other destination field.
     * @return true if accessible, false otherwise.
     */
    public boolean isReachable(PieceProperties other) {
        return this.isDame() ? sameDiagonal(other) : isNeighbor(other);
    }

    private boolean isNeighbor(PieceProperties other) {
        return Math.abs(other.rank - this.rank) == 1 && Math.abs(other.file - this.file) == 1;
    }

    private boolean sameDiagonal(PieceProperties other) {
        return Math.abs(other.rank - this.rank) == Math.abs(other.file - this.file);
    }

    /**
     * Get a route from current coordinates to destination.
     * @param to destination cell coords.
     * @return ArrayList of Coordinates en route.
     * @throws GeneralErrorException in case move is invalid.
     */
    public ArrayList<PieceProperties> getRouteTo(PieceProperties to) throws GeneralErrorException {
        if (!this.sameDiagonal(to)) {
            throw new GeneralErrorException();
        } else {
            int rankDelta = (int) Math.signum(to.rank - this.rank);
            int fileDelta = (int) Math.signum(to.file - this.file);
            int curRank = this.rank;
            int curFile = this.file;
            ArrayList<PieceProperties> path = new ArrayList<>();
            while (curRank != to.rank || curFile != to.file) {
                curRank += rankDelta;
                curFile += fileDelta;
                path.add(new PieceProperties(curRank, curFile, Color.NONE));
            }
            return path;
        }
    }

    public boolean isDame() {
        return dame;
    }

    public boolean isWhite() {
        return cellColor == Color.WHITE;
    }

    public boolean outOfBounds(Board board) {
        return rank < 0 || rank >= board.getBoardSize() || file < 0 || file >= board.getBoardSize();
    }

    /**
     * Check if the cell is last in the row.
     * @param board board against which check is performed.
     * @return true if last row, false otherwise.
     */
    public boolean isLastRank(Board board) {
        return pieceColor == Color.WHITE && rank == board.getBoardSize() - 1
                || pieceColor == Color.BLACK && rank == 0;
    }
}
