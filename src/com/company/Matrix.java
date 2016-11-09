package com.company;

/**
 * Created by danielpredmore on 10/28/16.
 *
 * The Matrix Object stores Values as a Matrix
 *
 * To construct a new matrix use the Builder class
 * Example:
 * Matrix matrix = new Builder(row, col)
 *      .set(value, row, col)
 *      .build;
 *
 */
public class Matrix {
    private Value[][] values;

    /**
     * Private constructor used by Builder class
     */
    private Matrix() {}

    /**
     * get the array of values
     *
     * @return values
     */
    public Value[][] getValues() {return values;}

    /**
     * gets a value at a given location
     *
     * @param row the row of the element
     * @param col the column of the element
     * @return the element
     */
    public Value get(int row, int col) {return values[row][col];}

    /**
     * gets the number of rows in the matrix
     *
     * @return the number of rows
     */
    public int rows() {return values.length;}

    /**
     * gets the number of columns in the matrix
     *
     * @return the number of columns
     */
    public int cols() {return values[0].length;};

    /**
     * finds if the matrix is either a row or column vector
     *
     * @return true if vector
     */
    public boolean isVector() {
        return this.rows() == 1 || this.cols() == 1;
    }

    /**
     * checks if matrix is a column vector
     *
     * @return true if column vector
     */
    public boolean isColVector() {
        return this.cols() == 1;
    }

    /**
     * checks if matrix is a row vector
     *
     * @return true if row vector
     */
    public boolean isRowVector() {
        return this.rows() == 1;
    }

    /**
     * checks if a matrix is rectangular
     *
     * @return true if rectangular
     */
    public boolean isRectangular() {
        return this.rows() == this.cols();
    }

