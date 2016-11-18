package com.company;

import java.io.PrintStream;
import java.util.*;
import java.io.File;

/**
 * Created by danielpredmore on 11/16/16.
 */
public class ReaderFunction {
    private String name;
    private ArrayList<String> outputNames;
    private ArrayList<String> inputNames;
    private String code;
    private Map<String, Object> vars;
    private PrintStream out;

    protected ReaderFunction (String code, PrintStream out){
        Scanner scan = new Scanner(code);
        vars = new HashMap<>();
        ArrayList<Object> titleLine = ReaderTools.readLine(scan, vars);

        outputNames = new ArrayList<>();
        inputNames = new ArrayList<>();

        boolean change = false;
        for (int i = 1; i < titleLine.size(); i++) {
            if (!titleLine.get(i).equals("=")) {
                if(!change) {
                    outputNames.add(titleLine.get(i).toString());
                } else {
                    inputNames.add(titleLine.get(i).toString());
                }
            } else {
                change = true;
                name = titleLine.get(i+1).toString();
                i++;
            }
        }

        this.code = "";

        while (scan.hasNextLine()) {
            this.code = this.code + scan.nextLine() + "\n";
        }

        this.out = out;
    }

    protected String getName() {return name;}
    protected int inputSize() {return inputNames.size();}
    protected int outputSize() {return outputNames.size();}

    protected ArrayList<Object> run(ArrayList<Object> inputs) {
        for (int i = 0; i < inputs.size(); i++) {
            vars.put(inputNames.get(i), inputs.get(i));
        }

        new Reader(new Scanner(code), out, vars, 1);
        ArrayList<Object> output = new ArrayList<>();

        for (String outName: outputNames) {
            output.add(vars.get(outName));
        }

        return output;
    }


}
