package expression.generic.calculator;

public interface Calculator<T> {
    T add(T x, T y);
    T subtract(T x, T y);
    T multiply(T x, T y);
    T divide(T x, T y);
    T negate(T x);
    T parse(String str);
    T parse(int x);
}
