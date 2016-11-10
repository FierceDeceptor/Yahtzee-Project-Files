package com.company;

import java.io.PrintStream;
import java.util.*;

/**
 * Created by danielpredmore on 11/2/16.
 */
public class Reader {
    private final char[] DELIMITERS = {' ', '+', '-', '*', '/', '=', '[', ']', '(', ')', ',', ';', '\n', ':', '^'};
    private Scanner input;
    private PrintStream output;
    private Map<String, Object> vars;

    public Reader(Scanner input, PrintStream output) {
        this.input = input;
        this.output = output;
        vars = new HashMap<>();
        output.print(">> ");
        while (input.hasNext()) {
            readLine();
            output.print(">> ");
        }
    }

    private boolean readLine() {
        String line = input.nextLine();
        ArrayList<String> tokens = new ArrayList<>();
        boolean print =  false;

        String current = "";

        for (int i = 0; i < line.length(); i++) {
            if (!isDelimiter(line.charAt(i))) {
                current += line.charAt(i);
            } else {
                if (!current.equals("")) {
                    tokens.add(current);
                }
                current = "";

                if(line.charAt(i) != ' ' && line.charAt(i) != '\n') {
                    tokens.add("" + line.charAt(i));
                }
            }

        }

        if (!current.equals("")) {
            tokens.add(current);
        }

        ArrayList<Object> cTokens = convert(tokens);

        if (cTokens.size() > 0
                && vars.containsKey("ans")
                &&(cTokens.get(0).equals("-")
                || cTokens.get(0).equals("+")
                || cTokens.get(0).equals("*")
                || cTokens.get(0).equals("/")
                || cTokens.get(0).equals("^"))) {
            cTokens.add(0, vars.get("ans"));
        }

        ArrayList<Object> out = evaluate(cTokens);


        vars.put("ans", out.get(0));

        if (!out.get(out.size() - 1).equals(";")) {

            if (!(out.get(0) instanceof Matrix)) {
                output.println();
            }

            for (Object o : out) {
                if (o instanceof Matrix) {
                    output.println();
                    MatrixPrinter.print((Matrix) o, output);
                } else {
                    output.print(o + " ");
                }
            }
            output.println();

            if (!(out.get(out.size() - 1) instanceof Matrix)) {
                output.println();
            }

            print = true;
        }

        return print;
    }

    private ArrayList<Object> evaluate(ArrayList<Object> tokens) {

        tokens = parentheses(tokens);
        tokens = functions(tokens);
        tokens = MatrixOperations(tokens);
        tokens = power(tokens);
        tokens = multiply(tokens);
        tokens = add(tokens);
        tokens = matrix(tokens);
        tokens = listBuild(tokens);
        equals(tokens);

        return tokens;
    }

    private ArrayList<Object> convert(ArrayList<String> tokens) {
        ArrayList<Object> operations = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (isNumber(token)) {
                operations.add(Stat.toValue(token));
            } else if(vars.containsKey(token) && !(tokens.size() > i + 1 && tokens.get(i + 1).equals("="))) {
                Object o = vars.get(token);
                if (vars.containsKey(o.toString())) {
                    operations.add(vars.get(o.toString()));
                } else {
                    operations.add(o);
                }

            } else if (tokens.get(i).equals("-")) {
                if (i > 0 &&
                        (!(operations.get(operations.size() - 1) instanceof String)
                                || operations.get(operations.size() -1).toString().equals("]")
                                || operations.get(operations.size() -1).toString().equals(")"))) {
                    operations.add("+");
                } else if (i > 0 && operations.get(operations.size() -1).toString().equals("^")) {
                    operations.add("(");
                    tokens.add(i + 2, ")");
                }
                operations.add(Stat.toValue(-1));
                operations.add("*");

            } else {
                operations.add(token);
            }
        }

