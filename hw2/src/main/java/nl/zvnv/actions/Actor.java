package nl.zvnv.actions;
import nl.zvnv.exceptions.BusyCellException;
import nl.zvnv.exceptions.GeneralErrorException;
import nl.zvnv.exceptions.InvalidMoveException;
import nl.zvnv.exceptions.WhiteCellException;
import nl.zvnv.objects.Board;
import nl.zvnv.objects.PieceProperties;

import java.util.ArrayList;
import java.util.HashSet;

public class Actor {
    private Board board;
    private Validator checker = new Validator();

    public Actor(Board board) {
        this.board = board;
    }

    /**
     * Validate and perform move.
     * @param move to be performed.
     * @throws GeneralErrorException, BusyCellException, InvalidMoveException, WhiteCellException in case move incorrect
     */
    public void move(Move move)
            throws GeneralErrorException, BusyCellException, InvalidMoveException, WhiteCellException {
        checker.commonChecks(move, board);
        if (move.getType() == MoveType.STEP) {
            playStepMove(move);
        } else {
            playTakeMove(move);
        }
    }

    private void playStepMove(Move move) throws GeneralErrorException, BusyCellException, InvalidMoveException {
        checker.validateStep(move, board);
        ArrayList<PieceProperties> movePair = move.getCoordsSequence();
        board.movePiece(movePair.get(0), movePair.get(1));
        if (becomeDameAfterMove(move)) {
            board.getPieceByCoords(movePair.get(1)).makeDame();
        }
    }

    private void playTakeMove(Move move) throws GeneralErrorException, BusyCellException {
        HashSet<PieceProperties> takenPieces = checker.validateTake(move, board);
        ArrayList<PieceProperties> moveCoordsSequence = move.getCoordsSequence();
        board.movePiece(moveCoordsSequence.get(0), moveCoordsSequence.get(moveCoordsSequence.size() - 1));
        takenPieces.forEach(coords -> board.deletePieceAt(coords));
        if (becomeDameAfterMove(move)) {
            board.getPieceByCoords(moveCoordsSequence.get(moveCoordsSequence.size() - 1)).makeDame();
        }
    }

    private boolean becomeDameAfterMove(Move move) {
        return move.getCoordsSequence().stream().anyMatch(coords -> coords.isLastRank(board));
    }
}
