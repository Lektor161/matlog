package expressions;

import java.util.Objects;

public abstract class BinOperation implements Expression {
    public final Expression l;
    public final Expression r;

    public BinOperation(Expression l, Expression r) {
        this.l = l;
        this.r = r;
    }

    abstract protected String getOp();

    public String toHumanString() {
        return String.format("(%s %s %s)", l.toHumanString(), getOp(), r.toHumanString());
    }

    @Override
    public String toString() {
        return String.format("(%s,%s,%s)", getOp(), l.toString(), r.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BinOperation that = (BinOperation) o;
        return Objects.equals(l, that.l) && Objects.equals(r, that.r);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOp(), l, r);
    }
}
