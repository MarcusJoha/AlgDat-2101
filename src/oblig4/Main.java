package oblig4;

import java.util.AbstractList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        CircularList prisoners = new CircularList();

        fillData(prisoners, 10); // Antall fanger man vil ha
        prisoners.printList();
        System.out.println();
        System.out.println("Vinner: " + joesphus(prisoners).getPrisoner().getName());
        System.out.println();
        prisoners.printList(); // Altså vinneren som står igjen





        /*
        // Test for å se om prisoners faktisk er en sirkulær liste
        PrisonerNode yo = prisoners.getHead();
        while (true) {
            yo = yo.getNext();
            System.out.println(yo);
        }
         */
    }


    public static void fillData(CircularList prisoners, int antall) {

        //prisoners.addNode(new Prisoner("Josephus"));
        String navn = "Prisoner";

        for (int i = 1; i <= antall; i++) {
            String number = String.valueOf(i);
            String navnNumber = navn.concat(number);
            prisoners.addNode(new Prisoner(navnNumber));
        }
    }


    /**
     *
      * @param prisonerList
     * @return liste av fanger. Består bare av 1 og det er vinneren
     */
    public static PrisonerNode joesphus(CircularList prisonerList) {
        PrisonerNode head = prisonerList.getHead();
        PrisonerNode current = head;
        int die = 1;
        while (!head.getNext().equals(prisonerList.getTail())) {
            die++;
            current = current.getNext(); // increments current
            if (die%4 == 0) {
                prisonerList.deleteNode(current.getPrisoner().getName());
                // Test for å se om rekkefølgen av de som dør stemmer med boken (10 fanger da)
                //System.out.println("Hvem dør: " + current.getPrisoner().getName());
            }
        }
        return prisonerList.getHead(); // Den som overlever.
    }
}
