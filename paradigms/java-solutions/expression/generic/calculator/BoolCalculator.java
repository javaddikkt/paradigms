package expression.generic.calculator;

import expression.exceptions.CalculationException;

public class BoolCalculator implements Calculator<Boolean>{
    @Override
    public Boolean add(Boolean x, Boolean y) {
        return x || y;
    }

    @Override
    public Boolean subtract(Boolean x, Boolean y) {
        return x != y;
    }

    @Override
    public Boolean multiply(Boolean x, Boolean y) {
        return x && y;
    }

    @Override
    public Boolean divide(Boolean x, Boolean y) {
        if (!y) {
            throw new CalculationException("division by zero");
        }
        return x;
    }

    @Override
    public Boolean negate(Boolean x) {
        return x;
    }

    @Override
    public Boolean parse(String str) {
        return !str.equals("0");
    }

    @Override
    public Boolean parse(int x) {
        return x != 0;
    }
}
