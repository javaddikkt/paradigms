package expression.generic.calculator;

import expression.exceptions.CalculationException;

public class ByteCalculator implements Calculator<Byte> {
    @Override
    public Byte add(Byte x, Byte y) {
        return (byte) (x + y);
    }

    @Override
    public Byte subtract(Byte x, Byte y) {
        return (byte) (x - y);
    }

    @Override
    public Byte multiply(Byte x, Byte y) {
        return (byte) (x * y);
    }

    @Override
    public Byte divide(Byte x, Byte y) {
        if (y == 0) {
            throw new CalculationException("division by zero");
        }
        return (byte) (x / y);
    }

    @Override
    public Byte negate(Byte x) {
        return (byte) -x;
    }

    @Override
    public Byte parse(String str) {
        return Byte.parseByte(str);
    }

    @Override
    public Byte parse(int x) {
        return (byte) x;
    }
}
