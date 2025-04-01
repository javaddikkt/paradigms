package expression.generic.parser;

import expression.exceptions.*;
import expression.generic.operations.*;
import expression.generic.calculator.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpressionParser<T> implements TripleParser<T> {
    private int start;
    private String line;
    private Calculator<T> calculator;

    private final Set<Character> operators = new HashSet<>(List.of('+', '-', '*', '/', '^', '|', '&', '~'));
    private final Set<Character> openingBrackets = new HashSet<>(List.of('(', '[', '{'));
    private final Set<Character> closingBrackets = new HashSet<>(List.of(')', ']', '}'));

    @Override
    public MyExpression<T> parse(String expression, Calculator<T> calculator) throws IllegalExpressionCheckableException {
        start = 0;
        line = expression;
        this.calculator = calculator;
        int balance = checkParenthesis();
        if (balance == 0) {
            return findSumSub();
        } else if (balance == 1) {
            throw new NoParenthesisException("closing");
        } else if (balance == -1){
            throw new NoParenthesisException("opening");
        } else {
            throw new WrongParenthesisException();
        }
    }

    private MyExpression<T> findSumSub() throws IllegalExpressionCheckableException {
        MyExpression<T> exp = findMulDiv();
        char c = nextToken();
        while (c == '+' || c == '-') {
            if (c == '+') {
                exp = new Add<>(exp, findMulDiv());
            } else {
                exp = new Subtract<>(exp, findMulDiv());
            }
            c = nextToken();
        }
        start--;
        return exp;
    }

    private MyExpression<T> findMulDiv() throws IllegalExpressionCheckableException {
        MyExpression<T> exp = findElement();
        char c = nextToken();
        while (c == '*' || c == '/') {
            if (c == '*') {
                exp = new Multiply<>(exp, findElement());
            } else {
                exp = new Divide<>(exp, findElement());
            }
            c = nextToken();
        }
        start--;
        return exp;
    }

    private MyExpression<T> findElement() throws IllegalExpressionCheckableException {
        char c = nextToken();
        if (openingBrackets.contains(c)) {
            MyExpression<T> exp = findSumSub();
            char nextC = nextToken();
            if ((c == '(' && nextC == ')') || (c == '[' && nextC == ']') || (c == '{' && nextC == '}')) {
                c = nextToken();
                if (!(operators.contains(c) || closingBrackets.contains(c) || c == '#')) {
                    throw new NoOperatorException(start);
                }
            }
            start--;
            return exp;
        } else if (c == '-') {
            c = nextToken();
            if (Character.isDigit(c)) {
                start--;
                T number = parseNumber(true);
                return new Const<>(number, calculator);
            } else if (c == '#') {
                throw new NoArgumentException(line.length());
            } else {
                start--;
                return new Negate<>(findElement());
            }
        } else if (Character.isDigit(c)) {
            start--;
            T number = parseNumber(false);
            return new Const<>(number, calculator);
        } else if (c == 'x' || c == 'y' || c == 'z') {
            return new Variable<>(String.valueOf(c), calculator);
        }
        throw new IllegalSymbolException(start);

    }

    private char nextToken() {
        char c;
        boolean notNull = false;
        while (start < line.length()) {
            c = line.charAt(start);
            if (Character.isWhitespace(c)) {
                start++;
            } else {
                notNull = true;
                break;
            }
        }
        if (start == line.length() && !notNull) {
            return '#';
        }
        return line.charAt(start++);
    }

    private T parseNumber(boolean isNegative) throws IllegalExpressionCheckableException {
        char c = nextToken();
        String stringNumber;
        StringBuilder sb = new StringBuilder(String.valueOf(c));
        int i = 0;
        while (start + i < line.length()) {
            c = line.charAt(start + i);
            if (!(Character.isWhitespace(c) || operators.contains(c)
                || openingBrackets.contains(c) || closingBrackets.contains(c))) {
                if (Character.isDigit(c)) {
                    sb.append(c);
                    i++;
                } else {
                    throw new IllegalSymbolException(start + i);
                }
            } else {
                break;
            }
        }
        if (isNegative) {
            stringNumber = "-" + sb;
        } else {
            stringNumber = sb.toString();
        }
        start += i;
        c = nextToken();
        if (operators.contains(c) || closingBrackets.contains(c) || c == '#') {
            start--;
            try {
                return calculator.parse(stringNumber);
            } catch (NumberFormatException e) {
                throw new ConstantOverflowException(stringNumber);
            }
        } else {
            throw new NoOperatorException(start);
        }
    }

    private int checkParenthesis() {
        List<Character> lastBracket = new ArrayList<>();
        for (int i = 0; i < line.length(); i++) {
            if (openingBrackets.contains(line.charAt(i))) {
                lastBracket.add(line.charAt(i));
            } else if (closingBrackets.contains(line.charAt(i))) {
                if (lastBracket.isEmpty()) {
                    return -1;
                }
                char last =  lastBracket.get(lastBracket.size() - 1);
                if ((line.charAt(i) == ')' && last != '(')
                    || (line.charAt(i) == ']' && last != '[')
                    || (line.charAt(i) == '}' && last != '{')) {
                    return -2;
                } else {
                    lastBracket.remove(lastBracket.size() - 1);
                }
            }
        }
        if (!lastBracket.isEmpty()) {
            return 1;
        }
        return 0;
    }
}