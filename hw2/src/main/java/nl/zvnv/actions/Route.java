package nl.zvnv.actions;
import nl.zvnv.exceptions.BusyCellException;
import nl.zvnv.exceptions.GeneralErrorException;
import nl.zvnv.objects.Board;
import nl.zvnv.objects.Color;
import nl.zvnv.objects.PieceProperties;

import java.util.ArrayList;

class Route {
    private PieceProperties from;
    private PieceProperties to;
    Route(PieceProperties from, PieceProperties to) {
        this.from = from;
        this.to = to;
    }

    boolean stepMoveAllowed(Board board) throws GeneralErrorException {
        return from.isReachable(to) && isFree(board);
    }

    private boolean isFree(Board board) throws GeneralErrorException {
        ArrayList<PieceProperties> checkRoute;
        checkRoute = from.getRouteTo(to);
        return checkRoute.stream().allMatch(board::cellIsEmpty);
    }

    PieceProperties takeMoveAllowed(Board board) throws GeneralErrorException, BusyCellException {
        ArrayList<PieceProperties> checkRoute;
        checkRoute = from.getRouteTo(to);

        if (!board.getPieceByCoords(checkRoute.get(checkRoute.size() - 1)).isNone()) {
            throw new BusyCellException();
        }

        Color pieceColor = from.getPieceColor();

        long routeTakeableCount = checkRoute.stream()
                .filter(coords -> board.getPieceByCoords(coords).getColor() == pieceColor.oppositeColor()).count();
        if (routeTakeableCount > 1) {
            throw new BusyCellException();
        }

        if (checkRoute.stream().anyMatch(coords -> board.getPieceByCoords(coords).getColor() == pieceColor)) {
            throw new BusyCellException();
        }
        for (PieceProperties coords : checkRoute) {
            if (board.getPieceByCoords(coords).getColor() == pieceColor.oppositeColor()) {
                return coords;
            }
        }
        throw new GeneralErrorException();
    }
}
