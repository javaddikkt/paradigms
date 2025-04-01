package expression.exceptions;

public class WrongParenthesisException extends IllegalExpressionCheckableException {
    public WrongParenthesisException() {}

    @Override
    public String getMessage() {
        return "Wrong type of parenthesis";
    }
}
