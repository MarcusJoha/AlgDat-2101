package oblig5.oppg2;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        int[] hashtable = generateRandomNumbers(40);
        HashTable ht = new HashTable(40);


        for (int i = 0; i < hashtable.length ; i++) {
            System.out.println(hashtable[i]);
        }
    }


    public static int[] generateRandomNumbers(int size) {
        int i;
        int result = 0;
        int[] hashtable = new int[size];
        Random rand = new Random();
        for (i = 0; i < hashtable.length ; i++) {
             result += rand.nextInt(50) + 1;
             hashtable[i] = result;
        }
        for (int j = 0; j < hashtable.length ; j++) {
            int randomIndex = rand.nextInt(hashtable.length);
            int temp = hashtable[randomIndex];
            hashtable[randomIndex] = hashtable[j];
            hashtable[j] = temp;
        }
        return hashtable;
    }
}