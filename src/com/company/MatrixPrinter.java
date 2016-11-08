package com.company;

import java.io.PrintStream;

/**
 * Created by danielpredmore on 10/28/16.
 */
public class MatrixPrinter {
    public static void print(Matrix matrix, PrintStream out) {

        for (int i = 0; i < matrix.rows(); i++) {
            for (int j = 0; j < matrix.cols(); j++) {
                out.print(matrix.get(i,j) + " ");
            }

            out.println();
        }
    }

}