    /**
     * checks to see if matrix is all zeros
     *
     * @return true if all zero
     */
    public boolean isZeroMatrix() {
        for (int i = 0; i < this.rows(); i++) {
            for (int j = 0; j < this.cols(); j++) {
                if (!this.get(i,j).equals(Stat.toValue(0))) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * gets a given row from the matrix
     *
     * @param row index of the row
     * @return the row
     */
    public Matrix getRow(int row) {
        Builder builder = new Builder(1, cols());

        for (int i = 0; i < cols(); i++) {
            builder = builder.set(get(row,i), 0, i);
        }

        return builder.Build();
    }

    /**
     * gets a given column of the matrix
     *
     * @param col the index of the column
     * @return the column
     */
    public Matrix getCol(int col) {
        Builder builder = new Builder(rows(), 1);

        for (int i = 0; i < rows(); i++) {
            builder = builder.set(get(i, col), i, 0);
        }

        return builder.Build();
    }

    /**
     * gets a sub matrix from the matrix
     *
     * @param rowStart the starting row
     * @param colStart the starting column
     * @param rowEnd the ending row
     * @param colEnd the ending column
     * @return a matrix from start to end points
     */
    public Matrix getSubMatrix(int rowStart, int colStart, int rowEnd, int colEnd) {
        Builder builder = new Builder(rowEnd - rowStart + 1, colEnd - colStart + 1);

        for (int i = rowStart; i <= rowEnd; i++) {
            for (int j = colStart; j <= colEnd; j++) {
                builder = builder.set(this.get(i, j), i - rowStart, j - colStart);
            }
        }

        return builder.Build();
    }

    /**
     * transposes a matrix
     *
     * @return the transpose of the matrix
     */
    public Matrix transpose() {
        Builder builder = new Builder(this.cols(), this.rows());

        for (int i = 0; i < this.rows(); i++) {
            for (int j = 0; j < this.cols(); j++) {
                builder = builder.set(this.values[i][j], j, i);
            }
        }

        return builder.Build();
    }

    /**
     * finds the dot product of two vectors
     *
     * @param other the vector to be dotted
     * @return the value of the product
     */
    public Value dotProduct(Matrix other) {
        if (!this.isVector() || !other.isVector()) {
            throw new DimensionMismatchException();
        }

        Matrix current = this;
        if (this.isColVector()) {
            current = current.transpose();
        }
        if (other.isColVector()) {
            other = other.transpose();
        }

        if (current.cols() != other.cols()) {
            throw new DimensionMismatchException();
        }

        Value value = Stat.toValue(0);

        for (int i = 0; i < current.cols(); i++) {
            value = value.add(current.get(0,i).multiply(other.get(0,i)));
        }

        return value;
    }

    /**
     * multiplies the matrix with another matrix
     *
     * @param other the matrix to b multiplied
     * @return the matrix of the product of the matrices
     */
    public Matrix multiply(Matrix other) {
        if (this.cols() != other.rows()) {
            throw new DimensionMismatchException();
        }

        Builder builder = new Builder(this.rows(), other.cols());

        for (int i = 0; i < this.rows(); i++) {
            for (int j = 0; j < other.cols(); j++) {
                builder.set(this.getRow(i).dotProduct(other.getCol(j)), i, j);
            }
        }

        return builder.Build();
    }

    public Matrix innerProduct(Matrix other) throws DimensionMismatchException {
        return this.multiply(other.transpose());
    }

    public Matrix multiply(Value value) {
        Builder builder = new Builder(this.rows(), this.cols());

        for (int i = 0; i < this.rows(); i++) {
            for (int j = 0; j < this.cols(); j++) {
                builder = builder.set(value.multiply(this.get(i, j)), i, j);
            }
        }

        return builder.Build();
    }

    /**
     * adds the matrix to another matrix
     *
     * @param other the other matrix to be added
     * @return the sum of the matrices
     */
    public Matrix add(Matrix other) {
        if (this.cols() != other.cols() && this.rows() != other.rows()) {
            throw new DimensionMismatchException();
        }

        Builder builder = new Builder(this.rows(), this.cols());

        for (int i = 0; i < this.rows(); i++) {
            for (int j = 0; j < this.cols(); j++) {
                builder = builder.set(this.get(i,j).add(other.get(i,j)), i, j);
            }
        }

        return builder.Build();
    }

    /**
     * subtracts two matrices
     *
     * @param other the matrix to be subtracted
     * @return the difference of the matrices
     */
    public Matrix subtract(Matrix other) {
        if (this.cols() != other.cols() && this.rows() != other.rows()) {
            throw new DimensionMismatchException();
        }

        Builder builder = new Builder(this.rows(), this.cols());

        for (int i = 0; i < this.rows(); i++) {
            for (int j = 0; j < this.cols(); j++) {
                builder = builder.set(this.get(i,j).subtract(other.get(i,j)), i, j);
            }
        }

        return builder.Build();
    }

    /**
     * adds a value to every term of a matrix
     *
     * @param value the value to be added
     * @return the sum of the matrix and the value
     */
    public Matrix add(Value value) {

        Builder builder = new Builder(this.rows(), this.cols());

        for (int i = 0; i < this.rows(); i++) {
            for (int j = 0; j < this.cols(); j++) {
                builder = builder.set(this.get(i,j).add(value), i, j);
            }
        }

        return builder.Build();
    }

    /**
     * subtracts a value from ever term of the matrix
     *
     * @param value the value to be subtracted
     * @return the difference of the matrix and the value
     */
    public Matrix subtract(Value value) {

        Builder builder = new Builder(this.rows(), this.cols());

        for (int i = 0; i < this.rows(); i++) {
            for (int j = 0; j < this.cols(); j++) {
                builder = builder.set(this.get(i,j).subtract(value), i, j);
            }
        }

        return builder.Build();
    }

    /**
     * adds a row vector to a row in the matrix
     *
     * @param row the index of the row
     * @param other the other row to be added
     * @return this matrix with the row operation
     */
    public Matrix rowAdd(int row, Matrix other) {
        if (!other.isRowVector() || other.cols() != this.cols() || row < 0 || row >= this.rows()) {
            throw new DimensionMismatchException();
        }

        Builder builder = Builder.fromMatrix(this);

        for (int i = 0; i < this.cols(); i++) {
            builder = builder.set(this.get(row, i).add(other.get(0, i)), row, i);
        }

        return builder.Build();
    }

    /**
     * subtracts a row vector from a row in the matrix
     *
     * @param row the index of the row to be subtracted from
     * @param other the row vector to be subtracted
     * @return this matrix with the row operation
     */
    public Matrix rowSubtract(int row, Matrix other)  {
        if (!other.isRowVector() || other.cols() != this.cols() || row < 0 || row >= this.rows()) {
            throw new DimensionMismatchException();
        }

        Builder builder = Builder.fromMatrix(this);

        for (int i = 0; i < this.cols(); i++) {
            builder = builder.set(this.get(row, i).subtract(other.get(0, i)), row, i);
        }

        return builder.Build();
    }

    /**
     * replaces a row with a given row vector
     *
     * @param row the index of the row to be changed
     * @param other the row to be placed in the matrix
     * @return this matrix with the row operation
     */
    public Matrix rowSwap(int row, Matrix other) {
        if (!other.isRowVector() || other.cols() != this.cols() || row < 0 || row >= this.rows()) {
            throw new DimensionMismatchException();
        }

        Builder builder = Builder.fromMatrix(this);

        for (int i = 0; i < this.cols(); i++) {
            builder = builder.set(other.get(0, i), row, i);
        }

        return builder.Build();
    }

    /**
     * switches two rows of the matrix
     *
     * @param row_1 the index of the first row
     * @param row_2 the index of the second row
     * @return the matrix with the row operation
     */
    public Matrix rowExchange(int row_1, int row_2) {
        return this.rowSwap(row_1, this.getRow(row_2)).rowSwap(row_2, this.getRow(row_1));
    }

    /**
     * finds the row echelon form of the matrix
     *
     * @return the row echelon form of the matrix
     */
    public Matrix ref() {
        Matrix matrix = Builder.fromMatrix(this).Build();

        int itr = Math.min(matrix.rows(), matrix.cols());

        for (int i = 0; i < itr; i++) {
            for (int j = 0; j < matrix.rows() - i; j++) {
                if (matrix.get(i,i).compareTo(matrix.get(j + i,i)) < 0) {
                    matrix = matrix.rowExchange(i, j + i);
                }
            }


            if (matrix.get(i,i).compareTo(Stat.toValue(0)) != 0) {
                Value value = Stat.toValue(1).divide(matrix.get(i, i));
                matrix = matrix.rowSwap(i, matrix.getRow(i).multiply(value));

                for (int j = i + 1; j < matrix.rows(); j++) {
                    matrix = matrix.rowSubtract(j, matrix.getRow(i).multiply(matrix.get(j, i)));

                }

            }
        }

        return matrix;
    }

    /**
     * find the reduced row echelon form of the matrix
     *
     * @return the reduced row echelon form of the matrix
     */
    public Matrix rref() {
        Matrix matrix = this.ref();

        int itr = Math.min(this.rows(), this.cols());

        for (int i = itr - 1; i >= 0; i--) {
            if (matrix.get(i,i).compareTo(Stat.toValue(0)) != 0) {

                for (int j = i - 1; j >= 0; j--) {
                    matrix = matrix.rowSubtract(j, matrix.getRow(i).multiply(matrix.get(j, i)));
                }

            }
        }

        return matrix;
    }

    /**
     * finds the inverse of the matrix
     *
     * @return the matrix inverse
     */
    public Matrix inverse() {
        if(!this.isRectangular()) {
            throw new DimensionMismatchException();
        }

        Matrix matrix = this.horizontalCat(Builder.eye(this.rows()).Build());
        matrix = matrix.rref();
        matrix = matrix.getSubMatrix(0, this.cols(), this.rows(), matrix.cols());

        return matrix;
    }

    /**
     * Finds the LU decomposition of the matrix
     *
     * @return {L matrix, U matrix}
     */
    public Matrix[] lu() {
        if (!this.isRectangular()) {
            throw new DimensionMismatchException();
        }

        int itr = this.rows();

        Matrix l = Builder.eye(itr).Build();
        Matrix u = Builder.fromMatrix(this).Build();

        for (int i = 0; i < itr; i++) {

            if (u.get(i,i).compareTo(Stat.toValue(0)) != 0) {

                for (int j = i + 1; j < itr; j++) {
                    l = Builder.fromMatrix(l)
                            .set(u.get(j, i).divide(u.get(i,i)), j, i)
                            .Build();
                    u = u.rowSubtract(j, u.getRow(i).multiply(u.get(j, i).divide(u.get(i,i))));

                }

            }
        }

        Matrix[] output = {l, u};

        return output;
    }

    /**
     * Finds the PLU decomposition of the matrix
     *
     * @return {P matrix, L matrix, U matrix}
     */
    public Matrix[] plu() {
        if (!this.isRectangular()) {
            throw new DimensionMismatchException();
        }

        int itr = this.rows();

        Matrix p = Builder.eye(itr).Build();
        Matrix l = Builder.eye(itr).Build();
        Matrix u = Builder.fromMatrix(this).Build();

        for (int i = 0; i < itr; i++) {
            for (int j = 0; j < itr - i; j++) {
                if (u.get(i,i).compareTo(u.get(j + i,i)) < 0) {

                    u = u.rowExchange(i, j + i);
                    p = p.rowExchange(i, j + i);
                }
            }


            if (u.get(i,i).compareTo(Stat.toValue(0)) != 0) {

                for (int j = i + 1; j < itr; j++) {
                    l = Builder.fromMatrix(l)
                            .set(u.get(j, i).divide(u.get(i,i)), j, i)
                            .Build();
                    u = u.rowSubtract(j, u.getRow(i).multiply(u.get(j, i).divide(u.get(i,i))));

                }

            }
        }

        Matrix[] output = {p, l, u};

        return output;
    }

    /**
     * Checks to see if the values of two matrices are the same
     *
     * @param other the other matrix
     * @return true if the values are the same
     */
    public boolean equals(Matrix other) {
        if (this.rows() != other.rows() || this.cols() != other.cols()) {
            return false;
        }

        for (int i = 0; i < this.rows(); i++) {
            for (int j = 0; j < this.cols(); j++) {
                if (this.get(i,j).compareTo(other.get(i,j)) != 0 ) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * finds the determinate of the matrix
     *
     * @return the determinate
     */
    public Value det() {
        Matrix[] arr = this.plu();

        Value det = Stat.toValue(1);


        for (int i = 0; i < this.rows(); i++) {
            det = det.multiply(arr[2].get(i,i));
        }

        return det;
    }

    /**
     * concatenates a matrix horizontally
     *
     * @param other the matrix to be added
     * @return the concatenated matrix
     */
    public Matrix horizontalCat(Matrix other) {
        if (this.rows() != other.rows()) {
            throw new DimensionMismatchException();
        }

        Builder builder = new Builder(this.rows() , this.cols() + other.cols());

        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < this.cols(); j++) {
                builder = builder.set(this.values[i][j], i, j);
            }
        }

        for (int i = 0; i < rows(); i++) {
            for (int j = 0; j < other.cols(); j++) {
                builder = builder.set(other.values[i][j], i, j + cols());
            }
        }

        return builder.Build();
    }

    /**
     * concatenates a matrix vertically
     *
     * @param other the matrix to be added
     * @return the concatenated matrix
     */
    public Matrix verticalCat(Matrix other) {
        if (this.cols() != other.cols()) {
            throw new DimensionMismatchException();
        }

        Builder builder = new Builder(this.rows() + other.rows() , this.cols());

        for (int i = 0; i < this.rows(); i++) {
            for (int j = 0; j < this.cols(); j++) {
                builder = builder.set(this.values[i][j], i, j);
            }
        }

        for (int i = 0; i < other.rows(); i++) {
            for (int j = 0; j < other.cols(); j++) {
                builder = builder.set(other.values[i][j], i + this.rows(), j);
            }
        }

        return builder.Build();
    }


    /**
     * Builder for the Matrix class
     */
    public static class Builder {
        private Value[][] values;

        /**
         * private constructor
         */
        private Builder() {}

        /**
         * constructor for a matrix with a given dimension
         *
         * @param row the number of rows
         * @param col the number of columns
         */
        public Builder(int row, int col) {

            values = new Value[row][col];

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    values[i][j] = Stat.toValue(0);
                }
            }
        }

        /**
         * constructs a Builder from an array of values
         *
         * @param values the values of the matrix
         */
        public Builder(Value[][] values) {
            for (int i = 0; i < values.length; i++) {
                for (int j = 0; j < values[i].length; j++) {
                    if (values[i][j] == null) {
                        values[i][j] = Stat.toValue(0);
                    }
                }
            }

            this.values = values;
        }

        /**
         * constructs a Builder form another Matrix
         *
         * @param other the matrix to be copied
         * @return the builder
         */
        public static Builder fromMatrix(Matrix other) {
            Builder builder = new Builder();
            builder.values = new Value[other.rows()][other.cols()];
            for (int i = 0; i < other.rows(); i++) {
                for (int j = 0; j < other.cols(); j++) {
                    builder.values[i][j] = other.values[i][j];
                }
            }

            return builder;
        }

        /**
         * creates a builder of an identity matrix
         *
         * @param size the size of the matrix
         * @return the builder
         */
        public static Builder eye(int size) {
            Builder builder = new Builder(size, size);

            for (int i = 0; i < size; i++) {
                builder.set(Stat.toValue(1), i, i);
            }

            return builder;
        }

        /**
         * sets an index of the builder to a value
         *
         * @param value the value to be set
         * @param row the index of the row
         * @param col the index of the column
         * @return the builder
         */
        public Builder set(Value value, int row, int col) {
            values[row][col] = value;
            return this;
        }

        /**
         * constructs the builder into a Matrix
         *
         * @return the new Matrix
         */
        public Matrix Build() {
            Matrix thisMatrix = new Matrix();
            thisMatrix.values = this.values;
            return thisMatrix;
        }

    }
}