        return operations;
    }

    private boolean isNumber(String str) {
        return str.matches("[-+]?\\d*\\.?\\d+");
    }

    private ArrayList<Object> matrix(ArrayList tokens) {
        ArrayList<Object> output = new ArrayList<>();
        boolean hasChanged = false;

        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("[")) {
                hasChanged = true;
                int col = 0;
                int row = 0;

                for (int j = i + 1; !tokens.get(j).equals("]"); j++) {
                    if (tokens.get(j).equals(";")) {
                        row++;
                    } else if(!tokens.get(j).equals(",")) {
                        col++;
                    }

                }

                Matrix.Builder builder = new Matrix.Builder(row + 1, col / (row + 1));
                col = 0;
                row = 0;

                for (int j = i + 1; !tokens.get(j).equals("]"); j++) {
                    if (!tokens.get(j).equals(",") && !tokens.get(j).equals(";")) {
                        builder = builder.set((Value) tokens.get(j), row, col);
                        col++;
                    }


                    if (tokens.get(j).equals(";")) {
                        row++;
                        col = 0;
                    }

                    i = j + 1;
                }

                output.add(builder.Build());
            } else {
                output.add(tokens.get(i));
            }

        }

        if (hasChanged) {
            return evaluate(output);
        }

        return output;
    }

    private ArrayList<Object> listBuild(ArrayList<Object> tokens) {
        ArrayList<Object> output = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals(":")) {
                if (i >= 1 && tokens.get(i-1) instanceof Value
                        && i < tokens.size() - 3
                        && tokens.get(i + 1) instanceof Value
                        && tokens.get(i + 2).equals(":")
                        && tokens.get(i + 3) instanceof Value) {
                    Matrix.Builder list = new Matrix.Builder(1, (int) ((Value) tokens.get(i + 3))
                            .subtract((Value) tokens.get(i - 1))
                            .divide((Value) tokens.get(i + 1))
                            .toLong() + 1);
                    int k = 0;

                    for (Value j = (Value) tokens.get(i - 1); j.compareTo((Value) tokens.get(i + 3)) <= 0; j = j.add((Value) tokens.get(i + 1))) {
                        list.set(j,0,k);
                        k++;
                    }

                    output.remove(output.size() - 1);
                    output.add(list.Build());
                    i += 3;

                } else if (i >= 1 && tokens.get(i-1) instanceof Value
                        && i < tokens.size() - 1
                        && tokens.get(i + 1) instanceof Value) {
                    Matrix.Builder list = new Matrix.Builder(1, (int) ((Value) tokens.get(i + 1))
                            .subtract((Value) tokens.get(i - 1))
                            .toLong() + 1);
                    int k = 0;

                    for (Value j = (Value) tokens.get(i - 1); j.compareTo((Value) tokens.get(i + 1)) <= 0; j = j.add(Stat.toValue(1))) {
                        list.set(j,0,k);
                        k++;
                    }

                    output.remove(output.size() - 1);
                    output.add(list.Build());
                    i += 1;

                } else {
                    output.add(tokens.get(i));
                }
            } else {
                output.add(tokens.get(i));
            }
        }

        return output;
    }

    private ArrayList<Object> MatrixOperations(ArrayList<Object> tokens) {

        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i) instanceof Matrix && i < tokens.size() - 3) {
                if (tokens.get(i + 1) instanceof Value && tokens.get(i + 3) instanceof Value) {
                    Object token = ((Matrix) tokens.get(i)).get((int) ((Value) tokens.get(i + 1)).toLong() - 1, (int) ((Value) tokens.get(i + 3)).toLong() - 1);
                    tokens.remove(i + 3);
                    tokens.remove(i + 2);
                    tokens.remove(i + 1);
                    tokens.set(i, token);
                } else if (tokens.get(i + 1) instanceof Matrix && tokens.get(i + 3) instanceof Value) {
                    Matrix mat = (Matrix) tokens.get(i + 1);
                    Value var = (Value) tokens.get(i + 3);
                    int val = (int) var.toLong() - 1;

                    Matrix.Builder builder = new Matrix.Builder(1, mat.cols());

                    for (int j = 0; j < mat.cols(); j++) {
                        builder = builder.set(((Matrix) tokens.get(i)).get(val, (int) mat.get(0,j).toLong() - 1), 0, j);
                    }
                    Object token = builder.Build();

                    tokens.remove(i + 3);
                    tokens.remove(i + 2);
                    tokens.remove(i + 1);
                    tokens.set(i, token);
                } else if (tokens.get(i + 1) instanceof Value && tokens.get(i + 3) instanceof Matrix) {
                    Matrix mat = (Matrix) tokens.get(i + 3);
                    Value var = (Value) tokens.get(i + 1);
                    int val = (int) var.toLong() - 1;

                    Matrix.Builder builder = new Matrix.Builder(mat.cols(), 1);

                    for (int j = 0; j < mat.cols(); j++) {
                        builder = builder.set(((Matrix) tokens.get(i)).get((int) mat.get(0, j).toLong() - 1, val), j, 0);
                    }
                    Object token = builder.Build();

                    tokens.remove(i + 3);
                    tokens.remove(i + 2);
                    tokens.remove(i + 1);
                    tokens.set(i, token);
                } else if (tokens.get(i + 1) instanceof Matrix && tokens.get(i + 3) instanceof Matrix) {
                    Matrix mat1 = (Matrix) tokens.get(i + 1);
                    Matrix mat2 = (Matrix) tokens.get(i + 3);
                    int rowStart = (int) mat1.get(0,0).toLong() - 1;
                    int rowEnd = (int) mat1.get(0, mat1.cols() - 1).toLong() - 1;
                    int colStart = (int) mat2.get(0,0).toLong() - 1;
                    int colEnd = (int) mat2.get(0, mat2.cols() - 1).toLong() - 1;

                    Object token = ((Matrix) tokens.get(i)).getSubMatrix(rowStart, colStart, rowEnd, colEnd);
                    tokens.remove(i + 3);
                    tokens.remove(i + 2);
                    tokens.remove(i + 1);
                    tokens.set(i, token);
                }
            }
        }

        return tokens;
    }

    private ArrayList<Object> functions(ArrayList<Object> tokens) {
        //ArrayList<Object> output = new ArrayList<>();
        Object token;
        Matrix mat;
        Value var;

        for (int i = 0; i < tokens.size(); i++) {
            switch (tokens.get(i).toString()) {
                case "eye" :
                    var = (Value) tokens.get(i + 1);
                    token = Matrix.Builder.eye((int) var.toLong()).Build();
                    tokens.set(i, token);
                    tokens.remove(i + 1);
                    return evaluate(tokens);
                case "inv" :
                    mat = (Matrix) tokens.get(i + 1);
                    token = mat.inverse();
                    tokens.set(i, token);
                    tokens.remove(i + 1);
                    return evaluate(tokens);
                case "ref" :
                    mat = (Matrix) tokens.get(i + 1);
                    token = mat.ref();
                    tokens.set(i, token);
                    tokens.remove(i + 1);
                    return evaluate(tokens);
                case "rref" :
                    mat = (Matrix) tokens.get(i + 1);
                    token = mat.rref();
                    tokens.set(i, token);
                    tokens.remove(i + 1);
                    return evaluate(tokens);
                case "det" :
                    mat = (Matrix) tokens.get(i + 1);
                    token = mat.det();
                    tokens.set(i, token);
                    tokens.remove(i + 1);
                    return evaluate(tokens);
                case "cat" :
                    mat = (Matrix) tokens.get(i + 1);
                    if (tokens.get(i + 2).equals(";")) {
                        token = mat.verticalCat((Matrix) tokens.get(i + 3));
                    } else {
                        token = mat.horizontalCat((Matrix) tokens.get(i + 3));
                    }
                    tokens.set(i, token);
                    tokens.remove(i + 3);
                    tokens.remove(i + 2);
                    tokens.remove(i + 1);
                    return evaluate(tokens);
                case "transpose" :
                    mat = (Matrix) tokens.get(i + 1);
                    token = mat.transpose();
                    tokens.set(i, token);
                    tokens.remove(i + 1);
                    return evaluate(tokens);
                case "exit" :
                    if (tokens.size() > 1) {
                        System.exit((int) ((Value) tokens.get(1)).toLong());
                    }
                    System.exit(0);
                case "size" :
                    mat = (Matrix) tokens.get(i + 1);
                    if (((Value) tokens.get(i + 3)).toLong() == 1) {
                        token = Stat.toValue(mat.cols());
                    } else {
                        token = Stat.toValue(mat.rows());
                    }
                    tokens.set(i, token);
                    tokens.remove(i + 3);
                    tokens.remove(i + 2);
                    tokens.remove(i + 1);
                    return evaluate(tokens);
                default:
                    break;
            }
        }

        return tokens;
    }

    private ArrayList<Object> parentheses(ArrayList tokens) {
        ArrayList<Object> output = new ArrayList<>();
        ArrayList<Object> temp = new ArrayList<>();
        int parenCount = 0;

        for (int i = 0; i < tokens.size(); i++) {
            int j;
            if (tokens.get(i).equals("(")) {
                if (parenCount == 0) {
                    temp = new ArrayList<>();
                }
                parenCount++;

                for (j = i + 1; parenCount > 0; j++) {
                    if (tokens.get(j).equals("(")) {
                        parenCount++;
                    } else if (tokens.get(j).equals(")")) {
                        parenCount--;
                    }
                    if (parenCount != 0) {
                        temp.add(tokens.get(j));
                    }
                }
                temp = evaluate(temp);

                output.addAll(temp);
                i = j - 1;

            } else {
                output.add(tokens.get(i));
            }
        }

        return output;
    }

    private ArrayList<Object> multiply(ArrayList tokens) {
        ArrayList<Object> output = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("*")) {
                if (tokens.get(i+1) instanceof Value && tokens.get(i-1) instanceof Value) {
                    Value value = ((Value) output.get(output.size() - 1)).multiply((Value) tokens.get(i+1));
                    output.remove(output.size() - 1);
                    output.add(value);
                    i++;
                } else if (tokens.get(i+1) instanceof Value && tokens.get(i-1) instanceof Matrix) {
                    Matrix value = ((Matrix) output.get(output.size() - 1)).multiply((Value) tokens.get(i + 1));
                    output.remove(output.size() - 1);
                    output.add(value);
                    i++;
                } else if (tokens.get(i+1) instanceof Matrix && tokens.get(i-1) instanceof Value) {
                    Matrix value = ((Matrix) tokens.get(i + 1)).multiply((Value) output.get(output.size() - 1));
                    output.remove(output.size() - 1);
                    output.add(value);
                    i++;
                } else if (tokens.get(i+1) instanceof Matrix && tokens.get(i-1) instanceof Matrix) {
                    Matrix value = ((Matrix) output.get(output.size() - 1)).multiply((Matrix) tokens.get(i+1));
                    output.remove(output.size() - 1);
                    output.add(value);
                    i++;
                } else {
                    output.add(tokens.get(i));
                }
            } else if (tokens.get(i).equals("/")) {
                if (tokens.get(i+1) instanceof Value && tokens.get(i-1) instanceof Value) {
                    Value value = ((Value) output.get(output.size() - 1)).divide((Value) tokens.get(i+1));
                    output.remove(output.size() - 1);
                    output.add(value);
                    i++;
                }
            } else {
                output.add(tokens.get(i));
            }
        }
        return output;
    }

    private ArrayList<Object> add(ArrayList tokens) {
        ArrayList<Object> output = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("+")) {
                if (tokens.get(i+1) instanceof Value && tokens.get(i-1) instanceof Value) {
                    Value value = ((Value) output.get(output.size() - 1)).add((Value) tokens.get(i+1));
                    output.remove(output.size() - 1);
                    output.add(value);
                    i++;
                } else if (tokens.get(i+1) instanceof Value && tokens.get(i-1) instanceof Matrix) {
                    Matrix value = ((Matrix) output.get(output.size() - 1)).add((Value) tokens.get(i + 1));
                    output.remove(output.size() - 1);
                    output.add(value);
                    i++;
                } else if (tokens.get(i+1) instanceof Matrix && tokens.get(i-1) instanceof Value) {
                    Matrix value = ((Matrix) tokens.get(i + 1)).add((Value) output.get(output.size() - 1));
                    output.remove(output.size() - 1);
                    output.add(value);
                    i++;
                } else if (tokens.get(i+1) instanceof Matrix && tokens.get(i-1) instanceof Matrix) {
                    Matrix value = ((Matrix) output.get(output.size() - 1)).add((Matrix) tokens.get(i+1));
                    output.remove(output.size() - 1);
                    output.add(value);
                    i++;
                } else {
                    output.add(tokens.get(i));
                }
            } else {
                output.add(tokens.get(i));
            }
        }
        return output;
    }

    private ArrayList<Object> power(ArrayList tokens) {
        ArrayList<Object> output = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("^")) {
                if (tokens.get(i + 1) instanceof Value && tokens.get(i - 1) instanceof Value) {
                    Value value = ((Value) output.get(output.size() - 1)).power((Value) tokens.get(i + 1));
                    output.remove(output.size() - 1);
                    output.add(value);
                    i++;
                }
            } else {
                output.add(tokens.get(i));
            }
        }

        return output;
    }

    private void equals(ArrayList tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).equals("=")) {
                vars.put(tokens.get(i-1).toString(), tokens.get(i + 1));
            }
        }

    }

    private boolean isDelimiter(char c) {
        for (int i = 0; i < DELIMITERS.length; i++) {
            if (c == DELIMITERS[i]) {
                return true;
            }
        }
        return false;
    }
}

