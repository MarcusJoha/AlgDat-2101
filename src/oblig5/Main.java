package oblig5;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        // todo: synes jeg leste at størrelse må være 2'er potens, fikser i morgen
        //Hashtable tb = new Hashtable(15); // må se litt mer må hash funksjon


        Hashtable studenHashTable = new Hashtable("src/oblig5/students.txt"); // løsninger på lesing av fil

        studenHashTable.printChainedHashtable();
        System.out.println();
        System.out.println("Last faktor: " + studenHashTable.lastFaktor());
        System.out.println("Antall studender: " + studenHashTable.getNumberOfElements());
        System.out.println("Størrelse på arraylist studender fra fil blir lagt inn i: " + studenHashTable.sizeOfArListStudents());
        System.out.println("Kollisjoner: " + studenHashTable.getCollisions());


    }
}