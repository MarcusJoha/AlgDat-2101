package oblig4;

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
    public PrisonerNode deleteNode(String name) { // forandrer den til å void???
        Prisoner deletedPrisoner = new Prisoner(name);
        PrisonerNode deletedNode = new PrisonerNode(deletedPrisoner);
        PrisonerNode current = head;
        PrisonerNode next;

        if (isEmpty()) { // hvis listen er tom
            return null;
        }
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
                    if (next.getPrisoner().equals(tail.getPrisoner())) { // 2n + 2n (equals)
                        tail = current; // n
                        tail.setNext(head); // n
                        size--; // n
                        return next; // n

                        // sum: 8n
                    }
                    // Den som hoppes over tar java garbage collector seg av
                    current.setNext(next.getNext());
                    size--;
                    return tail;
                }

            } while (!current.getPrisoner().equals(head.getPrisoner()));
        }
        return null;
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
