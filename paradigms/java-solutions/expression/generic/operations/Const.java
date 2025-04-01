package expression.generic.operations;

import expression.generic.calculator.Calculator;

public class Const<T> extends Operation<T> {
    private final T cons;
    public Const(T cons, Calculator<T> calculator) {
        super(calculator);
        this.cons = cons;
    }

    @Override
    public T evaluate(T x, T y, T z) {
        return cons;
    }

    @Override
    public String toString() {
        return String.valueOf(cons);
    }
}
