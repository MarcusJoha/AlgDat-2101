package oblig4;

import java.util.Objects;

public class PrisonerNode {

    private Prisoner prisoner;
    private PrisonerNode next;
    private PrisonerNode previous; // ikke sikkert jeg bruker denne


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

    public PrisonerNode getPrevious() {
        return previous;
    }

    public void setPrevious(PrisonerNode previous) {
        this.previous = previous;
    }




    @Override
    public String toString() {
        return "prisoner: " + prisoner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrisonerNode)) return false;
        PrisonerNode that = (PrisonerNode) o;
        return Objects.equals(prisoner, that.prisoner);
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
