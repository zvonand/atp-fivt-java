package nl.zvnv.objects;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;


public final class Board {
    @Getter private final int boardSize = 8;
    private ArrayList<ArrayList<Cell>> board = new ArrayList<>(boardSize);
    @Getter private HashMap<Color, HashSet<Coordinates>> piecesPositions = new HashMap<>();

    public Board(ArrayList<String> whitePosition, ArrayList<String> blackPosition) {
        for (int rank = 0; rank < boardSize; ++rank) {
            board.add(new ArrayList<>(boardSize));
            for (int file = 0; file < boardSize; ++file) {
                board.get(rank).add(new Cell(rank, file));
            }
        }
        piecesPositions.put(Color.WHITE, new HashSet<>());
        piecesPositions.put(Color.BLACK, new HashSet<>());
        initAt(whitePosition, Color.WHITE);
        initAt(blackPosition, Color.BLACK);
    }

    public boolean cellIsEmpty(PieceProperties coords) {
        return board.get(coords.getRank()).get(coords.getFile()).isEmpty();
    }

    private Piece getPieceAt(int rank, int file) {
        return board.get(rank).get(file).getPiece();
    }

    public Piece getPieceByCoords(PieceProperties coords) {
        return board.get(coords.getRank()).get(coords.getFile()).getPiece();
    }

    private void initAt(ArrayList<String> position, Color color) {
        position.forEach(coordsNotation -> putPieceAt(coordsNotation, color));
    }

    private void putPieceAt(String coordsNotation, Color color) {
        PieceProperties coords = new PieceProperties(coordsNotation, color);
        board.get(coords.getRank()).get(coords.getFile()).placePiece(color, coords.isDame());
        Coordinates coordinates = new Coordinates(coords.getRank(), coords.getFile());
        piecesPositions.get(color).add(coordinates);
    }

    private Cell getCellAt(int rank, int file) {
        return board.get(rank).get(file);
    }

    public void movePiece(PieceProperties from, PieceProperties to) {
        Cell toCell = getCellAt(to.getRank(), to.getFile());
        toCell.placePiece(getPieceAt(from.getRank(), from.getFile()));
        piecesPositions.get(toCell.getPiece().getColor()).add(new Coordinates(to.getRank(), to.getFile()));
        deletePieceAt(from);
    }

    public void deletePieceAt(PieceProperties coords) {
        Cell cell = board.get(coords.getRank()).get(coords.getFile());
        piecesPositions.get(cell.getPiece().getColor()).removeIf(coordinates ->
                coords.getRank() == coordinates.getRank() && coords.getFile() == coordinates.getFile()
        );
        cell.deletePiece();
    }

    public ArrayList<ArrayList<String>> getCurrentLayout() {
        ArrayList<ArrayList<String>> layout =
                new ArrayList<>(Arrays.asList(new ArrayList<>(), new ArrayList<>()));
        for (int i = 0; i < boardSize; ++i) {
            for (int j = 0; j < boardSize; ++j) {
                if (getPieceAt(i, j).getColor() == Color.WHITE) {
                    layout.get(0).add(posToString(i, j));
                } else if (getPieceAt(i, j).getColor() == Color.BLACK) {
                    layout.get(1).add(posToString(i, j));
                }
            }
        }
        Collections.sort(layout.get(0));
        Collections.sort(layout.get(1));
        return layout;
    }

    private String posToString(int rank, int file) {
        return Character.toString((char) ((getPieceAt(rank, file).isDame() ? 'A' : 'a') + file)) + (char) (rank + '1');
    }
}
