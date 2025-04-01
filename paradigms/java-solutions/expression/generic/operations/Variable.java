package expression.generic.operations;

import expression.generic.calculator.Calculator;

public class Variable<T> extends Operation<T> {
    private final String x;
    public Variable(String x, Calculator<T> calculator) {
        super(calculator);
        this.x = x;
    }

    public T evaluate(T x, T y, T z) {
        if (this.x.equals("x")) {
            return x;
        }
        if (this.x.equals("y")) {
            return y;
        }
        return z;
    }

    public String toString() {
        return x;
    }
}
