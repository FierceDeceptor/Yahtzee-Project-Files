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
        MatrixPrinter.print(matrix.getCol(1));
        MatrixPrinter.print(matrix.getRow(1));
        MatrixPrinter.print(matrix.horizontalCat(matrix.getCol(0)));



    }
}
