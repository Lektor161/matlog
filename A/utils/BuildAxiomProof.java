package utils;

import expressions.*;
import proof.NaturalProof;

import java.util.List;

public class BuildAxiomProof {

    // a -> b -> a
    static NaturalProof buildAxiom1(Expression a, Expression b) {
        return MakeProofUtils.intrImpl(
                MakeProofUtils.intrImpl(
                        MakeProofUtils.getAxiom(new Turnstile(a, b, a)),
                        b
                ),
                a
        );
    }

    // (a -> b) -> (a -> b -> c) -> (a -> c)
    static NaturalProof buildAxiom2(Expression a, Expression b, Expression c) {
        Impl ab = new Impl(a, b);
        Impl abc = new Impl(a, new Impl(b, c));

        // |- (a->b) -> (a->b->c) -> (a->c)
        return MakeProofUtils.intrImpl(
                // a->b |- (a->b->c) -> (a->c)
                MakeProofUtils.intrImpl(
                        // a->b, a->b->c |- a->c
                        MakeProofUtils.intrImpl(
                                // a, a->b, a->b->c |- c
                                MakeProofUtils.eraseImpl(
                                        // a, a->b, a->b->c |- b->bc
                                        MakeProofUtils.eraseImpl(
                                                MakeProofUtils.getAxiom(new Turnstile(a, ab, abc, abc)),
                                                MakeProofUtils.getAxiom(new Turnstile(a, ab, abc, a))
                                        ),
                                        // a, a->b, a->b->c |- b
                                        MakeProofUtils.eraseImpl(
                                                MakeProofUtils.getAxiom(new Turnstile(a, ab, abc, ab)),
                                                MakeProofUtils.getAxiom(new Turnstile(a, ab, abc, a))
                                        )
                                ), a
                        ), abc
                ), ab
        );
    }

    // a -> b -> a & b
    static NaturalProof buildAxiom3(Expression a, Expression b) {
        // |- a -> b -> a&b
        return MakeProofUtils.intrImpl(
                MakeProofUtils.intrImpl(
                        // a, b |- &b
                        MakeProofUtils.intrAnd(
                                MakeProofUtils.getAxiom(new Turnstile(a, b, a)),
                                MakeProofUtils.getAxiom(new Turnstile(a, b, b))
                        ), b
                ), a
        );
    }

    // a & b -> a
    static NaturalProof buildAxiom4(Expression a, Expression b) {
        Expression aAndB = new And(a, b);
        return MakeProofUtils.intrImpl(
                MakeProofUtils.eraseLeftAnd(
                        MakeProofUtils.getAxiom(new Turnstile(aAndB, aAndB))
                ),
                new And(a, b)
        );
    }

    // a & b -> b
    static NaturalProof buildAxiom5(Expression a, Expression b) {
        Expression aAndB = new And(a, b);
        return MakeProofUtils.intrImpl(
                MakeProofUtils.eraseRightAnd(
                        MakeProofUtils.getAxiom(new Turnstile(aAndB, aAndB))
                ),
                new And(a, b)
        );
    }

    // a -> a | b
    static NaturalProof buildAxiom6(Expression a, Expression b) {
        return MakeProofUtils.intrImpl(
                MakeProofUtils.intrLeftOr(
                        MakeProofUtils.getAxiom(new Turnstile(a, a)),
                        b
                ), a
        );
    }

    // b -> a | b
    static NaturalProof buildAxiom7(Expression a, Expression b) {
        return MakeProofUtils.intrImpl(
                MakeProofUtils.intrRightOr(
                        MakeProofUtils.getAxiom(new Turnstile(b, b)),
                        a
                ), b
        );
    }

    // (a -> c) -> (b -> c) -> (a | b -> c)
    static NaturalProof buildAxiom8(Expression a, Expression b, Expression c) {
        Or ab = new Or(a, b);
        Impl ac = new Impl(a, c);
        Impl bc = new Impl(b, c);
        // |- (a->c) -> (b->c) -> (a|b -> c)
        return MakeProofUtils.intrImpl(
                // a->c |- (b->c) -> (a|b -> c)
                MakeProofUtils.intrImpl(
                        // a->c, (b->c) |- a|b -> c
                        MakeProofUtils.intrImpl(
                                // a->c, b->c, a|b |-  c
                                MakeProofUtils.eraseOr(
                                        // a->c, b->c, a|b, a |- c
                                        MakeProofUtils.eraseImpl(
                                                MakeProofUtils.getAxiom(new Turnstile(ac, bc, ab, a, ac)),
                                                MakeProofUtils.getAxiom(new Turnstile(ac, bc, ab, a, a))
                                        ),
                                        // a->c, b->c, a|b, b |- c
                                        MakeProofUtils.eraseImpl(
                                                MakeProofUtils.getAxiom(new Turnstile(ac, bc, ab, b, bc)),
                                                MakeProofUtils.getAxiom(new Turnstile(ac, bc, ab, b, b))
                                        ),
                                        // a->c, b->c, a|b |- a|b
                                        MakeProofUtils.getAxiom(new Turnstile(ac, bc, ab, ab))
                                ), ab
                        ), bc
                ), ac
        );
    }

