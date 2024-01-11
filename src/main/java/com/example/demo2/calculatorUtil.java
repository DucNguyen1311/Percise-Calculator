package com.example.demo2;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import static ch.obermuhlner.math.big.BigDecimalMath.toBigDecimal;
public class calculatorUtil {
    public static final MathContext mathContext = new MathContext(100);
    public static final BigDecimal MIN_INT =  toBigDecimal("-2147483648");
    public static final BigDecimal MAX_INT = toBigDecimal( "2147483647");
    public static final String[] opeValue = new String[] { "+", "-", "*", "/", "^", "√","(",")"};
    public static final Set<String> Operators = new HashSet<>(Arrays.asList(opeValue));

    public static final String[] numberValue = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10","."};
    public static final Set<String> Numbers = new HashSet<>(Arrays.asList(numberValue));

    public static Map<String, Integer> priority = initMap();

    private static Map<String, Integer> initMap() {
        return Map.of("+", 1, "-", 1, "*", 2, "/", 2, "^", 3, "√", 3, "(", 0, ")", 0, "%", 2);
    }
    public calculatorUtil() {
    }

    public void printQueue(Queue<String> function) {
        int size = function.size();
        for (int i = 0; i < size ; i++){
            String tmp = function.poll();
            function.add(tmp);
            System.out.println(tmp);
        }
    }
    public Queue<String> stringToQueue(String function) {
        //assume that the input string is correct
        Queue<String> result = new LinkedList<String>();
        Stack<String> storage = new Stack<String>();
        //tmp is used to store number
        String tmp = "";
        for (int i = 0; i < function.length();i++) {
            String functionCharAt = "";
            functionCharAt += function.charAt(i);
            if (i == 0) {
                if (function.charAt(0) == '+' || function.charAt(0) == '-' || Numbers.contains(functionCharAt)) {
                    tmp += functionCharAt;
                }
                else {
                    result.add(functionCharAt);
                    storage.push(functionCharAt);
                }
                continue;
            }
            String functionCharPrevious = "";
            functionCharPrevious += function.charAt(i-1);
//            System.out.println(functionCharAt);

            if (Numbers.contains(functionCharAt)) {
//                System.out.println(functionCharAt + " is a number");
                tmp += functionCharAt;
            }

            if (Operators.contains(functionCharAt)) {
                if (result.isEmpty()) {
                    if (functionCharAt.equals("(") && Numbers.contains(functionCharPrevious)) {
                        if (!tmp.isEmpty()) {result.add(tmp); storage.add(tmp);}
                        result.add("*");
                        result.add(functionCharAt);
                        storage.add("*");
                        storage.add(functionCharAt);
                        tmp = "";
                        continue;
                    }
                    if(Numbers.contains(functionCharPrevious)) {
                        result.add(tmp);
                        result.add(functionCharAt);
                        storage.add(tmp);
                        storage.add(functionCharAt);
                        tmp = "";
                        continue;
                    }
                    if ((functionCharAt.equals("+") || functionCharAt.equals("-"))&& !Numbers.contains(functionCharPrevious)) {
                        tmp += functionCharAt;
                        continue;
                    }
                    result.add(tmp + 1);
                    result.add("*");
                    result.add(functionCharAt);
                    storage.push(tmp + 1);
                    storage.push("*");
                    storage.push(functionCharAt);
                    tmp = "";
                    continue;
                }
                if (functionCharAt.equals("(") && Numbers.contains(functionCharPrevious)) {
                    result.add(tmp);
                    result.add("*");
                    result.add(functionCharAt);
                    storage.add(tmp);
                    storage.add("*");
                    storage.add(functionCharAt);
                    tmp = "";
                    continue;
                }
                if (!Numbers.contains(functionCharPrevious)) {
                    if (Operators.contains(storage.peek())
                            && (functionCharAt.equals("+") || functionCharAt.equals("-"))) {
                        tmp += functionCharAt;
                        continue;
                    }
                }

                if (!tmp.isEmpty()) {result.add(tmp); storage.push(tmp);}
                result.add(functionCharAt);
                storage.push(functionCharAt);
                tmp = "";
            }
        }
        if (!tmp.isEmpty()) {result.add(tmp); storage.push(tmp);}
        return result;
    };

    public Queue<String> numberModify(Queue<String> function) {
            Queue<String> result = new LinkedList<String>();
            int size = function.size();
            for (int i = 0; i < size; i++) {
                String functionCharAt = "";
                functionCharAt += function.peek().charAt(0);
                String modify = function.peek();
                if (function.peek().length() == 1 || Numbers.contains(functionCharAt)) {
                    if (modify.equals("0")) {
                        result.add(modify);
                        continue;
                    }
                    while(modify.startsWith("0")) {
                        modify = modify.substring(1);
                    }
                    result.add(modify);
                    function.poll();
                    continue;
                }
                int sign = 1;
                int j = 0;
                while (modify.charAt(j) == '+' || modify.charAt(j) =='-') {
                    if (modify.charAt(j) == '+') {
                        j++;
                        continue;
                    }
                    sign *= -1;
                    j++;
                }
                modify = modify.replace("+", "");
                modify = modify.replace("-", "");
                while(modify.startsWith("0")) {
                    modify = modify.substring(1);
                }
                if (sign == -1) {
                    modify = '-' + modify;
                }
                result.add(modify);
                function.poll();
            }
            return result;
    }
}
