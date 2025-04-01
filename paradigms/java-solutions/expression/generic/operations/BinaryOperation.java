package expression.generic.operations;

public abstract class BinaryOperation<T> extends Operation<T> {
    private final MyExpression<T> a, b;

    protected BinaryOperation(MyExpression<T> a, MyExpression<T> b) {
        super(a.getCalculator());
        this.a = a;
        this.b = b;
    }

    protected abstract String getSymbol();
    protected abstract T operation(T a, T b);

    public T evaluate(T x, T y, T z) {
        return operation(a.evaluate(x, y, z), b.evaluate(x, y, z));
    }

    public String toString() {
        return "(" + a + getSymbol() + b + ")";
    }
}
