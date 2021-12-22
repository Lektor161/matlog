package proof;

import expressions.Expression;
import expressions.Turnstile;

import java.util.*;
import java.util.stream.Collectors;

public class NaturalProof {
    public final List<NaturalProof> conclusions;
    public final Turnstile expression;
    public final Type type;

    public NaturalProof(Type type, Turnstile expression, NaturalProof... conclusions) {
        this.conclusions = Arrays.stream(conclusions).collect(Collectors.toList());
        this.expression = expression;
        this.type = type;
    }

    public NaturalProof(Turnstile expression) {
        conclusions = Collections.emptyList();
        type = Type.AX;
        this.expression = expression;
    }

    public void print(List<Expression> hypothesis, int lvl) {
        for (NaturalProof proof : conclusions) {
            proof.print(hypothesis, lvl + 1);
        }
        System.out.println(String.format(
                "[%d] %s [%s]",
                lvl,
                expression.toHumanString(hypothesis),
                type.toString())
        );
    }
}
