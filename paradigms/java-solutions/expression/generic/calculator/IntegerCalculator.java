package expression.generic.calculator;

import expression.exceptions.*;

public class IntegerCalculator implements Calculator<Integer> {
    private final boolean check;

    public IntegerCalculator(boolean check) {
        this.check = check;
    }

    @Override
    public Integer add(Integer x, Integer y) {
        if (check && ((y > 0 && Integer.MAX_VALUE - y < x) || (y < 0 && Integer.MIN_VALUE - y > x))) {
            throw new NumberOutOfRangeException("sum");
        } else {
            return x + y;
        }
    }

    @Override
    public Integer subtract(Integer x, Integer y) {
        if (check && ((y > 0 && x < Integer.MIN_VALUE + y) || (y < 0 && x > Integer.MAX_VALUE + y))) {
            throw new NumberOutOfRangeException("subtract");
        }
        return x - y;
    }

    @Override
    public Integer multiply(Integer x, Integer y) {
        if (check && ((y == Integer.MIN_VALUE && x == -1) || (x == Integer.MIN_VALUE && y == -1) ||
                (x > 0 && (y > Integer.MAX_VALUE / x || y < Integer.MIN_VALUE / x)) ||
                (x < -1 && (y > Integer.MIN_VALUE / x || y < Integer.MAX_VALUE / x)))) {
            throw new NumberOutOfRangeException("multiply");
        }
        return x * y;
    }

    @Override
    public Integer divide(Integer x, Integer y) {
        if (y == 0) {
            throw new CalculationException("division by zero");
        }
        if (check && x == Integer.MIN_VALUE && y == -1) {
            throw new NumberOutOfRangeException("division");
        }
        return x / y;
    }

    @Override
    public Integer negate(Integer x) {
        if (check && x == Integer.MIN_VALUE) {
            throw new NumberOutOfRangeException("negate");
        }
        return -1 * x;
    }

    @Override
    public Integer parse(String str) {
        return Integer.parseInt(str);
    }

    @Override
    public Integer parse(int x) {
        return x;
    }
}
