
package oblig1;

import java.util.Date;
import java.util.Random;
import java.util.Timer;

/**
 * Oblig 1 i Algoritmer og Datastrukturer
 * oppg. 1-1, 1-2, 1-3. S. 18 og 19: Hafting og Ljosland
 */

public class Main {


    public static void main(String[] args) {
        int[] priceChange = {40, 65, -9, 2, 2, -1, 70, -10, -5};
        System.out.println(largGap(priceChange));


        int[] ar = new int[100_000];
        Random rand = new Random();
        for (int i = 0; i < 100_000; i++) {
            ar[i] = rand.nextInt(1_000_000);
        }

        // Sjekker tiden(millisekunder) for metoden
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;

        do {
            largGap(ar);
            slutt = new Date();
            ++runder;

        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double) (slutt.getTime()- start.getTime())/runder;
        System.out.println("Gjennomsnittlige millisekunder pr. runde: " + tid);

        /* Oppg 3, (16 GB med RAM skal nokk takle størrelsen på minnet)
         * Tidsmålinger for antall prisforskjeller på aksjer:
         * 10_000 = 38 ms
         * 30_000 = 358 ms
         * 50_000 = 1870 ms
         * 100_0000 = 4946 ms
         */
    }

    // Oppg 1
    public static String largGap(int[] array) {
        Timer time = new Timer();
        int largestGap = 0;                              // 1 tilordning
        int buy = 0;                                     // 1 tilordning
        int sell = 0;                                    // 1 tilordning
        for (int i = 0; i < array.length; i++) {         // 1 tilordning
            // sum: 4
            // n sammenligning, n increments, sum: 2n

            for (int k = i + 1; k < array.length; k++) { // n tilordninger, n sammenligninger, n increments: 3n
                // (intervallet til innerste loop blir mindre og mindre i dette tilfellet)

                int gap = array[k] - array[i];           //  n tilordning, 2n tabelloppslag og n utregning: 4n
                if (largestGap < gap && array[i] < array[k]) { // 3n sammenligninger + 2n tablelloppslag: 5n
                    buy = i;                             // n tilordning
                    sell = k;                            // n tilordninger
                    largestGap = gap;                    // n tilordninger

                    // sum fra innerste loop: 3n+4n+5n+3n = 15n
                }
            }
        }
        // Mye å returnere, kunne heller lagd et object...
        return "Shoul buy on day " + (buy + 1) + " and sell on day " + (sell + 1) + "\nThe gap is then " + largestGap; // 1 return, 2 utregninger, sum: 3

        // Kjøretid utregning: 4 + 3 + 2n*(15n) = 7 + 30n^2
    }

    /** Oppg 2
     * Kjøretid: 30n^2 + 7
     *
     * O(n^2) tidskompleksitet
     */
}
