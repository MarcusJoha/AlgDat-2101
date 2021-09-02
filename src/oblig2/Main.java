package oblig2;

import java.util.Date;

/**
 * S. 28, Oppg 2.1-1
 * S. 38, Oppg 2.2-3
 */

public class Main {
    public static void main(String[] args) {

        char a = 'A';
        char b = 'B';
        char c = 'C';

        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        // Min exponent metode
        do {
            exponent(3,14);
            slutt = new Date();
            ++runder;

        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double) (slutt.getTime()- start.getTime())/runder;
        System.out.println("Gjennomsnittlige millisekunder pr. runde: " + tid);

        // Java sin eksponent metode!!!
        do {
            Math.pow(3, 14);
            slutt = new Date();
            ++runder;

        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double) (slutt.getTime()- start.getTime())/runder;
        System.out.println("Gjennomsnittlige millisekunder pr. runde: " + tid);


        System.out.println("Exponent: " + exponent(3,14)); // 1024.0
        System.out.println("Exponent: " + exponent(2, 10)); // 4782969.0
        System.out.println("Hanoitime: " + hanoiTime(2,4));
        // hanoi(3, a,b,c);
/*
Med 3 steg for hanoi
        A --> C
        A --> B
        C --> B
        A --> C
        B --> A
        B --> C
        A --> C
 */

    }

    // Oppg 2.1-1
    public static double exponent(double x, int n) {
        if (n == 0) {
            return 1;
        }
        return x * exponent(x, n-1);
    }


    // Oppg 2.2-3
    public static double hanoiTime(double x, int n ) {
        if (n == 0) {
            return 1.0;
        }
        // Partall
        if (n % 2 == 0) {
            // -1 ???
            return x * hanoiTime(x*x, (n-1)/2);

        // Oddetall
        } else {
            // -2 ???
            return x * hanoiTime(x*x, (n-2)/2);
        }
    }

    /*
     * Løsninger på oddetall/partall problemet er
     * at om man printer a --> c eller a --> b
     * dreier seg om NÅR man kaller hanoi()
     * (Nice to know)
     */

    public static void hanoi(int n, char a, char b, char c) {
        System.out.println("Verdi n: " + n);
        if (n == 1) { // Vil aldri gå under 1 uansett (hvorfor < ??)
            System.out.println("if(n<=1) = true");
            System.out.println(a + " --> " + c);
            // slutt på dekrementering, funksjonen returnerer...
        } else {
            System.out.println("\nHanoi 1");
            hanoi(n-1, a, c, b); // n = 2
            // n = 1
            // skriver: nu er vi her
            // A --> B
            // A --> C
            System.out.println(a + " --> " + c);
            // Hopper til Hannoi 2
            System.out.println("\nHannoi 2");
            hanoi(n-1, b,a,c);
        }
    }

}
