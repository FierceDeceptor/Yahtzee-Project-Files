package com.company;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.io.File;

public class Main {


    public static void main(String[] args) throws DimensionMismatchException, FileNotFoundException {

        new Reader(new Scanner(System.in), new PrintStream(System.out));

    }
}
