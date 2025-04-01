package expression.generic;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String mode = args[0].substring(1);
        GenericTabulator tabulator = new GenericTabulator();
        try {
            Object[][][] table = tabulator.tabulate(mode, args[1], -2, 2, -2, 2, -2, 2);
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    for (int k = 0; k < 5; k++) {
                        System.out.print(table[i][j][k] + " ");
                    }
                    System.out.println();
                }
                System.out.println();
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println("error");
        }
    }
}
