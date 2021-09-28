package oblig5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class Hashtable {

    final int A = 1327217885;
    private int collisions = 0;
    private int numberOfElements;
    int hashTableLength;
    private ArrayList<String> students;


    private LinkedList<String>[] hashtable;

    /*
    array som inneholder linked list
    hasher verdi
     */

    public LinkedList<String>[] getHashtable() {
        return hashtable;
    }

    public int sizeOfArListStudents() {
        return students.size();
    }

    public int getNumberOfElements() {
        return numberOfElements;
    }

    /**
     *
     * @param capacity
     * konstruktør hvor man bestemmer selv hvor mange elementer man vil ha
     */
    public Hashtable(int capacity) {
        this.numberOfElements = 0;
        this.hashtable = new LinkedList[capacity];
        for (int i = 0; i < hashtable.length; i++) {
            hashtable[i] = new LinkedList<>(); // fyller opp array med linked list
        }
        this.hashTableLength = hashtable.length;
    }

    /**
     *
     * @param filePath
     * konstrukør som leser fra fil
     * initialsierer størrelse på hashtable utifra antall elementer i fil
     * kan velge meollom to put metoder i hash-tabellen
     */
    public Hashtable(String filePath) {
        this.collisions = 0;
        this.numberOfElements = 0;
        this.students = new ArrayList<>();
        try {
            readFromFile(filePath);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        this.hashtable = new LinkedList[(int)(students.size() * 1.25)];
        for (int i = 0; i < hashtable.length; i++) {
            hashtable[i] = new LinkedList<>(); // fyller opp array med linked list
        }
        for (String stud: students) {
            //put(stud); // får hver sin index, men kollisjoner
            annenPut(stud); // på samme index, viktig med spredning
        }
        this.hashTableLength = hashtable.length;
    }


    /**
     *
     * @param student
     * @return index for student
     * fungerer egentlig bare når man har
     * brukt annenPut() metoden
     */
    public int getIndexOf(String student) {
        return hashCode(student, hashTableLength);
    }

    public int getCollisions() {
        return collisions;
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

    // todo: får ikke lagret ny nøkkelverdi med min implementasjon
    //  når probing blir kalt
    //  fordi nøkkel bestemmes av hash funksjonen min.
    //annenPut() har ikke dette problemet
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


    /**
     * (Vet ikke dette er riktig måte å gjøre oppgaven på)
     * @param student
     * @return index til student den blir lagt i
     * Vet ikke noe om kollisjoner, bruker jo addLast()-metode
     */
    public int annenPut(String student) {
        int m = hashtable.length;
        int key = hashCode(student, m); // viktig at denne blir jevnt fordelt
        ListIterator<String> iterator = hashtable[key].listIterator();

        while (iterator.hasNext()) { // for å telle antall kollisjoner
            iterator.next();
            collisions++;
        }
        hashtable[key].addLast(student); // får man egentlig kollisjoner med addLast() ??
        numberOfElements++;
        return key;
    }

    public double lastFaktor() {
        return (double) numberOfElements/ (double) hashTableLength;
    }

    public void readFromFile(String filename) throws IOException {
        BufferedReader b = new BufferedReader(new FileReader(new File(filename)));
        String student;
        try {
            while((student = b.readLine()) != null ){
                students.add(student);
            }
        } catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        } finally {
            if (b != null) {
                b.close();
            }
        }
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
