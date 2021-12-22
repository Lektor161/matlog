package expressions;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Impl extends BinOperation {
    public Impl(Expression l, Expression r) {
        super(l, r);
    }

    @Override
    protected String getOp() {
        return "->";
    }

}
