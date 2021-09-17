package oblig4.oppg1;

public class CircularList {

    private PrisonerNode head;
    private PrisonerNode tail; // peker tilbake på head
    private int size;


    /**
     *
     * @param prisoner
     * legger til ny prisoner
     * legge alltid bakerst slik at den blir tail.
     */
    public void addNode(Prisoner prisoner) {
        PrisonerNode newNode = new PrisonerNode(prisoner);

        if (head == null) { // hvis listen er tom
            head = newNode;
        } else {
            tail.setNext(newNode); // next for tail blir ny node
        }
        tail = newNode; // forandrer tail til ny node
        tail.setNext(head); // tail peker tilbake til head
        size++; // øker størrelse med 1
    }

    /**
     *
     * @param name
     * @return slettet node
     * Sletter node i sirkulær liste basert på navn til Prisoner
     */
    public PrisonerNode deleteNode(String name) {

        Prisoner deletedPrisoner = new Prisoner(name); // 1
        PrisonerNode deletedNode = new PrisonerNode(deletedPrisoner); // 1
        PrisonerNode current = head; // 1
        PrisonerNode next; // 1

        if (isEmpty()) { // hvis listen er tom. En sammenligning og return // 2
            return null;
        }
        // sum = 5

        // Hvis det er head som skal slettes
        if (current.equals(deletedNode)) { // 1 kall, equals: 2
            next = current.getNext(); // 2
            head = next; // 1
            tail.setNext(head); // 1
            size--; // 1
            return current; // 1

            // sum 9

        } else {
            do {
                current = current.getNext(); // 2n
                next = current.getNext(); // 2n
                if (next.equals(deletedNode)) { // 2n ?

                    // hvis node som skal slettes er tail
                    if (next.getPrisoner().equals(tail.getPrisoner())) { // 1 + 2 + 1 (Denne sammenligningen vil bare skje 1 gang)
                        tail = current; // 1
                        tail.setNext(head); // 1
                        size--; // 1
                        return next; // 1

                        // sum: 8
                    }
                    // Den som hoppes over tar java garbage collector seg av
                    // tidskompleksitet for java garbage collector??
                    current.setNext(next.getNext()); // 2
                    size--; // 1
                    return tail; // 1
                    // sum: 4
                }

            } while (!current.getPrisoner().equals(head.getPrisoner())); // n + 2n + n
            // sum while løkke: 4n
        }
        return null; // 1
        // værst tenkelig blir hvis man skal slette tail
        // da må den loope gjennom hele listen og vi får en kjøretid på

        // Sammenlagt: 10n + 15
    }



    public PrisonerNode getHead() {
        return head;
    }

    public PrisonerNode getTail() {
        return tail;
    }

    public int getSize() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void printList() {
        PrisonerNode prisoner = head;
        System.out.println(prisoner.getPrisoner());
        while (prisoner.getNext() != head) {
            prisoner = prisoner.getNext();
            System.out.println(prisoner.getPrisoner());
        }
    }
}
