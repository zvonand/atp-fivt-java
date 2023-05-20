import java.util.Scanner;

public class Task4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), k = sc.nextInt();
        sc.close();

        System.out.println(solve(n, k));
    }

    private static int solve (int n, int k) {
        if (n == 1) {
            return 1;
        }
        return (solve(n-1, k) + k - 1) % n + 1;
    }
}
