package oblig4;

import java.util.AbstractList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        CircularList prisoners = new CircularList();
        fillData(prisoners, 10);


        Prisoner node = new Prisoner("Halla");
        prisoners.addNode(node);

        System.out.println("head: " + prisoners.getHead().getPrisoner());
        prisoners.printList();
        System.out.println();
        System.out.println();
        System.out.println(prisoners.getSize());
        prisoners.deleteNode(prisoners.getHead().getPrisoner().getName());
        System.out.println(prisoners.getSize());
        prisoners.printList();

        System.out.println();
        System.out.println("New head: " + prisoners.getHead().getPrisoner());

        prisoners.deleteNode("Prisoner6");
        System.out.println(prisoners.getSize());
        System.out.println();
        //prisoners.printList();

        prisoners.deleteNode(prisoners.getTail().getPrisoner().getName());
        System.out.println(prisoners.getSize());
        prisoners.printList();
        System.out.println("tail: " + prisoners.getTail());
        System.out.println("head: " + prisoners.getHead());


    }

    public static void fillData(CircularList prisoners, int antall) {

        prisoners.addNode(new Prisoner("Josephit"));
        String navn = "Prisoner";

        for (int i = 1; i <= antall; i++) {
            String number = String.valueOf(i);
            String navnNumber = navn.concat(number);
            prisoners.addNode(new Prisoner(navnNumber));
        }
    }
}
