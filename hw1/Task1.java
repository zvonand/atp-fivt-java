import java.util.Scanner;

public class Task1 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] a = inputArray(sc, n);
        int[] b = inputArray(sc, n);

        int max = a[0];
        int[] maxA = new int[n];
        int tmp = 0;
        for (int i = 0; i < n; ++i) {
            if (a[i] > max) {
                max = a[i];
                tmp = i;
            }
            maxA[i] = tmp;
        }

        int i0 = 0, j0 = 0;
        max = b[0] + a[maxA[0]];
        for (int i = 0; i < n; ++i) {
            tmp = b[i] + a[maxA[i]];
            if (tmp > max) {
                max = tmp;
                i0 = maxA[i];
                j0 = i;
            }
        }

        System.out.println(i0 + " " + j0);
        sc.close();
    }

    private static int[] inputArray(Scanner sc, int n) {
        int[] ret = new int[n];
        for (int i = 0; i < n; ++i) {
            ret[i] = sc.nextInt();
        }
        return ret;
    }
}
