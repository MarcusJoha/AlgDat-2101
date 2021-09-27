package oblig5;

import java.util.LinkedList;
import java.util.ListIterator;

public class Hashtable {

    final int A = 1327217885;
    static int collisions = 0;
    private int numberOfElements;
    int hashTableLength;


    private LinkedList<String>[] hashtable;

    /*
    array som inneholder linked list
    hasher verdi
     */

    public LinkedList<String>[] getHashtable() {
        return hashtable;
    }

    public Hashtable(int capacity) {
        this.numberOfElements = 0;
        this.hashtable = new LinkedList[capacity];
        for (int i = 0; i < hashtable.length; i++) {
            hashtable[i] = new LinkedList<>(); // fyller opp array med linked list
        }
        this.hashTableLength = hashtable.length;
    }

    // returns the index of
    public int getIndexOf(String student) {
        return hashCode(student, hashTableLength);
    }

    public String getStudent(String student) {
        int hashVal = hashCode(student, hashtable.length);
        ListIterator<String> iterator = hashtable[hashVal].listIterator();
        String stud = "";
        while (iterator.hasNext()) {
            stud = iterator.next();
            if (stud.equals(student)) {
                return stud;
            }
        }
        return null;
    }



    /*
    todo: problemet nu er at jeg ikke får assosiert hashverdi med key
        , gjør at jeg ikke kan hente ut person utifra nøkkel
    må nesten lage en klasse som fikser problemet med at nøkkel blir knyttet
    opp mot en tekst string.
     */


    public int put(String student) {
        int m = hashtable.length;
        int key = hashCode(student, m);

        for (int i = 0; i < m ; i++) {
            int index = probe(key, i, m); // exponential probing
            ListIterator<String> iterator = hashtable[index].listIterator();
            if (!iterator.hasNext()) { // Hvis den er ledig, legg til student på index
                hashtable[index].add(student); // ledig index blir key til student
                numberOfElements++;
                return index;
            }
        }
        return -1; // full
    }


    // Vet ikke om put-metoden min var helt riktig
    public int annenPut(String student) {
        int m = hashtable.length;
        int key = hashCode(student, m); // viktig at denne blir jevnt fordelt
        ListIterator<String> iterator = hashtable[key].listIterator();
        String stud = "";
        // Bryter ut av while, betyr neste er ledig
        hashtable[key].addLast(student); // ingen kollisjoner fordi den legge sist
        numberOfElements++;
        return key;
    }

    public double lastFaktor() {
        return (double) numberOfElements/ (double) hashTableLength;
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

    public int divHash(int val, int length) {
        return val%length;
    }

    // ikke helt som det skal være
    public int multHash(int k) {
        return (k * A >>> (31-2*2*2*2*2*2*2));
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

    // trur ikke jeg skal bruke denne
    static class Student {
        private int key;
        private String name;

        public Student(int key, String name) {
            this.key = key;
            this.name = name;
        }

        public int getKey() {
            return key;
        }

        public String getName() {
            return name;
        }

        public void setKey(int key) {
            this.key = key;
        }
    }
}
