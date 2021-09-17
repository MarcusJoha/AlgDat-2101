package oblig4.oppg1;

import java.util.Objects;

public class PrisonerNode {

    private Prisoner prisoner;
    private PrisonerNode next;


    public PrisonerNode(Prisoner prisoner) {
        this.prisoner = prisoner;
    }

    public Prisoner getPrisoner() {
        return prisoner;
    }

    public void setPrisoner(Prisoner prisoner) {
        this.prisoner = prisoner;
    }

    public PrisonerNode getNext() {
        return next;
    }

    public void setNext(PrisonerNode next) {
        this.next = next;
    }



    @Override
    public String toString() {
        return "prisoner: " + prisoner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // 2
        if (!(o instanceof PrisonerNode)) return false; // 2
        PrisonerNode that = (PrisonerNode) o; // 1
        return Objects.equals(prisoner, that.prisoner); // 2
        // returnerer max 2
    }

    @Override
    public int hashCode() {
        return Objects.hash(prisoner);
    }

    /*
    Hvis en node peke til head betyr det at den er den bakerste noden
    Trenger egentlig ikke en tail da
     */



}
