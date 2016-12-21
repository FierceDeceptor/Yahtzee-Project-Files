package com.company;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Scanner;
import java.io.File;

public class Main {


    public static void main(String[] args) throws DimensionMismatchException, FileNotFoundException {

        CommandInterface game = new CommandInterface(new Scanner(System.in), new PrintStream(System.out));
        game.playGame();

        // new Reader(new Scanner(System.in), new PrintStream(System.out));

    }
}
