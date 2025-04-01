package expression.generic.operations;

import expression.generic.calculator.Calculator;

public interface MyExpression<T> extends TripleExpression<T>{
    Calculator<T> getCalculator();
}
