package expression.generic.operations;

import expression.generic.calculator.Calculator;

public abstract class Operation<T> implements MyExpression<T> {
    protected final Calculator<T> calculator;

    protected Operation(Calculator<T> calculator) {
        this.calculator = calculator;
    }

    public Calculator<T> getCalculator() {
        return calculator;
    }
}
