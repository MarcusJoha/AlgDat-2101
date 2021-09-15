package oblig4;

public class CircularList {

    private PrisonerNode head;
    private PrisonerNode tail; // peker tilbake på head
    private int size;


    /*
    Hvordan tar jeg og forandrer hvordan
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
    /*
    Burde egentlig ha en meotde for å finne previous
    eller kan jo lagre dette her når jeg itererr gjennom listen
    hvordan sletter man en node i

    todo: finn ut hvordan jeg henter ut den noden jeg skal slette

    */

    /**
     *
     * @param name
     * @return slettet node
     * Sletter node i sirkulær liste basert på navn til Prisoner
     */

    public PrisonerNode deleteNode(String name) {
        Prisoner deletedPrisoner = new Prisoner(name);
        PrisonerNode deletedNode = new PrisonerNode(deletedPrisoner);
        PrisonerNode current = head;
        PrisonerNode next;

        if (isEmpty()) {
            return null;
        }
        // Hvis det er head som skal slettes
        if (current.equals(deletedNode)) {
            next = current.getNext();
            head = next;
            tail.setNext(head);

            size--;
            return current;

        } else {
            current = current.getNext();
            while (!current.getPrisoner().equals(head.getPrisoner())) {
                //previous = current;
                current = current.getNext();
                next = current.getNext();

                if (next.equals(deletedNode)) { // java garbage collector
                    // hvis node som skal slettes er tail
                    if (next.getPrisoner().equals(tail.getPrisoner())) {
                        tail = current;
                        tail.setNext(head);
                        size--;
                        return next;
                    }

                    System.out.println("vi er her");
                  current.setNext(next.getNext());
                  size--;
                  return tail;
                }
            }
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

    // todo: legger til prisoners til front. siste element sin next peker hele tiden til head
    //  slik blir det en sirkulær liste.



}
