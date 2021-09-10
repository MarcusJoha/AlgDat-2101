package oblig3;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        int[] array = {-1,-2,-3,-4,-5,-6, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        //bytt(array, 0, 4);

        // liste som består a arrayer
        List<int[]> unsortedArrays = new ArrayList<>();

        // liste på størrelse 10.000 som består av usorterte
        // arrayer på størrelse 1.000.000
        int[] usortert = new int[3_00_000];
        for (int i = 0; i < 3_00_000; i++) {
            // 10.000 elementer i listen
            usortert[i] = generateRandomNumbers(-5000, 5000);
            unsortedArrays.add(usortert);
        }

        // Tid ms for sorterings algoritmen
        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        int i = 0;
        do {
            // må fikse sånn at den ikke kaller masse sorterte tabeller
            quickSortInsertionSort(unsortedArrays.get(i), 0, unsortedArrays.get(i).length - 1);
            i++;
            // minQuickSort(ar, 0, ar.length);
            slutt = new Date();
            ++runder;

        } while (i < 10_000); // kjører 10.000 runder med usortert tabell fra
        tid = (double) (slutt.getTime()- start.getTime())/runder;
        System.out.println("Gjennomsnittlige millisekunder pr. runde: " + tid);

        // true hvis sortert, false hvis ikke
        System.out.println("Er list.get(2) ordered?: Svar: " + isOrdered(unsortedArrays.get(2)));

        /**
         *  Tidtakning for tabell som er sortert
         */
        Date start1 = new Date();
        int runder1 = 0;
        double tid1;
        Date slutt1;
        int y = 0;
        do {
            // sjekker tiden for en allerede sortert tabell
            // list.get(2) skal være sortert fra tidligere kall ovenfor
            quickSortInsertionSort(unsortedArrays.get(2), 0, unsortedArrays.get(2).length - 1);
            y++;
            // minQuickSort(ar, 0, ar.length);
            slutt1 = new Date();
            ++runder1;

        } while (y < 10_000); // kjører 10.000 runder med sortert tabell
        tid1 = (double) (slutt1.getTime()- start1.getTime())/runder1;
        System.out.println("Gjennomsnittlige millisekunder pr. runde: " + tid1);

        // Sjekker at den sorterer riktig selv om den
        // er sortert fra før av
        System.out.println(isOrdered(unsortedArrays.get(2)));
        System.out.println("Og hvor mange elementer er det i listen: " + unsortedArrays.get(2).length);
    }

    public static void quickSortInsertionSort(int[] array, int start, int end) {
        if (end - start > 40_000) {
            int delops = splitt(array, start, end);
            quickSortInsertionSort(array, start, delops - 1);
            quickSortInsertionSort(array, delops + 1, end);
        }
        else {
            insertionSort(array);
            //median3Sort(array,start,end);
        }
    }

    // Finner median i tabellen
    public static int median3Sort(int[] ar, int start, int end) {
        int middle = (start + end) /2;
        if (ar[start] > ar[middle]) {
            bytt(ar, start, middle);
        }
        if (ar[middle] > ar[end]) {
            bytt(ar, middle, end);

            if (ar[start] > ar[middle])
                bytt(ar,start,middle);
        }
        return middle;
    }

    // bytter om på to indexer i en liste
    public static void bytt(int[] ar, int index1, int index2) {
        int lagring = ar[index1];
        ar[index1] = ar[index2];
        ar[index2] = lagring;
    }

    // splitter liste på pivot med mindre verdier på venstre
    // og større verdier på høyre
    public static int splitt(int[] ar, int v, int h) {
        int iv,ih;
        int m = median3Sort(ar,v,h);
        int dv = ar[m];
        bytt(ar, m, h -1);
        for (iv = v, ih = h-1;;) {
            while (ar[++iv] < dv);
            while (ar[--ih] > dv);

            if (iv >= ih)
                break;
            bytt(ar,iv,ih);
        }
        bytt(ar,iv,h - 1);
        return iv;

    }

    // egen quicksort metode
    public static void minQuickSort(int[] array, int start, int end) {
        int size = end - start;
        if (size > 10_000) {
            int pivot = split(array, start, end);
            minQuickSort(array, start, pivot);
            minQuickSort(array, pivot + 1, end);
        } else {
            insertionSort(array);
            return;
        }

    }

    public static void insertionSort(int[] array) {
        for (int firsUnsortedIndex = 1; firsUnsortedIndex < array.length  ; firsUnsortedIndex++) {
            int newElement = array[firsUnsortedIndex];

            int i;

            for (i = firsUnsortedIndex; (i > 0) && (array[i-1] > newElement) ; i--) {

                array[i] = array[i - 1];

            }
            array[i] = newElement;
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
