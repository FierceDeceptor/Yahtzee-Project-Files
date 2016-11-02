package com.company;

public class Main {


    public static void main(String[] args) throws DimensionMismatchException {
//	    Value fraction1 = Stat.toValue(341,759);
//        Value fraction2 = Stat.toValue(1,3)
//                .add(Stat.toValue(-1));
//        Value fraction = fraction1
//                .multiply(fraction2);
//        System.out.println(fraction + " = " + fraction.toDouble());
//        System.out.println(Stat.toValue(6,6).multiply(Stat.toValue(1,6)).multiply(Stat.toValue(1,6).multiply(Stat.toValue(1,6))));
//        System.out.println(Stat.toValue(6,1296));
//
//        Value[] ratios = {Stat.toValue(6,6), Stat.toValue(1,6), Stat.toValue(5,6), Stat.toValue(4,6)};
//        System.out.println(Stat.product(ratios));
//
//        System.out.println(Stat.approximateToRatio(fraction.toDouble(), 1.0E-6));
//        System.out.println(Stat.convertToRatio(fraction.toDouble()));
//
//        System.out.println(Stat.factorialQuotient(7,7));
//        System.out.println(Stat.toValue(Stat.factorial(15),(Stat.factorial(7))));
//        System.out.println("\n\n");
//        System.out.println(Stat.toValue(7).multiply(Stat.toValue(5,7)));

        Matrix matrix =  new Matrix
                .Builder(3,3)
                .set(Stat.toValue(11),0,0)
                .set(Stat.toValue(2,1),1,0)
                .set(Stat.toValue(3,1),2,0)
                .set(Stat.toValue(1,2),0,1)
                .set(Stat.toValue(2,2),1,1)
                .set(Stat.toValue(3,2),2,1)
                .set(Stat.toValue(13),0,2)
                .set(Stat.toValue(2,3),1,2)
                .set(Stat.toValue(3,3),2,2)
                .Build();

        MatrixPrinter.print(matrix);
        System.out.println();
        //MatrixPrinter.print(matrix.getCol(1));
        //MatrixPrinter.print(matrix.getRow(2));
        MatrixPrinter.print(matrix.horizontalCat(matrix.getCol(1)));
        System.out.println();
        System.out.println(matrix.getCol(1).dotProduct(matrix.getRow(2)));

        System.out.println();
        MatrixPrinter.print(matrix.multiply(matrix));

        System.out.println("ref");
        MatrixPrinter.print(matrix.ref());

        //MatrixPrinter.print(matrix);

        System.out.println("Solving!!!");

        Matrix system = new Matrix.Builder(3, 4)
                .set(Stat.toValue(5),0,0)
                .set(Stat.toValue(2),0,1)
                .set(Stat.toValue(4),0,2)
                .set(Stat.toValue(7),0,3)
                .set(Stat.toValue(2),1,0)
                .set(Stat.toValue(4),1,1)
                .set(Stat.toValue(5),1,2)
                .set(Stat.toValue(3),1,3)
                .set(Stat.toValue(6),2,0)
                .set(Stat.toValue(4),2,1)
                .set(Stat.toValue(1),2,2)
                .set(Stat.toValue(2),2,3)
                .Build();

        System.out.println("The System");
        MatrixPrinter.print(system);
        System.out.println("rref of system");
        Matrix rref = system.rref();
        MatrixPrinter.print(rref);
        System.out.println("The solution");
        Matrix solution = rref.getCol(3).transpose();
        MatrixPrinter.print(solution);

        System.out.println();

        Matrix mat = system.innerProduct(system);
        MatrixPrinter.print(mat);

        System.out.println("Inverse");
        MatrixPrinter.print(mat.inverse());

        MatrixPrinter.print(mat.multiply(mat.inverse()));

        MatrixPrinter.print(mat.ref());

        System.out.println("PLU");
        MatrixPrinter.print(mat.rowExchange(0,2));
        Matrix[] arr = mat.rowExchange(0,2).plu();
        System.out.println("P");
        MatrixPrinter.print(arr[0]);
        System.out.println("L");
        MatrixPrinter.print(arr[1]);
        System.out.println("U");
        MatrixPrinter.print(arr[2]);
        System.out.println("Check PLU");
        MatrixPrinter.print(arr[0].transpose().multiply(arr[1].multiply(arr[2])));

        System.out.println("Det = " + mat.det());





    }
}
