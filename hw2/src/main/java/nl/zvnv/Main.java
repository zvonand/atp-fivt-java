package nl.zvnv;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public final class Main {
    private Main() { }

    public static void main(String[] args) throws IOException {
        Path notationPath = Paths.get("input.txt");
        List<String> gameNotation = Files.readAllLines(notationPath);

        Game game = new Game(gameNotation);

        try {
            game.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        for (var colorLayout : game.getPosition()) {
            System.out.println(colorLayout.stream().map(Object::toString).collect(Collectors.joining(" ")));
        }
    }
}
