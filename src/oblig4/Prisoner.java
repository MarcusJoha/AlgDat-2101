package oblig4;

import java.util.Objects;

public class Prisoner {

    private String name;

    public Prisoner(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Name: " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Prisoner)) return false;
        Prisoner prisoner = (Prisoner) o;
        return Objects.equals(name, prisoner.name);
    }
}
