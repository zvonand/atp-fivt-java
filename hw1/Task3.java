import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Task3 {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("input.txt"));

        try
        {
            int lenA = sc.nextInt();
            int[] a = parseArray(sc, lenA);

            int lenB = sc.nextInt();
            int[] b = parseArray(sc, lenB);

            int target = sc.nextInt();

            long count = 0;

            int i = 0, j = lenB - 1;
            while ((i < lenA) && (j >= 0)) {
                if (a[i] + b[j] == target) {
                    count++;
                    i++;
                    j--;
                } else if (a[i] + b[j] > target) {
                    j--;
                } else {
                    i++;
                }
            }

            System.out.println(count);
        }
        finally
        {
            sc.close();
        }

    }

    private static int[] parseArray(Scanner sc, int len) throws IOException {
        int[] ret = new int[len];

        for (int i = 0; i < len; ++i) {
            ret[i] = sc.nextInt();
        }
        return ret;
    }
}
