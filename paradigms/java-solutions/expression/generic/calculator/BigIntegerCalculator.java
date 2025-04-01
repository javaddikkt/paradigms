package expression.generic.calculator;

import expression.exceptions.CalculationException;

import java.math.BigInteger;

public class BigIntegerCalculator implements Calculator<BigInteger>{
    @Override
    public BigInteger add(BigInteger x, BigInteger y) {
        return x.add(y);
    }

    @Override
    public BigInteger subtract(BigInteger x, BigInteger y) {
        return x.subtract(y);
    }

    @Override
    public BigInteger multiply(BigInteger x, BigInteger y) {
        return x.multiply(y);
    }

    @Override
    public BigInteger divide(BigInteger x, BigInteger y) {
        if (y.equals(parse(0))) {
            throw new CalculationException("division by zero");
        }
        return x.divide(y);
    }

    @Override
    public BigInteger negate(BigInteger x) {
        return x.negate();
    }

    @Override
    public BigInteger parse(String str) {
        return new BigInteger(str);
    }

    public BigInteger parse(int x) {
        return BigInteger.valueOf(x);
    }
}
