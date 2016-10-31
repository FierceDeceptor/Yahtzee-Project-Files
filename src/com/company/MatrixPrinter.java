package com.company;

/**
 * Created by danielpredmore on 10/28/16.
 */
public class MatrixPrinter {
    public static void print(Matrix matrix) {

        for (int i = 0; i < matrix.rows(); i++) {
            for (int j = 0; j < matrix.cols(); j++) {
                System.out.print(matrix.get(i,j) + " ");
            }

            System.out.println();
        }
    }
}
