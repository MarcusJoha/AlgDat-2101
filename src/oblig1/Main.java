
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
        // test array som består av priser på forskjellige dager
        // i dette tilfellet vil det beste være å kjøpe på dag
        // 6 og selge på dag 7, forskjell vil da være 69
        int[] stockPrices = {40, 65, 9, 2, 3, 1, 70, 10, 5};
        System.out.println(optimalStockTrade(stockPrices));


        int[] priceChanges = new int[1000];
        for (int i = 0; i < priceChanges.length ; i++) {
            // array som representerer prisendringer med 100.000 elementer
            // som består av tall mellom -300 og 800
            priceChanges[i] = generateRandomNumbers(-300, 800);
        }


        // Sjekker tiden for metoden
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            optimalStockTrade(priceChanges);
            slutt = new Date();
            ++runder;

        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double) (slutt.getTime()- start.getTime())/runder;
        System.out.println("Gjennomsnittlige millisekunder pr. runde: " + tid);

        /* Oppg 3, (16 GB med RAM skal nokk takle størrelsen på minnet)
         * Se også lagt ved graf
         *
         * Tidsmålinger for antall prisforskjeller på aksjer:
         * 1000 = 0.27, 0.26, 0.26. Snitt = 0.26
         *
         * 20_000 = 181, 184, 181. Snitt = 181
         *
         * 50_000 = 1309, 1254, 1086. Snitt = 1282
         *
         * 100_000 = 4900, 5185, 5080. Snitt = 5055
         */
    }

    // Oppg 1

    /**
     * 
     * Tar en en array med forskjellige kursforandringer på aksjer
     * regner ut aksjepriser utifra en initiell aksje-pris
     * 
     * 
     * @param stockChanges
     * @return Optimal kjøps og salgs dag med differansen mellom disse prisene
     */
    public static String optimalStockTrade(int[] stockChanges) {
        int initilaStockPrice = 100;
        int largestGap = 0;
        int buyDay= 0;
        int sellDay = 0;
        // 4 tilordninger

         int[] stockprices = new int[stockChanges.length]; // 1 tilordninge, 1 oppslag: 2

        // loop for å forandre prisendringer til akskjepriser basert på startpris for en aksje
        for (int i = 0; i < stockChanges.length; i++) { // 1 tilordning, n tester, n increment: 2n + 1
            int stockPrice = initilaStockPrice + stockChanges[i]; // n tilordning, n utregning, n tabelloppslag: 3n
            if (stockPrice < 0) { // n tester
                initilaStockPrice = 0; // n tilordning
            }
            // array som består av akjepriser
            stockprices[i] = stockPrice; // n tilordninger
        }

        // Sum1 = = 4 + 2 + 2n + 1 + 6n = 8n + 7
        
        for (int i = 0; i < stockprices.length; i++) {   // 1 tilordning, n test, n increments: 2n + 1

            for (int k = i + 1; k < stockprices.length; k++) { // n tilordninger, n sammenligninger, n increments: 3n
                // (intervallet til innerste loop blir mindre og mindre i dette tilfellet)

                // lagrere differanse i gap slik at man slipper å regne det ut flere ganger
                int gap = stockChanges[k] - stockChanges[i];               //  n tilordning, 2n tabelloppslag og n utregning: 4n
                if (largestGap < gap && stockprices[i] < stockprices[k]) { // 3n sammenligninger + 2n tablelloppslag: 5n
                    buyDay = i;                                            // n tilordning
                    sellDay = k;                                           // n tilordninger
                    largestGap = gap;                                      // n tilordninger

                    // sum fra innerste loop: 3n+4n+5n+3n = 15n
                }
            }

        }
        // sum2 = (2n + 1) * 15n = 30n^2 + 15n
        return "Buy: " + (buyDay + 1) + ", sell:  " + (sellDay + 1) + "\nLargest gap: " + largestGap; // 1 return, 2 utregninger, sum: 3

        // Kjøretid utregning = sum1 + sum2 + return
        // (8n + 7) + (30n^2 + 15n) + 3
        // = 30n^2 + 23n + 10

    }
    /** Oppg 2
     * O(30n^2 + 23n + 10)
     *
     * O(n^2) tidskompleksitet
     */

    public static int generateRandomNumbers(int min, int max) {
        return (int) (Math.random() * (max-min) + min);
    }
}
