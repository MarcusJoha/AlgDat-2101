package oblig4;

import java.util.Date;

public class Main {
    public static void main(String[] args) {

        /*
        TODO: kan lage en raskere algoritme der selve sletting og josephus algoritme er det samme
            problemet nu er at hver gang jeg sletter så må jeg traversere listen igjen.
         */

        CircularList prisoners = new CircularList();

        fillData(prisoners, 30_000); // Antall fanger man vil ha

        PrisonerNode vinner;

        Date start = new Date();
        int runder = 0;
        double tid;
        Date slutt;
        do {
            joesphus(prisoners);
            slutt = new Date();
            ++runder;

        } while (slutt.getTime() - start.getTime() < 1000);
        tid = (double) (slutt.getTime()- start.getTime())/runder;


        System.out.println("Gjennomsnittlige millisekunder pr. runde: " + tid);

        //System.out.println("vinner: " + vinner.getPrisoner().getName());
        //System.out.println();
        //prisoners.printList();



        /*
        // Test for å se om prisoners faktisk er en sirkulær liste
        PrisonerNode yo = prisoners.getHead();
        while (true) {
            yo = yo.getNext();
            System.out.println(yo);
        }
         */
    }

    /**
     *
      * @param prisonerList
     * @return liste av fanger. Består bare av 1 og det er vinneren
     */
    public static PrisonerNode joesphus(CircularList prisonerList) {
        PrisonerNode current = prisonerList.getHead();
        int die = 1;
        while (prisonerList.getSize() > 1) { // todo: fiks denne, henger seg opp for noen antall
            current = current.getNext(); // increments current
            die++;
            if (die%4 == 0) {
                prisonerList.deleteNode(current.getPrisoner().getName());
                // Test for å se om rekkefølgen av de som dør stemmer med boken (10 fanger da)
                // System.out.println("Hvem dør: " + current.getPrisoner().getName());
            }
        }
        return prisonerList.getHead(); // Den som overlever.
    }

    /**
     *
     * @param prisoners
     * @param antall
     * fill data with prisoners
     */
    public static void fillData(CircularList prisoners, int antall) {

        //prisoners.addNode(new Prisoner("Josephus"));
        String navn = "Prisoner";

        for (int i = 1; i <= antall; i++) {
            String number = String.valueOf(i);
            String navnNumber = navn.concat(number);
            prisoners.addNode(new Prisoner(navnNumber));
        }
    }
}
