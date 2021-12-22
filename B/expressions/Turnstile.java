package expressions;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Turnstile implements Expression {
    public final List<Expression> hypots;
    public final Expression expression;

    public Turnstile(Expression ... exprs) {
        this.hypots = Arrays.stream(exprs).collect(Collectors.toList());
        this.hypots.remove(exprs.length - 1);
        this.expression = exprs[exprs.length - 1];
    }


    public Turnstile(List<Expression> hypots, Expression expression) {
        this.hypots = hypots;
        this.expression = expression;
    }

    public Turnstile(Expression hypot, Expression expression) {
        this(Collections.singletonList(hypot), expression);
    }


    public Turnstile(Expression expression) {
        this(Collections.emptyList(), expression);
    }

    @Override
    public String toString() {
        return String.format("|- %s; %s",
                hypots.stream().map(Expression::toString).collect(Collectors.joining(", ")),
                expression.toString());
    }

    @Override
    public String toHumanString() {
        if (hypots.isEmpty()) {
            return String.format("|- %s", expression.toHumanString());
        }
        return String.format("%s |- %s",
                hypots.stream().map(Expression::toHumanString).collect(Collectors.joining(", ")),
                expression.toHumanString());
    }

    public String toHumanString(List<Expression> addHypotes) {
        if (hypots.isEmpty() && addHypotes.isEmpty()) {
            return String.format("|- %s", expression.toHumanString());
        }
        return String.format("%s |- %s",
                Stream.concat(
                        hypots.stream(), addHypotes.stream()).
                        map(Expression::toHumanString).
                        collect(Collectors.joining(", ")
                        ),
                expression.toHumanString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Turnstile turnstile = (Turnstile) o;

        if (hypots.size() != turnstile.hypots.size() || !Objects.equals(expression, turnstile.expression)) {
            return false;
        }
        for (int i = 0; i < hypots.size(); i++) {
            if (!Objects.equals(hypots.get(i), turnstile.hypots.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hypots, expression);
    }
}
