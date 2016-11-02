package com.company;

/**
 * Created by danielpredmore on 10/28/16.
 */
public class Matrix {
    private Value[][] values;

    private Matrix() {}

    public Value[][] getValues() {return values;}

    public Value get(int row, int col) {return values[row][col];}

    public int rows() {return values.length;}

    public int cols() {return values[0].length;};

    public boolean isVector() {
        return this.rows() == 1 || this.cols() == 1;
    }

    public boolean isColVector() {
        return this.cols() == 1;
    }

    public boolean isRowVector() {
        return this.rows() == 1;
    }

    public boolean isRectangular() {
        return this.rows() == this.cols();
    }

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

    public Matrix getRow(int row) {
        Builder builder = new Builder(1, cols());

        for (int i = 0; i < cols(); i++) {
            builder = builder.set(get(row,i), 0, i);
        }

        return builder.Build();
    }

    public Matrix getCol(int col) {
        Builder builder = new Builder(rows(), 1);

        for (int i = 0; i < rows(); i++) {
            builder = builder.set(get(i, col), i, 0);
        }

        return builder.Build();
    }

    public Matrix getSubMatrix(int rowStart, int colStart, int rowEnd, int colEnd) {
        Builder builder = new Builder(rowEnd - rowStart, colEnd - colStart);

        for (int i = rowStart; i < rowEnd; i++) {
            for (int j = colStart; j < colEnd; j++) {
                builder = builder.set(this.get(i, j), i - rowStart, j - colStart);
            }
        }

        return builder.Build();
    }

    public Matrix transpose() {
        Builder builder = new Builder(this.cols(), this.rows());

        for (int i = 0; i < this.rows(); i++) {
            for (int j = 0; j < this.cols(); j++) {
                builder = builder.set(this.values[i][j], j, i);
            }
        }

        return builder.Build();
    }

    public Value dotProduct(Matrix other) throws DimensionMismatchException {
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

    public Matrix multiply(Matrix other) throws DimensionMismatchException {
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

    public Matrix add(Matrix other) throws  DimensionMismatchException {
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

    public Matrix subtract(Matrix other) throws DimensionMismatchException {
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

    public Matrix add(Value value) {

        Builder builder = new Builder(this.rows(), this.cols());

        for (int i = 0; i < this.rows(); i++) {
            for (int j = 0; j < this.cols(); j++) {
                builder = builder.set(this.get(i,j).add(value), i, j);
            }
        }

        return builder.Build();
    }

    public Matrix subtract(Value value) {

        Builder builder = new Builder(this.rows(), this.cols());

        for (int i = 0; i < this.rows(); i++) {
            for (int j = 0; j < this.cols(); j++) {
                builder = builder.set(this.get(i,j).subtract(value), i, j);
            }
        }

        return builder.Build();
    }

    public Matrix rowAdd(int row, Matrix other) throws DimensionMismatchException {
        if (!other.isRowVector() || other.cols() != this.cols() || row < 0 || row >= this.rows()) {
            throw new DimensionMismatchException();
        }

        Builder builder = new Builder().fromMatrix(this);

        for (int i = 0; i < this.cols(); i++) {
            builder = builder.set(this.get(row, i).add(other.get(0, i)), row, i);
        }

        return builder.Build();
    }

    public Matrix rowSubtract(int row, Matrix other)  {
        if (!other.isRowVector() || other.cols() != this.cols() || row < 0 || row >= this.rows()) {
            throw new DimensionMismatchException();
        }

        Builder builder = new Builder().fromMatrix(this);

        for (int i = 0; i < this.cols(); i++) {
            builder = builder.set(this.get(row, i).subtract(other.get(0, i)), row, i);
        }

        return builder.Build();
    }

    public Matrix rowSwap(int row, Matrix other) {
        if (!other.isRowVector() || other.cols() != this.cols() || row < 0 || row >= this.rows()) {
            throw new DimensionMismatchException();
        }

        Builder builder = new Builder().fromMatrix(this);

        for (int i = 0; i < this.cols(); i++) {
            builder = builder.set(other.get(0, i), row, i);
        }

        return builder.Build();
    }

    public Matrix rowExchange(int row_1, int row_2) {
        return this.rowSwap(row_1, this.getRow(row_2)).rowSwap(row_2, this.getRow(row_1));
    }

    public Matrix ref() {
        Matrix matrix = new Builder().fromMatrix(this).Build();

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

    public Matrix inverse() {
        if(!this.isRectangular()) {
            throw new DimensionMismatchException();
        }

        Matrix matrix = this.horizontalCat(Builder.eye(this.rows()).Build());
        matrix = matrix.rref();
        matrix = matrix.getSubMatrix(0, this.cols(), this.rows(), matrix.cols());

        return matrix;
    }

    public Matrix horizontalCat(Matrix other) throws DimensionMismatchException {
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

    public Matrix verticalCat(Matrix other) throws DimensionMismatchException {
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


    public static class Builder {
        private Value[][] values;

        private Builder() {}

        public Builder(int row, int col) {

            values = new Value[row][col];

            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    values[i][j] = Stat.toValue(0);
                }
            }
        }

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

        public static Builder eye(int size) {
            Builder builder = new Builder(size, size);

            for (int i = 0; i < size; i++) {
                builder.set(Stat.toValue(1), i, i);
            }

            return builder;
        }

        public Builder set(Value value, int row, int col) {
            values[row][col] = value;
            return this;
        }

        public Matrix Build() {
            Matrix thisMatrix = new Matrix();
            thisMatrix.values = this.values;
            return thisMatrix;
        }

    }
}
