package oblig5;

import java.util.LinkedList;
import java.util.ListIterator;

public class Hashtable {

    static int collisions = 0;


    private LinkedList<String>[] hashtable;

    /*
    array som inneholder linked list
    hasher verdi
     */

    public LinkedList<String>[] getHashtable() {
        return hashtable;
    }

    public Hashtable(int capacity) {
        this.hashtable = new LinkedList[capacity];
        for (int i = 0; i < hashtable.length; i++) {
            hashtable[i] = new LinkedList<>(); // fyller opp array med linked list
        }
    }

    public int put(String student) {
        int m = hashtable.length;
        int key = (int) hashCode(student, m);

        for (int i = 0; i < m ; i++) {
            int index = probe(key, i, m); // exponential probing
            ListIterator<String> iterator = hashtable[index].listIterator();
            if (!iterator.hasNext()) { // Hvis den er ledig, legg til student på index
                hashtable[index].add(student);
                return index;
            }
        }
        return -1; // full


    }

    public int probe(int hashed, int i, int size) {
        collisions++;
        return (hashed + 3*i + 5*i*i) % size;
    }


    /**
     * @param input
     * @return
     */
    public int hashCode(String input, int length) {
        char[] in = input.toCharArray();
        long numberValue = 0;
        int hashValue;
        int mul = 7;
        for (int i = 0; i < in.length ; i++) {
            // todo: fikse, var det dette vi egentlig skulle gjøre?
            for (int j = 0; j < i ; j++) {
                mul *= 7;
            }
            numberValue += (long)(in[i])*mul;
        }
        // rest div, forandrer senere??
        hashValue = Math.abs((int)numberValue%length);
        return hashValue;

    }

    public void printChainedHashtable() {
        for (int i = 0; i < hashtable.length; i++) {
            ListIterator<String> iterator = hashtable[i].listIterator();
            String student = "";
            while(iterator.hasNext()) {
                student = iterator.next();
                System.out.println("key: " + i + " student: " + student);
            }
        }
    }
}
