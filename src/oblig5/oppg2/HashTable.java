package oppg2;

public abstract class HashTable {

    //private final int A = (Math.sqrt(5) - 1)/2;
    private static final long A = 2654435769L; // fra boken, 32-bitts prosessor
    private static final double AA = (Math.sqrt(5) - 1) / 2;
    private int[] hashTable;
    private int x;
    private int collisions;

    public HashTable(int[] hashTable) {
        this.hashTable = hashTable;
    }


    public int[] getHashTable() {
        return hashTable;
    }

    /**
     * @param k
     * @return dårlig hvis m er 2'er potens
     * Dårlig hvis m er 10,100,1000..h avhenger av de siste siferene
     * gode verdier for m er primtall som ikke burde ligge for nær noen 2 er potens ("for nær??")
     */
    public int divHash(int k, int m) {
        return (k % (m - 1)) + 1;
    }


    // Henrik sin røyk hash metode
    public int h1(int tall, int tableLength) {
        // legger til 1 slik at hashverdi ikke kan bli 0, ødelegger metode for doublehashProbing
        return (int) (tableLength * (AA * tall - (int) (AA * tall))) % tableLength + 1;
    }

<<<<<<< HEAD
=======
    /**
     * @param k
     * @return hash verdi
     * A som ligger mellom 0 og 1 ganges med 2^x
     * x - slik at vi får nærmeste 2'er potens
     */
    public int multHash(int k, int x) {
        return (int) (k * A >> (32 - x));
    }

    /**
     * Abstract method for handling collisions when int are added to hashtables
     *
     * @param k
     * @param hashTable
     * @return
     */
    public abstract int handleCollision(int k, int[] hashTable);

    public int put(int k, int[] hashTable) {
        int m = hashTable.length;
        int hashVal1 = divHash(k, m);
        int j; // so that I can return value
        if (hashTable[hashVal1] == 0) { // hvis ledig
            hashTable[hashVal1] = k; // leg til element
            return hashVal1;

        } else {
            j = handleCollision(k, hashTable);
        }
        return j;
    }
    public abstract int getCollisions();
>>>>>>> 139f539c56de1df38bfe66b35169ce5d2a38c9e8


}
    /**
     * Inner class for linear probing
     */
    class LinearProbing extends HashTable {
        private int collisions;

        public LinearProbing(int[] hashTable) {
            super(hashTable);
        }

        @Override
        public int[] getHashTable() {
            return super.getHashTable();
        }

        @Override
        public int handleCollision(int k, int[] hashTable) {
            int m = hashTable.length;
            int hashVal1 = divHash(k, m);
            int j; // so that I can return value
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
            return j;
        }

        @Override
        public int getCollisions() {
            return collisions;
        }

        public int linearProbing(int h, int i, int m) {
            return (h + i) % m;
        }

    }

    /**
     * Inner class for Quadratic probing
     */
    class QuadraticProbing extends HashTable {

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

        @Override
        public int getCollisions() {
            return collisions;
        }

        public int quadraticProbing(int h, int i, int m) {
            return (h + k1*i + k2*i)%m;
        }

        @Override
        public int handleCollision(int k, int[] hashTable) {
            int m = hashTable.length;
            int hashVal1 = divHash(k, m);
            int j; // so that I can return value

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
            return j;
        }
    }


    /**
     * Inner class for dobbelhash probing
     */
    class DobbelHash extends HashTable {
        private int collisions;

        public DobbelHash(int[] hashTable) {
            super(hashTable);
        }

        @Override
        public int[] getHashTable() {
            return super.getHashTable();
        }


        @Override
        public int getCollisions() {
            return collisions;
        }

        // probe funksjon som bruker to hashverdier
        public int doubleHashProbing(int h1, int h2, int i, int hashtableLength) {

            return (h1 + i*h2) % hashtableLength;
        }

        @Override
        // put metode ved bruk av dobbelhash probing
        public int handleCollision(int k, int[] hashTable) {
            int m = hashTable.length;
            int hash1 = divHash(k, m); // hashvalue based on modulo
            int j; // so that I can return value
                collisions++;
                int hash2 = h1(k, m); // h1 - multiplication hashfunction
                int i = 1;
                while (true) {
                    j = doubleHashProbing(hash1, hash2, i, m);
<<<<<<< HEAD
                    j = Math.abs(j); // for ikke negativ verdi
=======
                    j = Math.abs(j);
                    //System.out.println(j);
>>>>>>> 139f539c56de1df38bfe66b35169ce5d2a38c9e8
                    if (hashTable[j] == 0) { // found free spot
                        hashTable[j] = k;
                        break; // break out of loop
                    }
                    collisions++;
                    i++;
                }
            return j;
        }
    }
