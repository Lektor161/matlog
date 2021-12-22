import utils.AxiomsChecker;
import utils.BuildAxiomProof;
import expressions.Expression;
import expressions.Turnstile;
import proof.NaturalProof;
import parser.Parser;
import utils.MakeProofUtils;

import java.util.*;
import java.util.stream.Collectors;

public class MainB {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Parser parser = new Parser();
        Turnstile mainExpression = (Turnstile) parser.parse(scanner.nextLine());
        List<Expression> list = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String string = scanner.nextLine();
            if (string.isEmpty()) {
                break;
            }
            list.add(parser.parse(string));
        }

        List<List<Integer>> proofPlan;
        try {
            proofPlan = AxiomsChecker.buildProofPlan(mainExpression.hypots, list, mainExpression.expression);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            return;
        }

        NaturalProof result = BuildAxiomProof.buildProof(proofPlan, list, list.size() - 1);
        result.print(mainExpression.hypots, 0);
    }
}
