package parser;


import expressions.*;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    private Tokenizer tokenizer;
    public Expression parse(String s) {
        tokenizer = new Tokenizer(s.replaceAll(" ", ""));
        return parseTurn();
    }

    private Expression parseTurn() {
        if (tokenizer.test("|-")) {
            return new Turnstile(parseImpl());
        }
        List<Expression> list = new ArrayList<>();
        do {
            list.add(parseImpl());
        } while (tokenizer.test(","));
        if (!tokenizer.test("|-")) {
            return list.get(0);
        }

        return new Turnstile(list, parseImpl());
    }

    private Expression parseImpl() {
        List<Expression> list = new ArrayList<>();
        do {
            list.add(parseOr());
        } while (tokenizer.test("->"));

        Expression expression = list.get(list.size() - 1);
        for (int i = list.size() - 2; i >= 0; i--) {
            expression = new Impl(list.get(i), expression);
        }
        return expression;
    }

    private Expression parseOr() {
        List<Expression> list = new ArrayList<>();
        do {
            list.add(parseAnd());
            tokenizer.save();
            if (tokenizer.test("|-")) {
                tokenizer.comeBack();
                break;
            }
        } while (tokenizer.test("|"));

        Expression expression = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            expression = new Or(expression, list.get(i));
        }
        return expression;
    }

    private Expression parseAnd() {
        List<Expression> list = new ArrayList<>();
        do {
            list.add(parseNot());
        } while (tokenizer.test("&"));

        Expression expression = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            expression = new And(expression, list.get(i));
        }
        return expression;
    }

    private Expression parseNot() {
        if (tokenizer.test("!")) {
            return new Not(parseNot());
        }
        return parseVal();
    }

    private Expression parseVal() {
        if (tokenizer.test("(")) {
            Expression expression = parseImpl();
            tokenizer.test(")");
            return expression;
        }
        return new Value(tokenizer.getVal());
    }
}
