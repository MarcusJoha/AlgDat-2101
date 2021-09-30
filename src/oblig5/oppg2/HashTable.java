package oblig5.oppg2;

public class HashTable {

    private final double A = (Math.sqrt(5) - 1)/2;
    private int[] hashTable;
    private int collisions;
    private int x;

    /**
     *
     * @param capacity
     *
     */
    public HashTable(int capacity) {
        this.hashTable = new int[capacity];
        this.collisions = 0;
    }

    public int getCollisions() {
        return collisions;
    }

    public int[] getHashTable() {
        return hashTable;
    }

    /**
     *
     * @param k
     * @return
     * dårlig hvis m er 2'er potens
     * Dårlig hvis m er 10,100,1000..h avhenger av de siste siferene
     */
    public int divHash(int k, int m) {
        return k%m;
    }

    // Må se på denne! sugde af
    public int multHash(double k) {
        int m = hashTable.length;
        return (int)(m*(k*A - (int)k*A)); // kan ikke kaste til 0
    }

    /**
     *
     * @param k
     * hashing to find position
     * probing for handeling collision
     * Todo: fikse på hash-funksjon
     * Todo: se på probe-funksjon
     */
    public int put(int k) {
        int m = hashTable.length;
        int h = divHash(k, m);
        for (int i = 0; i < m ; i++) {
            //int j = linearProbing()
            int j = quadraticProbing(h, i, m);
            if (hashTable[j] == -1) { // se på denne
                hashTable[j] = k;
                return j;
            }
        }
        return -1; // Fullt
    }

    /**
     *
     * @param h hashvalue
     * @param i index
     * @param m length of hashtable
     * @return value from hashtable
     */
    public int linearProbing(int h, int i, int m) {
        return (h+1) % m;
    }

    /**
     *
     * @param h
     * @param i
     * @param m
     * k1 -
     * k2 -
     */
    public int quadraticProbing(int h, int i, int m) {
        int k1 = 0; // fikser senere
        int k2 = 0; // fikser senere
        return (h + k1*i + k2*i)%m;
    }

    public int doubleProbe() {
        return -1;
    }



}

/* Notater til oppgave, forelesning og bok





 */

























