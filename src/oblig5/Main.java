package oblig5;

public class Main {
    public static void main(String[] args) {

        Hashtable tb = new Hashtable(20); // må se litt mer må hash funksjon

        String navn = "Marcus Johannessen";


        System.out.println(tb.annenPut(navn));
        System.out.println(tb.annenPut("Runar"));
        System.out.println(tb.annenPut("Henrik"));
        System.out.println(tb.annenPut("Pablo"));
        System.out.println(tb.annenPut("Jonny Bravo"));




        System.out.println("ja hver får vi nå: " + tb.getStudent("Henrik"));
        tb.printChainedHashtable();
        System.out.println(tb.getIndexOf("Runar"));
        System.out.println(Hashtable.collisions);

        System.out.println("lastfaktor: " + tb.lastFaktor());


    }
}


/**
 * load factor: ant_items/capacity
 * vil ikke ha for lav, heller ikke for høy: 75 %
 *
 */