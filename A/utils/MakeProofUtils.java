package utils;

import expressions.*;
import proof.NaturalProof;
import proof.Type;

import java.util.ArrayList;
import java.util.List;

public class MakeProofUtils {

    // a |- a
    public static NaturalProof getAxiom(Turnstile expression) {
        return new NaturalProof(expression);
    }

    // |-a->b, |-a => |-b
    public static NaturalProof eraseImpl(NaturalProof a, NaturalProof b) {
        Turnstile A = a.expression;
        return new NaturalProof(Type.EIMPL, new Turnstile(A.hypots, ((Impl)A.expression).r), a, b);
    }

    // a|-b => |-a->b
    public static NaturalProof intrImpl(NaturalProof a, Expression exp) {
        List<Expression> hypots = new ArrayList<>(a.expression.hypots);
        hypots.remove(exp);
        return new NaturalProof(Type.IIMPL, new Turnstile(hypots, new Impl(exp, a.expression.expression)), a);
    }

    // |-a |-b => |-a&b
    public static NaturalProof intrAnd(NaturalProof a, NaturalProof b) {
        Turnstile A = a.expression;
        Turnstile B = b.expression;
        return new NaturalProof(Type.IAND, new Turnstile(A.hypots, new And(A.expression, B.expression)), a, b);
    }

    // |-a&b => |-a
    public static NaturalProof eraseLeftAnd(NaturalProof a) {
        Turnstile A = a.expression;
        return new NaturalProof(Type.ELAND, new Turnstile(A.hypots, ((And)A.expression).l), a);
    }


    // |-a&b => |-b
    public static NaturalProof eraseRightAnd(NaturalProof a) {
        Turnstile A = a.expression;
        return new NaturalProof(Type.ERAND, new Turnstile(A.hypots, ((And) A.expression).r), a);
    }

    // |-a => |-a|b
    public static NaturalProof intrLeftOr(NaturalProof a, Expression expr) {
        Turnstile A = a.expression;
        return new NaturalProof(Type.ILOR, new Turnstile(A.hypots, new Or(A.expression, expr)), a);
    }

    // |-b => |-a|b
    public static NaturalProof intrRightOr(NaturalProof b, Expression expr) {
        Turnstile B = b.expression;
        return new NaturalProof(Type.IROR, new Turnstile(B.hypots, new Or(expr, B.expression)), b);
    }

    // a|-c b|-c |-a|b => |-c
    public static NaturalProof eraseOr(NaturalProof a, NaturalProof b, NaturalProof c) {
        return new NaturalProof(Type.EOR, new Turnstile(c.expression.hypots, a.expression.expression), a, b, c);
    }

    // a|-_|_ -> a|-b
    public static NaturalProof eraseBottom(NaturalProof a, Expression expr) {
        return new NaturalProof(Type.EBOTTOM, new Turnstile(a.expression.hypots, expr), a);
    }
}
