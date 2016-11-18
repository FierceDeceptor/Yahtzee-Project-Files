package com.company;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Map;

/**
 * Created by danielpredmore on 11/16/16.
 */
public class ReaderTools {
    private static final char[] DELIMITERS = {' ', '+', '-', '*', '/', '=', '[', ']', '(', ')', ',', ';', '\n', ':', '^', '<', '>', '~', '{', '}'};


    protected static ArrayList<Object> readLine(Scanner input, Map<String, Object> vars) {
        String line = input.nextLine();
        ArrayList<String> tokens = new ArrayList<>();

        String current = "";

        for (int i = 0; i < line.length(); i++) {
            if (!isDelimiter(line.charAt(i))) {
                current += line.charAt(i);
            } else {
                if (!current.equals("")) {
                    tokens.add(current);
                }
                current = "";

                if (line.charAt(i) != ' ' && line.charAt(i) != '\n') {
                    tokens.add("" + line.charAt(i));
                }
            }

        }

        if (!current.equals("")) {
            tokens.add(current);
        }

        return convert(tokens, vars);

    }

    protected static ArrayList<Object> convert(ArrayList<String> tokens, Map<String, Object> vars) {
        ArrayList<Object> operations = new ArrayList<>();

        for (int i = 0; i < tokens.size(); i++) {
            String token = tokens.get(i);
            if (isNumber(token)) {
                operations.add(Stat.toValue(token));
            } else if(vars.containsKey(token) && !(tokens.size() > i + 1 && tokens.get(i + 1).equals("=") && !tokens.get(i+2).equals("="))) {
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

    private static boolean isNumber(String str) {
        return str.matches("[-+]?\\d*\\.?\\d+");
    }

    protected static boolean isDelimiter(char c) {
        for (int i = 0; i < DELIMITERS.length; i++) {
            if (c == DELIMITERS[i]) {
                return true;
            }
        }
        return false;
    }
}
