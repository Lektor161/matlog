package expressions;

import java.util.List;

public class Or extends BinOperation {
    public Or(Expression l, Expression r) {
        super(l, r);
    }

    @Override
    protected String getOp() {
        return "|";
    }
}
