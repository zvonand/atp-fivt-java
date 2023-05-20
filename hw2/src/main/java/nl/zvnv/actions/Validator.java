package nl.zvnv.actions;

import nl.zvnv.exceptions.BusyCellException;
import nl.zvnv.exceptions.GeneralErrorException;
import nl.zvnv.exceptions.InvalidMoveException;
import nl.zvnv.exceptions.WhiteCellException;
import nl.zvnv.objects.Board;
import nl.zvnv.objects.Color;
import nl.zvnv.objects.Coordinates;
import nl.zvnv.objects.PieceProperties;

import java.util.ArrayList;
import java.util.HashSet;

public class Validator {

    /**
     * Performs common checks for any move type.
     * @param move move checked.
     * @param board current table layout.
     * @throws GeneralErrorException in case move is invalid.
     */
    public void commonChecks(Move move, Board board) throws GeneralErrorException, WhiteCellException {
        noWhiteCells(move);
        pieceColorMatches(move, board);
        isDameMatches(move, board);
    }

    private void noWhiteCells(Move move) throws WhiteCellException {
        if (move.getCoordsSequence().stream().anyMatch(PieceProperties::isWhite)) {
            throw new WhiteCellException();
        }
    }

    private void pieceColorMatches(Move move, Board board) throws GeneralErrorException {
        if (!(move.getPieceColor() == board.getPieceByCoords(move.getCoordsSequence().get(0)).getColor())) {
            throw new GeneralErrorException();
        }
    }

    private void isDameMatches(Move move, Board board) throws GeneralErrorException {
        if (!(move.isDame() == board.getPieceByCoords(move.getCoordsSequence().get(0)).isDame())) {
            throw new GeneralErrorException();
        }
    }

    /**
     * Performs checks for a Step move.
     * @param move move to be checked.
     * @param board current board layout.
     * @throws GeneralErrorException in case move is invalid.
     */
    public void validateStep(Move move, Board board)
            throws GeneralErrorException, BusyCellException, InvalidMoveException {
        targetCellEmpty(move, board);
        ArrayList<PieceProperties> coordsPair = move.getCoordsSequence();
        if (!(new Route(coordsPair.get(0), coordsPair.get(1))).stepMoveAllowed(board)) {
            throw new GeneralErrorException();
        }
        unreachablePiece(move, board);
    }

    private void targetCellEmpty(Move move, Board board) throws BusyCellException {
        if (!board.cellIsEmpty(move.getCoordsSequence().get(1))) {
            throw new BusyCellException();
        }
    }

    private void unreachablePiece(Move move, Board board) throws InvalidMoveException {
        for (Coordinates coordinates : board.getPiecesPositions().get(move.getPieceColor())) {
            unableToTake(
                    new PieceProperties(coordinates.getRank(), coordinates.getFile(), move.getPieceColor()),
                    board,
                    new HashSet<>()
            );
        }
    }

    private void unableToTake(PieceProperties coords, Board board, HashSet<PieceProperties> takenPieces)
            throws InvalidMoveException {
        if (takingAllowedAt(coords, board, -1, -1, takenPieces)) {
            throw new InvalidMoveException();
        } else if (takingAllowedAt(coords, board, -1, 1, takenPieces)) {
            throw new InvalidMoveException();
        } else if (takingAllowedAt(coords, board, 1, -1, takenPieces)) {
            throw new InvalidMoveException();
        } else if (takingAllowedAt(coords, board, 1, 1, takenPieces)) {
            throw new InvalidMoveException();
        }
    }

    private boolean takingAllowedAt(PieceProperties coords, Board board, int rankDelta, int fileDelta,
                                    HashSet<PieceProperties> takenPieces) {
        if (!coords.isDame()) {
            return !takenPieces.contains(coords) && plainPieceTakingAllowedAt(coords, board, rankDelta, fileDelta);
        } else {
            return !takenPieces.contains(coords) && damePieceTakingAllowedAt(coords, board, rankDelta, fileDelta);
        }
    }

    private boolean plainPieceTakingAllowedAt(PieceProperties coords, Board board, int rankDelta, int fileDelta) {
        PieceProperties takeCoords = new PieceProperties(
                coords.getRank() + rankDelta,
                coords.getFile() + fileDelta,
                board.getPieceByCoords(coords).getColor().oppositeColor()
        );
        PieceProperties landCoords = new PieceProperties(
                coords.getRank() + 2 * rankDelta,
                coords.getFile() + 2 * fileDelta,
                Color.NONE
        );
        if (takeCoords.outOfBounds(board) || landCoords.outOfBounds(board)) {
            return false;
        }
        return takeCoords.getPieceColor() == board.getPieceByCoords(takeCoords).getColor()
                && landCoords.getPieceColor() == board.getPieceByCoords(landCoords).getColor();
    }

    private boolean damePieceTakingAllowedAt(PieceProperties pieceCoords, Board board, int rankDelta, int fileDelta) {
        Color pieceColor = board.getPieceByCoords(pieceCoords).getColor();
        PieceProperties curCoords = new PieceProperties(
                pieceCoords.getRank() + rankDelta,
                pieceCoords.getFile() + fileDelta,
                pieceColor.oppositeColor()
        );
        boolean legal = true;
        int counter = 0;
        if (curCoords.outOfBounds(board) || board.getPieceByCoords(curCoords).getColor() == pieceColor) {
            legal = false;
        }
        while (legal && board.getPieceByCoords(curCoords).getColor() != pieceColor.oppositeColor()) {
            ++counter;
            curCoords = new PieceProperties(
                    pieceCoords.getRank() + counter * rankDelta,
                    pieceCoords.getFile() + counter * fileDelta,
                    pieceColor.oppositeColor()
            );
            if (curCoords.outOfBounds(board) || board.getPieceByCoords(curCoords).getColor() == pieceColor) {
                legal = false;
                break;
            }
        }
        ++counter;
        PieceProperties landingCell = new PieceProperties(
                pieceCoords.getRank() + counter * rankDelta,
                pieceCoords.getFile() + counter * fileDelta,
                Color.NONE
        );
        if (landingCell.outOfBounds(board)) {
            legal = false;
        }
        return  legal && board.getPieceByCoords(landingCell).getColor() == Color.NONE;
    }

    /**
     * Validate Take move.
     * @param move Move to validate.
     * @param board current board layout.
     * @return HashSet of Coordinates of taken pieces.
     * @throws GeneralErrorException in case move is invalid.
     */
    HashSet<PieceProperties> validateTake(Move move, Board board) throws GeneralErrorException, BusyCellException {
        ArrayList<PieceProperties> coordsSequence = move.getCoordsSequence();
        HashSet<PieceProperties> takenPieces = new HashSet<>();
        for (int i = 0; i < coordsSequence.size() - 1; ++i) {
            Route route = new Route(coordsSequence.get(i), coordsSequence.get(i + 1));
            takenPieces.add(route.takeMoveAllowed(board));
        }

        return takenPieces;
    }
}
