package expressions;

public class Bottom implements Expression {
    public static final Bottom BOTOM = new Bottom();

    private Bottom() {

    }


    @Override
    public String toString() {
        return "_|_";
    }

    @Override
    public String toHumanString() {
        return toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        return this == obj;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
