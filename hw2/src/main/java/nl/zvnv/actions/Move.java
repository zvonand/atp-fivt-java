package nl.zvnv.actions;
import lombok.Getter;
import nl.zvnv.objects.Color;
import nl.zvnv.objects.PieceProperties;

import java.util.ArrayList;
import java.util.Arrays;

public final class Move {
    @Getter private Color pieceColor;
    private boolean dame;
    @Getter private MoveType type;
    @Getter private ArrayList<PieceProperties> coordsSequence = new ArrayList<>();

    public Move(String moveNotation, Color pieceColor) {
        this.pieceColor = pieceColor;
        ArrayList<String> resplittedCoordinates = new ArrayList<>(Arrays.asList(moveNotation.split("-")));
        if (resplittedCoordinates.size() > 1) {
            type = MoveType.STEP;

        } else {
            resplittedCoordinates = new ArrayList<>(Arrays.asList(moveNotation.split(":")));
            type = MoveType.TAKE;
        }
        resplittedCoordinates.forEach(
                coordsNotation -> coordsSequence.add(new PieceProperties(coordsNotation, this.pieceColor))
        );
        dame = coordsSequence.get(0).isDame();
        coordsSequence.forEach(coords -> coords.setDame(coordsSequence.get(0).isDame()));
    }

    public boolean isDame() {
        return dame;
    }
}