    // (a -> b) -> (a -> !b) -> !a
    static NaturalProof buildAxiom9(Expression a, Expression b) {
        Expression notB = new Impl(b, Bottom.BOTOM);
        Expression ab = new Impl(a, b);
        Expression aNotB = new Impl(a, notB);

        // |- (a->b) -> (a->!b) -> !a
        return MakeProofUtils.intrImpl(
                // a->b |- a->!b -> !a
                MakeProofUtils.intrImpl(
                        // a->b, a->!b |- !a
                        MakeProofUtils.intrImpl(
                                // a->b, a->!b, a |- _|_
                                MakeProofUtils.eraseImpl(
                                        // a->b, a->!b, a |- b -> _|_
                                        MakeProofUtils.eraseImpl(
                                                // a->b, a->!b, a |- a -> !b
                                                MakeProofUtils.getAxiom(new Turnstile(ab, aNotB, a, aNotB)),
                                                // a->b, a->!b, a |- a
                                                MakeProofUtils.getAxiom(new Turnstile(ab, aNotB, a, a))
                                        ),
                                        // a->b, a->!b, a |- b
                                        MakeProofUtils.eraseImpl(
                                                MakeProofUtils.getAxiom(new Turnstile(ab, aNotB, a, ab)),
                                                MakeProofUtils.getAxiom(new Turnstile(ab, aNotB, a, a))
                                        )
                                ), a
                        ), aNotB
                ), ab
        );
    }

    // a -> !a -> b
    static NaturalProof buildAxiom10(Expression a, Expression b) {
        Expression notA = new Impl(a, Bottom.BOTOM);
        // |- a -> !a -> b
        return MakeProofUtils.intrImpl(
                // a|- !a -> b
                MakeProofUtils.intrImpl(
                        // a, !a |- b
                        MakeProofUtils.eraseBottom(
                                // a, !a |- _|_
                                MakeProofUtils.eraseImpl(
                                        MakeProofUtils.getAxiom(new Turnstile(a, notA, notA)),
                                        MakeProofUtils.getAxiom(new Turnstile(a, notA, a))
                                ), b
                        ), notA
                ), a
        );
    }


    public static NaturalProof buildProof(List<List<Integer>> proofPlan, List<Expression> proof, int id) {
        Expression expression = proof.get(id);
        if (proofPlan.get(id).isEmpty()) {
            int axiomId = AxiomsChecker.getAxiomNumber(expression);
            if (axiomId == -1) {
                return new NaturalProof(new Turnstile(expression));
            }
            return generateProofAxiom(expression, axiomId);
        }
        int k = proofPlan.get(id).get(0);
        int j = proofPlan.get(id).get(1);
        return MakeProofUtils.eraseImpl(
                buildProof(proofPlan, proof, k),
                buildProof(proofPlan, proof, j)
        );
    }

    public static NaturalProof generateProofAxiom(Expression expression, int number) {
        Impl expr = (Impl) expression;
        if (number == 1) {
            return BuildAxiomProof.buildAxiom1(expr.l, ((Impl) expr.r).l);
        } else if (number == 2) {
            Impl ab = (Impl) expr.l;
            Impl bc = (Impl) ((Impl) expr.r).r;
            return BuildAxiomProof.buildAxiom2(ab.l, ab.r, bc.r);
        } else if (number == 3) {
            return BuildAxiomProof.buildAxiom3(expr.l, ((Impl) expr.r).l);
        } else if (number == 4) {
            And aAndB = (And) expr.l;
            return BuildAxiomProof.buildAxiom4(aAndB.l, aAndB.r);
        } else if (number == 5) {
            And aAndB = (And) expr.l;
            return BuildAxiomProof.buildAxiom5(aAndB.l, aAndB.r);
        } else if (number == 6) {
            Or aOrB = (Or) expr.r;
            return BuildAxiomProof.buildAxiom6(aOrB.l, aOrB.r);
        } else if (number == 7) {
            Or aOrB = (Or) expr.r;
            return BuildAxiomProof.buildAxiom7(aOrB.l, aOrB.r);
        } else if (number == 8) {
            Impl ac = (Impl) expr.l;
            Impl bc = (Impl) ((Impl) expr.r).l;
            return BuildAxiomProof.buildAxiom8(ac.l, bc.l, ac.r);
        } else if (number == 9) {
            Impl ab = (Impl) expr.l;
            return BuildAxiomProof.buildAxiom9(ab.l, ab.r);
        } else if (number == 10) {
            return BuildAxiomProof.buildAxiom10(expr.l, ((Impl) expr.r).r);
        }
        return null;
    }
}
