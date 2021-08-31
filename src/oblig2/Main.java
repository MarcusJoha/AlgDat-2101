package oblig2;

/**
 * S. 28, Oppg 2.1-1
 * S. 38, Oppg 2.2-3
 */

public class Main {
    public static void main(String[] args) {

        System.out.println(exponent(2,4));

    }

    // Oppg 2.1-1
    public static int exponent(int x, int n) {
        if (n == 0) {
            return 1;
        }
        return x * exponent(x, n-1);
    }


}
