package oblig5.oppg2;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        double size = 10_000_000 * 1.3;
        int intSize = (int) size;

        // random array of numbers (shuffeled)
        int[] randomTable = generateRandTabell(10_000_000);

        int[] hashtable1 = new int[intSize+7]; // linear probing
        int[] hashtable2 = new int[intSize+7]; // quadratic probing
        int[] hashtable3 = new int[intSize+7]; // dobbel hash

        HashTable.LinearProbing linearProbing = new HashTable.LinearProbing(hashtable1);
        HashTable.QuadraticProbing quadraticProbing = new HashTable.QuadraticProbing(hashtable2);
        HashTable.DobbelHash dobbelHash = new HashTable.DobbelHash(hashtable3);


        System.out.println("randtable lengde: " + randomTable.length);
        System.out.println("Lengde for linear probing hashtable: " + linearProbing.getHashTable().length); // 1300
        System.out.println("Lengde for quadratic probing: " + quadraticProbing.getHashTable().length); // 1200

        int[] linHashTable = linearProbing.getHashTable();
        int[] quadraticHashTable = quadraticProbing.getHashTable();
        int[] dobbelHashTable = dobbelHash.getHashTable();


        // Collisons for linear probing
        for (int i = 0; i < randomTable.length ; i++) {
            linearProbing.put(randomTable[i], linHashTable);
        }
        System.out.println("Collisions linear probing: " + linearProbing.getCollisions());


        // Collisions for quadratic probing
        for (int i = 0; i < randomTable.length; i++) {
            quadraticProbing.put(randomTable[i], quadraticHashTable);
        }
        System.out.println("Collisions quadratic probing: " + quadraticProbing.getCollisions());


        // Collisions for dobbel hash probing
        for (int i = 0; i < randomTable.length ; i++) {
            dobbelHash.put(randomTable[i], dobbelHashTable);
        }
        System.out.println("Collisions dobbelhash probing: " + dobbelHash.getCollisions());
    }



    static int[] generateRandTabell(int capacity) {
        int result = 0;
        int[] randTable = new int[capacity];
        Random rand = new Random();
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
