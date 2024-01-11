package com.example.demo2;

import ch.obermuhlner.math.big.BigDecimalMath;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Queue;
import java.util.Stack;

import static ch.obermuhlner.math.big.BigDecimalMath.toBigDecimal;

public class Calculator {

    private calculatorUtil util;
    private Stack<String> history;


    public Calculator() {
        util = new calculatorUtil();
        history = new Stack<String>();
    }

    public String calculate(String function) {
        Stack<String> Opes = new Stack<String>();
        Stack<String> Nums = new Stack<String>();
        try {
            isLegit(function);
            Queue<String> func = util.numberModify(util.stringToQueue(function));
            util.printQueue(func);
            int size = func.size();
            for (int i = 0; i < size; i++) {
                System.out.println(func.peek());
                if (Opes.empty() && util.Operators.contains(func.peek())) {
                    System.out.println("Case 1");
                    Opes.add(func.peek());
                    func.poll();
                    continue;
                }
                if (util.Operators.contains(func.peek())) {
                    if (util.priority.get(func.peek()) > util.priority.get(Opes.peek())) {
                        System.out.println("Case 6");
                        Opes.add(func.peek());
                        func.poll();
                        continue;
                    }
                }

                if (func.peek().equals("(")) {
                    System.out.println("Case 2");
                    Opes.add(func.peek());
                    func.poll();
                    continue;
                }

                if (func.peek().equals(")")) {
                    System.out.println("Case 3");
                    while (!Opes.peek().equals("(")) {
                        String y = Nums.peek();
                        Nums.pop();
                        String x = Nums.peek();
                        Nums.pop();
                        String operand = Opes.peek();
                        Opes.pop();
                        String result = controller(operand,x,y);
                        Nums.add(result);
                    }
                    Opes.pop();
                    func.poll();
                    continue;
                }

                if (!util.Operators.contains(func.peek())) {
                    System.out.println("Case 4");
                    System.out.println(func.peek());
                    Nums.add(func.peek());
                    func.poll();
                    continue;
                }


                if (util.Operators.contains(func.peek())
                        && util.priority.get(func.peek()) <= util.priority.get(Opes.peek())
                        && util.priority.get(Opes.peek()) != 0) {
                    System.out.println("Case 5");
                    String operand = Opes.peek();
                    Opes.pop();
                    Opes.add(func.peek());
                    String y = Nums.peek();
                    Nums.pop();
                    String x = Nums.peek();
                    Nums.pop();
                    String result = controller(operand, x, y);
                    Nums.add(result);
                    func.poll();
                    continue;
                }
            }
            while (Nums.size() != 1) {
                System.out.println("Nums size is" + Nums.size());
                String y = Nums.peek();
                Nums.pop();
                System.out.println("y is " + y);
                String x = Nums.peek();
                Nums.pop();
                String operand = Opes.peek();
                Opes.pop();
                String result = controller(operand,x,y);
                Nums.add(result);
            }
        } catch (SyntaxException | MathException ex) {
            System.out.println("Error!!!  :" + ex);
            return "";
        } finally {
            System.out.println("Try-Catch finished");
        }
        if (!Nums.empty()) {
            history.add(Nums.peek());
        }
        return Nums.peek();
    }

    public void isLegit(String function) throws SyntaxException{
        Stack<String> check = new Stack<String>();
        for (int i = 0; i < function.length();i++) {
            String functionCharAt = "";
            functionCharAt += function.charAt(i);
            if (functionCharAt.equals("(")) {
                check.add("(");
            }
            if (functionCharAt.equals(")")) {
                if (check.empty()) {
                    throw new SyntaxException(i, 0);
                }
                else {
                    check.pop();
                }
            }
            if (!util.Numbers.contains(functionCharAt) && i == 0 && function.charAt(i) != '+' && function.charAt(i) != '-' && function.charAt(i) != '(' ) {
                throw new SyntaxException(i, 1);
            }
            if (i == function.length() - 1) {
                if (functionCharAt.equals(")")) {
                    continue;
                }
                if (!util.Numbers.contains(functionCharAt)) {
                    throw new SyntaxException(i, 2);
                }
            }
            if (!util.Numbers.contains(functionCharAt)
                    && i != 0
                        && function.charAt(i) != '+'
                            && function.charAt(i) != '-'
                                && function.charAt(i) != '('
                                    && function.charAt(i) != ')' ) {
                String test = "";
                test += function.charAt(i-1);
                if (function.charAt(i - 1) == ')') {
                    continue;
                }
                if (!util.Numbers.contains(test)) {
                    throw new SyntaxException(i, 3);
                }
            }
            if (!util.Numbers.contains(functionCharAt) && !util.Operators.contains(functionCharAt)) {
                throw new SyntaxException(i, 4);
            }

        }
        if (!check.empty()) throw new SyntaxException(-1 , 0);
    }

    private String controller(String op, String x, String y) throws MathException{
        System.out.println("op is : " + op + ", x is " + x + " , y is " + y);
        String result = "";
        switch (op) {
            case "+":
                result = add(x,y);
                break;
            case "-":
                result = subtract(x,y);
                break;
            case "*":
                result = multiply(x,y);
                break;
            case "/":
                if (y.equals("0")) {
                    throw new MathException();
                }
                result = divide(x,y);
                break;
            case "^":
                BigDecimal tmp = new BigDecimal(y);
                if (tmp.compareTo(calculatorUtil.MAX_INT) >= 0 || tmp.compareTo(calculatorUtil.MIN_INT) <= 0 ) {
                    throw new MathException();
                }
                result = pow(x,y);
                break;
        }
        System.out.println(result);
        return result;
    }

    public String trim(String a) {
        if (!a.contains(".")) {
            return a;
        }
        for (int i = 0; i < a.length(); i++){
            if (a.charAt(i) == '.') {
                for (int j = i + 1; j < a.length();j++) {
                    if (a.charAt(j) != '0') {
                        return a;
                    }
                }
                return a.substring(0,i);
            }
        }
        return a;
    }

    private String pow(String a, String b) {
        BigDecimal x = toBigDecimal(a);
        BigDecimal y = toBigDecimal(b);
        BigDecimal result = BigDecimalMath.pow(x,y,calculatorUtil.mathContext);
        return trim(result.toString());
    }

    private String add(String a, String b) {
        BigDecimal x = toBigDecimal(a);
        BigDecimal y = toBigDecimal(b);
        BigDecimal result = x.add(y);
        return trim(result.toString());
    }

    private String subtract(String a, String b) {
        BigDecimal x = toBigDecimal(a);
        BigDecimal y = toBigDecimal(b);
        BigDecimal result = x.subtract(y);
        return trim(result.toString());
    }

    private String multiply(String a, String b) {
        BigDecimal x = toBigDecimal(a);
        BigDecimal y = toBigDecimal(b);
        BigDecimal result = x.multiply(y);
        return trim(result.toString());
    }

    private String divide(String a, String b) {
        BigDecimal x = toBigDecimal(a);
        BigDecimal y = toBigDecimal(b);
        BigDecimal result = x.divide(y, calculatorUtil.mathContext);
        return trim(result.toString());
    }
}
