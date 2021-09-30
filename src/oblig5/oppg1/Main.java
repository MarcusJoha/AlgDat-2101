package oblig5.oppg1;

public class Main {
    public static void main(String[] args) {

        // todo: synes jeg leste at størrelse må være 2'er potens, fikser i morgen
        //Hashtable tb = new Hashtable(15); // må se litt mer må hash funksjon


        ChainedHashTable studenHashTable = new ChainedHashTable("src/oblig5/oppg1/students.txt"); // løsninger på lesing av fil

        studenHashTable.printChainedHashtable();
        System.out.println();
        System.out.println("Antall studender: " + studenHashTable.getNumberOfElements());
        System.out.println("Størrelse på arraylist studender som leses fra fil (skal være lik ant.studender): " + studenHashTable.sizeOfArListStudents());
        System.out.println("Kollisjoner: " + studenHashTable.getCollisions());
        System.out.println("Last faktor: " + studenHashTable.lastFaktor());
        System.out.println("Kollisjoner per stud =  " + studenHashTable.collisonsPerStud() + " (ikke over 0.4)");

        // oppslag er case sensitiv
        System.out.println("Oppslag: " + studenHashTable.getStudent("Marcus Johannessen") +
                "\nIndex Marcus Johannessen: " + studenHashTable.getIndexOf("Marcus Johannessen"));


        /*
        Fra deg jeg ser på utskrift av studender er det max 3 studender som står på samme index
        Skal prøve litt andre hashfunksjoner for å se om spredning blir bedre
         */

    }
}