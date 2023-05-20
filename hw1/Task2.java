import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Task2 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));

        int n = Integer.parseInt(reader.readLine());

        if (n == 1) {
            System.out.println(0);
            return;
        }

        float area = 0;

        String[] currPointStr = reader.readLine().split(" ");

        int[] firstVert = {Integer.parseInt(currPointStr[0]), Integer.parseInt(currPointStr[1])};
        int[] prevVert = {firstVert[0], firstVert[1]};

        for (int i = 1; i < n; ++i) {
            currPointStr = reader.readLine().split(" ");
            int[] currVert = {Integer.parseInt(currPointStr[0]), Integer.parseInt(currPointStr[1])};
            int width = currVert[0] - prevVert[0];
            float avgBase = (float) (currVert[1] + prevVert[1]) / 2;

            area += width * avgBase;
            prevVert[0] = currVert[0];
            prevVert[1] = currVert[1];
        }

        area += (firstVert[0] - prevVert[0]) * ((float) (firstVert[1] + prevVert[1]) / 2);

        System.out.println(area);

        reader.close();

    }
}
