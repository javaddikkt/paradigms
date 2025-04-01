package expression.generic.parser;

import expression.generic.calculator.Calculator;
import expression.generic.operations.TripleExpression;

@FunctionalInterface
public interface TripleParser<T> {
    TripleExpression<T> parse(String expression, Calculator<T> calculator) throws Exception;
}
