package expression.generic.operations;

public class Negate<T> extends UnaryOperation<T> {
    public Negate(MyExpression<T> a) {
        super(a);
    }

    protected String getSymbol() {
        return "-";
    }

    protected T operation(T x) {
        return calculator.negate(x);
    }
}
