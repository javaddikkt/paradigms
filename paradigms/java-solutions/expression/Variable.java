package expression;

import java.util.List;
import java.util.Objects;

public class Variable implements MyExpression {
    private String x;
    private final int num;
    public Variable(String x) {
        this.x = x;
        num = -1;
    }

    public Variable(int num) {
        this.x = null;
        this.num = num;
    }

    public void setName(String x) {
        this.x = x;
    }

    public int evaluate(int x) {
        return x;
    }

    public int evaluate(int x, int y, int z) {
        if (this.x.equals("x")) {
            return x;
        }
        if (this.x.equals("y")) {
            return y;
        }
        return z;
    }

    public int evaluate(List<Integer> list) {
        return list.get(num);
    }

    public String toString() {
        return x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x);
    }

    public boolean equals(Object b) {
        if (b != null && this.getClass() == b.getClass()) {
            return Objects.equals(x, ((Variable) b).x);
        }
        return false;
    }
}
