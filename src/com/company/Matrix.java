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
                builder = builder.set(this.values[i][j], i, j + cols());
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
        if ((this.rows() != 1 && this.cols() != 1) || (other.rows() != 1 && other.cols() != 1)) {
            throw new DimensionMismatchException();
        }

        Matrix current = this;
        if (current.rows() != 1) {
            current = current.transpose();
        }
        if (other.rows() != 1) {
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

        public Builder fromMatrix(Matrix other) {
            Builder builder = new Builder();
            builder.values = other.values;
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
