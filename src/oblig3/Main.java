package oblig3;

public class Main {
    public static void main(String[] args) {

        int[] array = {1,2,3,4,10,6,7,8,9,10};

        int[] ar = new int[100_000];
        for (int i = 0; i < ar.length ; i++) {
            ar[i] = generateRandomNumbers(-40_000,40_000);
        }

        quickSort(ar, 0, ar.length);

        for (int i = 0; i < ar.length; i++) {
            System.out.println(ar[i]);
        }

        System.out.println(isOrdered(ar));
    }


    public static void quickSort(int[] array, int start, int end) {
        int size = end-start;
        if (size < 2) {
            return;
        }

        if (size == 4) {
            // lager en annen sorterings metode her
        }

        int pivot = split(array, start, end);
        quickSort(array, start, pivot);
        quickSort(array, pivot + 1, end);

    }

    /*
    Bruker første element som pivot og jobber fra høyre --> venstre
     */
    public static int split(int[] ar, int start, int end) {
        int pivot = ar[start];
        int i = start;
        int j = end;

        while (i < j) {
            // Decrement j (end)
            while (i < j && ar[--j] >= pivot);
            if (i < j) {
                ar[i] = ar[j];
            }

            // increment i
            while(i < j && ar[++i] <= pivot);
            if (i < j) {
                ar[j] = ar[i];
            }
        }
        ar[j] = pivot;
        return j;
    }



    public static boolean isOrdered(int[] array) {
        for (int i = 0; i < array.length - 1 ; i++) {
            if (array[i] > array[i+1]) {
                return false;
            }
        }
        return true;
    }


    /**
     *
     * @param min
     * @param max
     * @return random numbers between intarvall min and max
     */
    public static int generateRandomNumbers(int min, int max) {
        return (int) (Math.random() * (max-min) + min);
    }
}
