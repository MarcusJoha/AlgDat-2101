package oblig5.oppg2;

public class HashTable {

    //private final int A = (Math.sqrt(5) - 1)/2;
    private static final long A = 2654435769L; // fra boken, 32-bitts prosessor
    private int[] hashTable;
    private int x;

    public HashTable(int[] hashTable) {
        this.hashTable = hashTable;
    }


    public int[] getHashTable() {
        return hashTable;
    }

    /**
     * @param k
     * @return
     * dårlig hvis m er 2'er potens
     * Dårlig hvis m er 10,100,1000..h avhenger av de siste siferene
     * gode verdier for m er primtall som ikke burde ligge for nær noen 2 er potens ("for nær??")
     */
    public int divHash(int k, int m) {
        return (k%(m-1)) + 1;
    }

    /**
     *
     * @param k
     * @return hash verdi
     * A som ligger mellom 0 og 1 ganges med 2^x
     * x - slik at vi får nærmeste 2'er potens
     */
    public int multHash(int k, int x) {
        //int A = (int)(0.5*Math.pow(2.0, 32.0)*(Math.sqrt(5) - 1));
        return (int) (k * A >> (32-x));
    }

    public int linearProbing(int h, int i, int m) {
        return (h + i) % m;
    }



    /**
     * Inner class Linear probing
     */
    static class LinearProbing extends HashTable {
        private int collisions;


        public LinearProbing(int[] hashTable) {
            super(hashTable);
        }

        @Override
        public int[] getHashTable() {
            return super.getHashTable();
        }

        public int getCollisions() {
            return collisions;
        }

        // put metode ved bruk av linær probing
        public int put(int k, int[] hashTable) {
            int m = hashTable.length;
            int hashVal1 = divHash(k, m);
            int j; // so that I can return value
            if (hashTable[hashVal1] == 0) { // hvis ledig
                hashTable[hashVal1] = k; // leg til element
                return hashVal1;

            } else { // hvis ikke ledig løser med probing
                collisions++;
                int i = 0;
                while (true) {
                    j = linearProbing(hashVal1, i, m);
                    if (hashTable[j] == 0) {
                        hashTable[j] = k;
                        break; // found free spot, break out of loop
                    }
                    collisions++;
                    i++;
                }
            }
            return j;
        }
    }

    /**
     * Inner class for Quadratic probing
     */
    static class QuadraticProbing extends HashTable {

        private int collisions;

        private final int k1 = 3;
        private final int k2 = 5;

        public QuadraticProbing(int[] hashTable) {
            super(hashTable);
        }

        @Override
        public int[] getHashTable() {
            return super.getHashTable();
        }

        public int getCollisions() {
            return collisions;
        }

        public int quadraticProbing(int h, int i, int m) {
            return (h + k1*i + k2*i)%m;
        }

        // put-method for quadratic probing
        public int put(int k, int[] hashTable) {
            int m = hashTable.length;
            // todo: bruke java sin hashfunksjon
            int hashVal1 = divHash(k, m);
            int j; // so that I can return value
            if (hashTable[hashVal1] == 0) { // hvis ledig
                hashTable[hashVal1] = k; // leg til element
                return hashVal1;

            } else { // hvis ikke ledig løser med probing
                collisions++;
                int i = 0;
                while (true) {
                    j = quadraticProbing(hashVal1, i, m);
                    if (hashTable[j] == 0) { // found free spot
                        hashTable[j] = k;
                        break; // break out of loop
                    }
                    collisions++;
                    i++;
                }
            }
            return j;
        }
    }


    /**
     * Inner class for hashtable
     */
    static class DobbelHash extends HashTable {
        private int collisions;
        private int[] hashTable;

        public DobbelHash(int[] hashTable) {
            super(hashTable);
        }

        @Override
        public int[] getHashTable() {
            return super.getHashTable();
        }

        public int getCollisions() {
            return collisions;
        }

        // hash-funksjon
        public int doubleHashProbing(int h1, int h2, int i, int hashtableLength) {
            return (h1 + i*h2) % hashtableLength;
        }

        // put metode ved bruk av dobbelhash probing
        public int put(int k, int[] hashTable) {
            int m = hashTable.length;
            int hash1 = divHash(k, m); // todo: sjekke ut java sin hashfunksjon
            int j; // so that I can return value
            if (hashTable[hash1] == 0) { // hvis ledig
                hashTable[hash1] = k; // leg til element
                return hash1;

            } else { // hvis ikke ledig løser med probing
                collisions++;
                int hash2 = divHash(k, m);
                int i = 0;
                while (true) {
                    j = doubleHashProbing(hash1, hash2, i, m);
                    //System.out.println(j);
                    if (hashTable[j] == 0) { // found free spot
                        hashTable[j] = k;
                        break; // break out of loop
                    }
                    collisions++;
                    i++;
                }
            }
            return j;
        }
    }

}

/* Notater til oppgave, forelesning og bok

k - verdier?? hva skal k1 og k2 være??




 */

























