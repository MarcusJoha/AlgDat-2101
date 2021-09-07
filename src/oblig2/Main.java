package oblig2;

import org.w3c.dom.ls.LSOutput;

import java.util.Date;

/**
 * S. 28, Oppg 2.1-1
 * S. 38, Oppg 2.2-3
 */


public class Main {
    public static void main(String[] args) {

        System.out.println("Exponent: " + exponent(2, 10)); // 4782969.0
        System.out.println("Exponent: " + exponent(2, 13)); // 1024.0

        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;

        int n = 100_000;
        /*
        Tid for de forskjellige eksponent metodene
         */
        do {
            //exponent(1.001, n);
            //expo(1.001, n);
            Math.pow(1.001, n);
            slutt = new Date();
            ++runder;

        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double) (slutt.getTime() - start.getTime()) / runder;
        System.out.println("Gjennomsnittlige millisekunder pr. runde: " + tid);


        // Test for å se om metoden (2.2-3) funker for alle verdier 0-10
        for (int i = 0; i < 11 ; i++) {
            System.out.println("2^"+ i + ": expo: " + expo(2, i));
        }
        System.out.println("2.2-3 expo(2,13): " + expo(2,13));

    }

    // Oppg 2.1-1
    // Basis n == 0
    public static double exponent(double x, int n) {
        if (n == 0) {
            return 1;
        }
        return x * exponent(x, n-1);
    }

    // Oppg 2.2-3
    // Basis n = 0 eller n = 1
    public static double expo(double x, int n) {
        if (n == 0)
            return 1.0;

        if (n == 1)
            return x;

        // Partall
        if (n % 2 == 0) {
            return expo(x*x, n/2);

        // Oddetall
        } else {
            return x * expo(x*x, (n-1)/2);
        }
    }
}