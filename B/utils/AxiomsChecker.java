package utils;

import expressions.*;

import java.util.*;

public class AxiomsChecker {
    private static boolean notImpl(Expression expression) {
        if (expression == null) {
            return true;
        }
        return !(expression instanceof Impl);
    }

    private static boolean notImpl3(Expression expression) {
        if (notImpl(expression)) {
            return true;
        }
        return notImpl(((Impl) expression).r);
    }

    private static List<Expression> getImpl3(Impl expression) {
        Impl impl = (Impl) expression.r;
        return Arrays.asList(expression.l, impl.l, impl.r);
    }

    // a -> b -> a
    public static boolean check1(Expression expression) {
        if (notImpl3(expression)) {
            return false;
        }
        List<Expression> list = getImpl3((Impl) expression);
        return Objects.equals(list.get(0), list.get(2));
    }

    // (a -> b) -> (a -> b -> c) -> (a -> c)
    public static boolean check2(Expression expression) {
        if (notImpl3(expression)) {
            return false;
        }
        List<Expression> list = getImpl3((Impl) expression);
        if (notImpl(list.get(0)) || notImpl3(list.get(1)) || notImpl(list.get(2))) {
            return false;
        }
        Impl A = (Impl) list.get(0);
        List<Expression> B = getImpl3((Impl) list.get(1));
        Impl C = (Impl) list.get(2);

        return Objects.equals(A.l, B.get(0)) && Objects.equals(A.l, C.l) &&
                Objects.equals(A.r, B.get(1)) && Objects.equals(B.get(2), C.r);
    }

    // a -> b -> a & b
    public static boolean check3(Expression expression) {
        if (notImpl3(expression)) {
            return false;
        }
        List<Expression> list = getImpl3((Impl) expression);
        if (!(list.get(2) instanceof And)) {
            return false;
        }
        And and = (And) list.get(2);
        return Objects.equals(list.get(0), and.l) && Objects.equals(list.get(1), and.r);
    }

    // a & b -> a
    public static boolean check4(Expression expression) {
        if (notImpl(expression)) {
            return false;
        }
        Impl impl = (Impl) expression;
        if (!(impl.l instanceof And)) {
            return false;
        }
        And A = (And) impl.l;
        Expression B = impl.r;
        return Objects.equals(A.l, B);
    }

    // a & b -> b
    public static boolean check5(Expression expression) {
        if (notImpl(expression)) {
            return false;
        }
        Impl impl = (Impl) expression;
        if (!(impl.l instanceof And)) {
            return false;
        }
        And A = (And) impl.l;
        Expression B = impl.r;
        return Objects.equals(A.r, B);
    }

    // a -> a | b
    public static boolean check6(Expression expression) {
        if (notImpl(expression)) {
            return false;
        }
        Impl impl = (Impl) expression;
        if (!(impl.r instanceof Or)) {
            return false;
        }
        Expression A = impl.l;
        Or B = (Or) impl.r;
        return Objects.equals(A, B.l);
    }

    public static boolean check7(Expression expression) {
        if (notImpl(expression)) {
            return false;
        }
        Impl impl = (Impl) expression;
        if (!(impl.r instanceof Or)) {
            return false;
        }
        Expression A = impl.l;
        Or B = (Or) impl.r;
        return Objects.equals(A, B.r);
    }

    // (a -> c) -> (b -> c) -> (a | b -> c)
    public static boolean check8(Expression expression) {
        if (notImpl3(expression)) {
            return false;
        }
        List<Expression> list = getImpl3((Impl) expression);
        if (notImpl(list.get(0)) || notImpl(list.get(1)) || notImpl(list.get(2))) {
            return false;
        }
        Impl A = (Impl) list.get(0);
        Impl B = (Impl) list.get(1);
        Impl C = (Impl) list.get(2);
        if (!(C.l instanceof Or)) {
            return false;
        }
        Or AB = (Or) C.l;

        return Objects.equals(A.r, B.r) && Objects.equals(B.r, C.r) &&
                Objects.equals(A.l, AB.l) && Objects.equals(B.l, AB.r);
    }

    // (a -> b) -> (a -> !b) -> !a
    public static boolean check9(Expression expression) {
        if (notImpl3(expression)) {
            return false;
        }
        List<Expression> list = getImpl3((Impl) expression);
        if (notImpl(list.get(0)) || notImpl(list.get(1)) || !(list.get(2) instanceof Not)) {
            return false;
        }

        Impl A = (Impl) list.get(0);
        Impl B = (Impl) list.get(1);
        Not C = (Not) list.get(2);

        if (!(B.r instanceof Not)) {
            return false;
        }
        Not BR = (Not) B.r;

        return Objects.equals(A.l, B.l) && Objects.equals(A.l, C.expression) &&
                Objects.equals(A.r, BR.expression);
    }

    // A -> !A -> B
    public static boolean check10(Expression expression) {
        if (notImpl3(expression)) {
            return false;
        }
        List<Expression> list = getImpl3((Impl) expression);
        if (!(list.get(1) instanceof Not)) {
            return false;
        }
        Not notA = (Not) list.get(1);
        return Objects.equals(notA.expression, list.get(0));
    }

    public static int getAxiomNumber(Expression expression) {
        if (check1(expression)) {
            return 1;
        } else if (check2(expression)) {
            return 2;
        } else if (check3(expression)) {
            return 3;
        } else if (check4(expression)) {
            return 4;
        } else if (check5(expression)) {
            return 5;
        } else if (check6(expression)) {
            return 6;
        } else if (check7(expression)) {
            return 7;
        } else if (check8(expression)) {
            return 8;
        } else if (check9(expression)) {
            return 9;
        } else if (check10(expression)) {
            return 10;
        }
        return -1;
    }

    public static boolean checkAxiom(Expression expression) {
        return getAxiomNumber(expression) != -1;
    }

    public static List<List<Integer>> buildProofPlan(List<Expression> hypots,
                                                     List<Expression> exprs,
                                                     Expression expr) throws RuntimeException {
        if (!exprs.get(exprs.size() - 1).equals(expr)) {
            throw new RuntimeException("The proof does not prove the required expression");
        }
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < exprs.size(); i++) {
            Expression curExpr = exprs.get(i);
            if (hypots.contains(curExpr) || AxiomsChecker.checkAxiom(curExpr)) {
                result.add(Collections.emptyList());
                continue;
            }
            boolean flag = false;
            for (int k = 0; k < i; k++) {
                if (exprs.get(k) instanceof Impl) {
                    Impl impl = (Impl) exprs.get(k);
                    if (exprs.get(i).equals(impl.r)) {
                        for (int j = 0; j < i; j++) {
                            if (impl.l.equals(exprs.get(j))) {
                                result.add(Arrays.asList(k, j));
                                flag = true;
                                break;
                            }
                        }
                    }
                }
                if (flag) {
                    break;
                }
            }
            if (!flag) {
                throw new RuntimeException(String.format("Proof is incorrect at line %d", i + 2));
            }
        }
        return result;
    }

}
