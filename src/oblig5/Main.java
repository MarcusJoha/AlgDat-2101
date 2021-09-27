package oblig5;

public class Main {
    public static void main(String[] args) {

        Hashtable tb = new Hashtable(5);

        String navn = "Marcus Johannessen";


        System.out.println(tb.put(navn));
        System.out.println(tb.put("Lukas"));
        System.out.println(tb.put("Henrik"));
        System.out.println(tb.put("Pablo"));
        System.out.println(tb.put("Jonny Bravo"));
        // System.out.println("Collisons: " + Hashtable.collisions);

        // Funker, fette nice!!

        tb.printChainedHashtable();

    }
}


/**
 * load factor: ant_items/capacity
 * vil ikke ha for lav, heller ikke for høy: 75 %
 *
 */