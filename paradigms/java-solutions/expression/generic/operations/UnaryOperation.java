package expression.generic.operations;

public abstract class UnaryOperation<T> extends Operation<T>  {
    private final MyExpression<T> a;

    public UnaryOperation(MyExpression<T> a) {
        super(a.getCalculator());
        this.a = a;
    }
    protected abstract String getSymbol();
    protected abstract T operation(T x);

    public T evaluate(T x, T y, T z) {
        return operation(a.evaluate(x, y, z));
    }

    public String toString() {
        return getSymbol() + "(" + a + ")";
    }
}
