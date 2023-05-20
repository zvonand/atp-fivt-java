package nl.zvnv;

import nl.zvnv.objects.Board;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

class BoardTest {
    // Let's have example from the task
    private String whitePositionNotation = "a1 a3 b2 c1 c3 d2 e1 e3 f2 g1 g3 h2";
    private String blackPositionNotation = "a7 b6 b8 c7 d6 d8 e7 f6 f8 g7 h6 h8";
    private ArrayList<String> whiteLayout = new ArrayList<>(Arrays.asList(whitePositionNotation.split(" ")));
    private ArrayList<String> blackLayout = new ArrayList<>(Arrays.asList(blackPositionNotation.split(" ")));

    @Test
    void ConstructorTest() {
        Board board = new Board(whiteLayout, blackLayout);
        assert board.getCurrentLayout().equals(
                new ArrayList<>(Arrays.asList(whiteLayout, blackLayout))
        );
    }
}
