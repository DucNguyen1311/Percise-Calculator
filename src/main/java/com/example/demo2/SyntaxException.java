package com.example.demo2;

public class SyntaxException extends calculatorException{
    public SyntaxException(int number, int block) {
        super("Syntax Error!!! error at index " + number + " , caught at block " + block);
    }
}
