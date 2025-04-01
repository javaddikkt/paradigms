package expression.exceptions;

import expression.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpressionParser implements TripleParser, ListParser {
    private int start;
    private String line;
    private List<String> variables = new ArrayList<>();

    private final Set<Character> operators = new HashSet<>(List.of('+', '-', '*', '/', '^', '|', '&', '~'));
    private final Set<Character> openingBrackets = new HashSet<>(List.of('(', '[', '{'));
    private final Set<Character> closingBrackets = new HashSet<>(List.of(')', ']', '}'));

    @Override
    public ListExpression parse(String expression, List<String> variables) throws IllegalExpressionCheckableException {
        this.variables = variables;
        return parse(expression);
    }
    @Override
    public MyExpression parse(String expression) throws IllegalExpressionCheckableException {
//        System.err.println(expression);
        start = 0;
        line = expression;
        if (variables.isEmpty()) {
            variables.add("x");
            variables.add("y");
            variables.add("z");
        }
        int balance = checkParenthesis();
        if (balance == 0) {
            return findBitwiseOr();
        } else if (balance == 1) {
            throw new NoParenthesisException("closing");
        } else if (balance == -1){
            throw new NoParenthesisException("opening");
        } else {
            throw new WrongParenthesisException();
        }
    }

    private MyExpression findBitwiseOr() throws IllegalExpressionCheckableException {
        MyExpression exp = findBitwiseXor();
        char c = nextToken();
        while (c == '|') {
            exp = new BitwiseOr(exp, findBitwiseXor());
            c = nextToken();
        }
        start--;
        return exp;
    }

    private MyExpression findBitwiseXor() throws IllegalExpressionCheckableException {
        MyExpression exp = findBitwiseAnd();
        char c = nextToken();
        while (c == '^') {
            exp = new BitwiseXor(exp, findBitwiseAnd());
            c = nextToken();
        }
        start--;
        return exp;
    }

    private MyExpression findBitwiseAnd() throws IllegalExpressionCheckableException {
        MyExpression exp = findSumSub();
        char c = nextToken();
        while (c == '&') {
            exp = new BitwiseAnd(exp, findSumSub());
            c = nextToken();
        }
        start--;
        return exp;
    }

    private MyExpression findSumSub() throws IllegalExpressionCheckableException {
        MyExpression exp = findMulDiv();
        char c = nextToken();
        while (c == '+' || c == '-') {
            if (c == '+') {
                exp = new CheckedAdd(exp, findMulDiv());
            } else {
                exp = new CheckedSubtract(exp, findMulDiv());
            }
            c = nextToken();
        }
        start--;
        return exp;
    }

    private MyExpression findMulDiv() throws IllegalExpressionCheckableException {
        MyExpression exp = findElement();
        char c = nextToken();
        while (c == '*' || c == '/') {
            if (c == '*') {
                exp = new CheckedMultiply(exp, findElement());
            } else {
                exp = new CheckedDivide(exp, findElement());
            }
            c = nextToken();
        }
        start--;
        return exp;
    }

    private MyExpression findElement() throws IllegalExpressionCheckableException {
        char c = nextToken();
        if (openingBrackets.contains(c)) {
            MyExpression exp = findBitwiseOr();
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
                int number = parseNumber(true);
                return new Const(number);
            } else if (c == '#') {
                throw new NoArgumentException(line.length());
            } else {
                start--;
                return new CheckedNegate(findElement());
            }
        } else if (c == '~') {
            return new BitwiseNot(findElement());
        } else if (Character.isDigit(c)) {
            start--;
            int number = parseNumber(false);
            return new Const(number);
        } else {
            start--;
            if (c == 'l') {
                if (findLogPow("log2")) {
                    return new CheckedLog(findElement());
                }
            } else if (c == 'p') {
                if (findLogPow("pow2")) {
                    return new CheckedPow(findElement());
                }
            }
            if (Character.isLetter(c) || c == '$' || c == '_') {
                String variable = parseVariable();
                Variable var = new Variable(variables.indexOf(variable));
                var.setName(variable);
                return var;
            }
            throw new NoArgumentException(start);
        }
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
    private String parseVariable() throws IllegalExpressionCheckableException{
        StringBuilder sb = new StringBuilder();
        int i = 0;
        char c;
        while (start + i < line.length()) {
            c = line.charAt(start + i);
            if (!operators.contains(c) && !Character.isWhitespace(c) && !closingBrackets.contains(c)
                    && !openingBrackets.contains(c)) {
                sb.append(c);
                i++;
            } else {
                break;
            }
        }
        if (variables.contains(sb.toString())) {
            start += i;
            c = nextToken();
            if (operators.contains(c) || closingBrackets.contains(c) || c == '#') {
                start--;
                return sb.toString();
            } else {
                throw new NoOperatorException(start);
            }
        }
        throw new IllegalSymbolException(start);
    }


    private int parseNumber(boolean isNegative) throws IllegalExpressionCheckableException {
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
                return Integer.parseInt(stringNumber);
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

    private boolean findLogPow(String toFind) throws IllegalExpressionCheckableException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            if (start + i >= line.length()) {
                return false;
            }
            sb.append(line.charAt(start + i));
        }
        if (sb.toString().equals(toFind)) {
            if (start + 4 >= line.length()) {
                throw new NoArgumentException(start + 4);
            }
            if (Character.isWhitespace(line.charAt(start + 4)) || openingBrackets.contains(line.charAt(start + 4))) {
                start += 4;
                return true;
            }
        }
        return false;
    }
}