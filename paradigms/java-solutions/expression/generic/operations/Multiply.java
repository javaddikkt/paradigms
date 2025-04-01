package expression.generic.operations;

public class Multiply<T> extends BinaryOperation<T> {
    public Multiply(MyExpression<T> x, MyExpression<T> y) {
        super(x, y);
    }

    protected String getSymbol() {
        return " * ";
    }

    protected T operation(T x, T y) {
        return calculator.multiply(x, y);
    }
}
