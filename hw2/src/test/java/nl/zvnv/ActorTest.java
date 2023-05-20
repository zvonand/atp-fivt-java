package nl.zvnv;

import nl.zvnv.actions.Move;
import nl.zvnv.actions.Actor;
import nl.zvnv.exceptions.BusyCellException;
import nl.zvnv.exceptions.GeneralErrorException;
import nl.zvnv.exceptions.InvalidMoveException;
import nl.zvnv.exceptions.WhiteCellException;
import nl.zvnv.objects.Board;
import nl.zvnv.objects.Color;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

class ActorTest {

    @Test
    void StepMoveTest() throws GeneralErrorException, BusyCellException, InvalidMoveException, WhiteCellException {
        ArrayList<String> whitePosition = new ArrayList<>(Collections.singletonList("a1"));
        ArrayList<String> blackPosition = new ArrayList<>();
        Board board = new Board(whitePosition, blackPosition);
        Actor interpreter = new Actor(board);
        interpreter.move(new Move("a1-b2", Color.WHITE));
        assert board.getCurrentLayout().equals(new ArrayList<>(Arrays.asList(
                new ArrayList<>(Collections.singletonList("b2")), new ArrayList<>()
        )));
        try {
            interpreter.move(new Move("b2-e4", Color.WHITE));
        } catch (WhiteCellException e) {
            assert e.getMessage() == "white cell";
        }
    }

    @Test
    void BecomesDameTest() throws GeneralErrorException, BusyCellException, InvalidMoveException, WhiteCellException {
        ArrayList<String> whitePosition = new ArrayList<>(Collections.singletonList("b2"));
        ArrayList<String> blackPosition = new ArrayList<>(Collections.singletonList("a3"));
        Board board = new Board(whitePosition, blackPosition);
        Actor interpreter = new Actor(board);
        interpreter.move(new Move("a3:c1", Color.BLACK));
        assert board.getCurrentLayout().equals(new ArrayList<>(Arrays.asList(
                new ArrayList<>(), new ArrayList<>(Collections.singletonList("C1"))
        )));
    }

    @Test
    void DameTakesTwiceTest() throws GeneralErrorException, BusyCellException, InvalidMoveException, WhiteCellException {
        ArrayList<String> whitePosition = new ArrayList<>(Collections.singletonList("A1"));
        ArrayList<String> blackPosition = new ArrayList<>(Arrays.asList("b2", "F2"));
        Board board = new Board(whitePosition, blackPosition);
        Actor interpreter = new Actor(board);
        interpreter.move(new Move("A1:d4:g1", Color.WHITE));
        assert board.getCurrentLayout().equals(new ArrayList<>(Arrays.asList(
                new ArrayList<>(Collections.singleton("G1")), new ArrayList<>()
        )));
    }
}
