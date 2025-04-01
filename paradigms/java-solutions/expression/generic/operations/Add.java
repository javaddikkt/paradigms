package expression.generic.operations;

public class Add<T> extends BinaryOperation<T> {
    public Add(MyExpression<T> x, MyExpression<T> y) {
        super(x, y);
    }

    protected String getSymbol() {
        return " + ";
    }

    protected T operation(T x, T y) {
        return calculator.add(x, y);
    }
}
