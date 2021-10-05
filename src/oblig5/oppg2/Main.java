package oppg2;


import java.util.*;

public class Main {
    public static void main(String[] args) {

        double size = 10_000_000 * 1.3;
        int intSize = (int) size;

        int fyllinsGrad50 = 6_500_013;
        int fyllinsGrad80 = 10_400_021;
        int fyllinsGrad90 = 11_700_024;
        int fyllinsGrad99 = 12_870_027;
        int fyllinsGrad100 = 13_000_027 - 1; // fordi man ikke kan plassere på index 0


        int[] randomTable = generateRandTabell(fyllinsGrad50);


        // 13.000.027: nærmeste primtall slik at man også har ledig plass for å redusere kollisjoner
        int[] hashtable1 = new int[13_000_027]; // linear probing,
        int[] hashtable2 = new int[13_000_027]; // quadratic probing
        int[] hashtable3 = new int[13_000_027]; // dobbel hash


        QuadraticProbing quadraticProbing = new QuadraticProbing(hashtable2);
        DobbelHash dobbelHash = new DobbelHash(hashtable3);

        System.out.println("randtable lengde: " + randomTable.length);
        System.out.println("Lengde for linear probing hashtable: " + linearProbing.getHashTable().length);
        System.out.println("Lengde for quadratic probing: " + quadraticProbing.getHashTable().length);
        System.out.println();


        int[] linHashTable = linearProbing.getHashTable();
        int[] quadraticHashTable = quadraticProbing.getHashTable();
        int[] dobbelHashTable = dobbelHash.getHashTable();

        /*
        Tidtakning for linear probing
         */

        Date start = new Date();
        double tid;
        Date slutt;
        for (int i = 0; i < randomTable.length; i++) {
            linearProbing.put(randomTable[i], linHashTable);
        }
        slutt = new Date();
        tid = (double) (slutt.getTime() - start.getTime());
        System.out.println("Linear probing millisekunder pr. runde: " + tid);
<<<<<<< HEAD
        // Antall kollisjoner for linær probing
        System.out.println("Collisions linear probing: " + linearProbing.getCollisions() + "\n");
=======

        System.out.println("Collisions linear probing: " + linearProbing
        .getCollisions() + "\n");
>>>>>>> 139f539c56de1df38bfe66b35169ce5d2a38c9e8


        /*
        tidtakning for quadratic probing
        */

        Date start1 = new Date();
        double tid1;
        Date slutt1;
        for (int i = 0; i < randomTable.length; i++) {
            quadraticProbing.put(randomTable[i], quadraticHashTable);
        }
        slutt1 = new Date();

        tid1 = (double) (slutt1.getTime() - start1.getTime());
        System.out.println("Quadratic probing millisekunder pr. runde: " + tid1);
        // Antall kollisjoner for quadratic probing
        System.out.println("Collisions quadratic probing: " + quadraticProbing.getCollisions() + "\n");



        /*
        Tidtakning for dobbelhash probing
         */

        Date start2 = new Date();
        double tid2;
        Date slutt2;
        for (int i = 0; i < randomTable.length; i++) {
            dobbelHash.put(randomTable[i], dobbelHashTable);
        }
        slutt2 = new Date();
        tid2 = (double) (slutt2.getTime() - start2.getTime());
        System.out.println("dobbelhash probing millisekunder pr. runde: " + tid2);
        // Antall kollisjoner for dobbel hash probing
        System.out.println("Collisions dobbelhash probing: " + dobbelHash
        .getCollisions());

    }

    /**
     * @param capacity
     * @return array med unike randome tall
     */
    static int[] generateRandTabell(int capacity) {
        int result = 0;
        int[] randTable = new int[capacity];
        Random rand = new Random();
        // unike randome tall i stigende rekkefølge
        for (int i = 0; i < randTable.length; i++) {
            result += rand.nextInt(50) + 1;
            randTable[i] = result;
        }
        // loop for å shuffle tallene slik at de ikke står i stigene rekkefølge
        for (int j = 0; j < randTable.length; j++) {
            int randomIndex = rand.nextInt(randTable.length);
            int temp = randTable[randomIndex];
            randTable[randomIndex] = randTable[j];
            randTable[j] = temp;
        }
        return randTable;
    }
}
