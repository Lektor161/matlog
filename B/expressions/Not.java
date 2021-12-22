package expressions;

import java.util.Objects;

public class Not implements Expression {
    public final Expression expression;

    public Not(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return String.format("(!%s)", expression.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Not not = (Not) o;
        return Objects.equals(expression, not.expression);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression);
    }

    @Override
    public String toHumanString() {
        return String.format("(%s -> _|_)", expression.toHumanString());
    }
}
