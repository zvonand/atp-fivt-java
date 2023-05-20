package nl.zvnv;
import nl.zvnv.actions.Move;
import nl.zvnv.actions.Actor;
import nl.zvnv.exceptions.BusyCellException;
import nl.zvnv.exceptions.GeneralErrorException;
import nl.zvnv.exceptions.InvalidMoveException;
import nl.zvnv.exceptions.WhiteCellException;
import nl.zvnv.objects.Board;
import nl.zvnv.objects.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Game {
    private Board board;
    private List<String> gameNotation;
    private Actor interpreter;

    Game(List<String> gameNotation) {
        this.gameNotation = gameNotation;
        ArrayList<String> whitePosition = new ArrayList<>(Arrays.asList(this.gameNotation.get(0).split(" ")));
        ArrayList<String> blackPosition = new ArrayList<>(Arrays.asList(this.gameNotation.get(1).split(" ")));
        board = new Board(whitePosition, blackPosition);
        interpreter = new Actor(board);
    }

    void play() throws GeneralErrorException, BusyCellException, InvalidMoveException, WhiteCellException {
        for (int i = 2; i < gameNotation.size(); i++) {
            ArrayList<String> doubleMove = new ArrayList<>(Arrays.asList(gameNotation.get(i).split(" ")));
            Move whiteMove = new Move(doubleMove.get(0), Color.WHITE);
            Move blackMove = new Move(doubleMove.get(1), Color.BLACK);
            interpreter.move(whiteMove);
            interpreter.move(blackMove);
        }
    }

    ArrayList<ArrayList<String>> getPosition() {
        return board.getCurrentLayout();
    }
}
