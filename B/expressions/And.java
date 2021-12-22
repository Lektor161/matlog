package expressions;

public class And extends BinOperation {
    public And(Expression l, Expression r) {
        super(l, r);
    }

    @Override
    protected String getOp() {
        return "&";
    }
}
