package expression.generic.operations;

public class Divide<T> extends BinaryOperation<T> {
    public Divide(MyExpression<T> x, MyExpression<T> y) {
        super(x, y);
    }

    protected String getSymbol() {
        return " / ";
    }

    protected T operation(T x, T y) {
        return calculator.divide(x, y);
    }
}
