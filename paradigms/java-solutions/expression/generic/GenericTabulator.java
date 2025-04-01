package expression.generic;

import expression.exceptions.IllegalExpressionCheckableException;
import expression.exceptions.IllegalExpressionUncheckableException;
import expression.generic.parser.ExpressionParser;
import expression.generic.operations.*;
import expression.generic.calculator.*;

import java.util.Map;

public class GenericTabulator implements Tabulator{
    private final Map<String, Calculator<?>> calculatorMap = Map.of(
            "i", new IntegerCalculator(true),
            "u", new IntegerCalculator(false),
            "b", new ByteCalculator(),
            "bool", new BoolCalculator(),
            "d", new DoubleCalculator(),
            "bi", new BigIntegerCalculator()
    );

    @Override
    public Object[][][] tabulate(String mode, String expression,
                                 int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {

        return makeTable(calculatorMap.get(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] makeTable(Calculator<T> calculator, String expression,
                                       int x1, int x2, int y1, int y2, int z1, int z2) throws IllegalExpressionCheckableException {
        MyExpression<T> result = new ExpressionParser<T>().parse(expression, calculator);
        Object[][][] table = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++) {
                    try {
                        table[i - x1][j - y1][k - z1] = result.evaluate(
                                calculator.parse(i), calculator.parse(j), calculator.parse(k));
                    } catch (IllegalExpressionUncheckableException e) {
                        table[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return table;
    }

}
