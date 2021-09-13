package oblig3;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    // todo: må kjøre målinger igjen på det
    //  må også analysere med tidskompleksitet
    // også må jeg finne ut hva som skjer med 1.000.000

    public static void main(String[] args) {

        // liste som består a arrayer
        List<int[]> unsortedArrays1 = new ArrayList<>();
        List<int[]> unsortedArrays2 = new ArrayList<>();

        int antallElementer = 1_000_000;
        // lager 2 arrayer som jeg kan kjøre tidtakning med.
        // begge arrayer består av samme sammensetning av elementer mellom
        // -400.000 og 400.000 med størrelse 1.000.000. Dette er for å få stor variasjon av tall
        // men også en del like tall for å se om algoritmen takler det.
        int[] usortert;
        int[] copyUsortert;
        for (int k = 0; k < 21 ; k++) {
            usortert = new int[antallElementer];
            // usortert: list med 1.000.000 elementer med
            // verdier mellom -400.000 og 400.000
            for (int i = 0; i < antallElementer; i++) {
                usortert[i] = generateRandomNumbers(-400_000, 400_000);
            }
            // to array lists med 5 usorterte arrary som består av antallElementer
            unsortedArrays1.add(usortert);
            copyUsortert = new int[usortert.length];
            // kopierer fra usortert til copyUsortert
            System.arraycopy(usortert, 0, copyUsortert, 0, usortert.length);
            unsortedArrays2.add(copyUsortert);
        }
        // =======================================================================================


        /*
        Tidtakning for tabell som er usortert
         */
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            // bruker .get(i) som er O(n) men går greit
            // ettersom begge metodene bruker det.
            //quickSortInsertionSort(unsortedArrays1.get(runder), 0, unsortedArrays1.get(runder).length-1);
            quickSort(unsortedArrays1.get(runder), 0, unsortedArrays1.get(runder).length-1);
            slutt = new Date();
            System.out.println(runder);
            ++runder;

        } while (runder < 20); // kjører 20 runder med sortertinhg av usortert tabell
        tid = (double) (slutt.getTime()- start.getTime())/runder;
        System.out.println("Gjennomsnittlige millisekunder pr. runde: " + tid);

        // Sjekker liste på index to som stikkprøve at den faktisk sorterte
        System.out.println("Stikkprøve: Er list.get(2) ordered?: (burde være true) Svar: " + isOrdered(unsortedArrays1.get(2)));
        // Sjekker også at størrelsen på listen er 1.000.000
        System.out.println("Størrelse burde være 1.000.000: " + unsortedArrays1.get(2).length);
    }

    /**
     *
     * @param array
     * @param start
     * @param end
     *
     * quicksort metdoe som bruker insertion sort som hjelpe metode
     * når størrelsen på del-arrayer blir mindre enn en viss størrelse
     */

    public static void quickSortInsertionSort(int[] array, int start, int end) {
        if (end - start > 100) { //
            //System.out.println("Nu bruker vi quicksort");
            int delops = splitt(array, start, end); // 1 metode kall
            // 2 rekursvie kall
            quickSortInsertionSort(array, start, delops - 1);
            quickSortInsertionSort(array, delops + 1, end);
        }
        else {
            insertionSort(array, start, end);
            //median3Sort(array,start,end);
        }
    }


    /**
     *
     * @param array
     * @param start
     * @param end
     * uten hjelpemetode
     */
    public static void quickSort(int[] array, int start, int end) {
        if (end - start > 2) { //
            //System.out.println("Nu bruker vi quicksort");
            int delops = splitt(array, start, end); // 1 metode kall
            // 2 rekursvie kall
            quickSort(array, start, delops - 1);
            quickSort(array, delops + 1, end);
        }
        else {
            median3Sort(array,start,end);
        }
    }


    // Finner median i tabellen
    // O(1)
    public static int median3Sort(int[] ar, int start, int end) {
        int middle = (start + end) /2; // 3
        if (ar[start] > ar[middle]) { // 3
            bytt(ar, start, middle); // 1
        }
        if (ar[middle] > ar[end]) { // 3
            bytt(ar, middle, end); // 1

            if (ar[start] > ar[middle]) // 3
                bytt(ar,start,middle); // 1
        }
        return middle; // 1
    }

    // bytter om på to indexer i en liste
    // O(1)
    public static void bytt(int[] ar, int index1, int index2) {
        int lagring = ar[index1]; // 2
        ar[index1] = ar[index2]; // 2
        ar[index2] = lagring; // 2
    }

    // splitter liste på pivot med mindre verdier på venstre
    // og større verdier på høyre
    // O(n) - tidskompleksitet
    public static int splitt(int[] ar, int v, int h) {
        int iv,ih; // 2
        int m = median3Sort(ar,v,h); // 2
        int dv = ar[m]; // 2
        bytt(ar, m, h - 1); // 2
        for (iv = v, ih = h-1;;) { // 2n
            // Uavhenging av for-løkken?
            while (ar[++iv] < dv); // 2n
            while (ar[--ih] > dv); // 2n

            if (iv >= ih) // n
                break;
            bytt(ar,iv,ih); // n (værste tilfellet)
        }
        bytt(ar,iv,h - 1); // 1
        return iv; // 1

        // sum: 10 +
    }


    public static void insertionSort(int[] array,int start, int end) {

        for (int firsUnsortedIndex = start + 1; firsUnsortedIndex <= end; firsUnsortedIndex++) {
            int newElement = array[firsUnsortedIndex];
            int i;
            for (i = firsUnsortedIndex; (i > start) && (array[i-1] > newElement) ; i--) {
                array[i] = array[i - 1];
            }
            array[i] = newElement;
        }
    }


    /**
     *
     * NEDENFOR:
     * koder som egentlig ikke er del av innlevering
     * prøvde litt andre versjoner enn det jeg fant i boken (Hafting og Ljosland)
     */

    public static void minQuickSort(int[] array, int start, int end) {
        int size = end - start;
        if (size > 10_000) {
            int pivot = split(array, start, end);
            minQuickSort(array, start, pivot);
            minQuickSort(array, pivot + 1, end);
        } else {
            //insertionSort(array);
            return;
        }

    }

    /*
    Bruker første element som pivot og jobber fra høyre --> venstre
    egen splitt metode, splitter på start index
     */
    public static int split(int[] ar, int start, int end) {
        int pivot = ar[start];
        int i = start;
        int j = end;

        while (i < j) {
            // Decrement j (end)
            while (i < j && ar[--j] >= pivot) ;
            if (i < j) {
                ar[i] = ar[j];
            }
            // increment i
            while (i < j && ar[++i] <= pivot) ;
            if (i < j) {
                ar[j] = ar[i];
            }
        }
        ar[j] = pivot;
        return j;
    }


    // sjekker om listen er sortert
    public static boolean isOrdered(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    // genererer random nummer mellom min og max
    public static int generateRandomNumbers(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }
}
