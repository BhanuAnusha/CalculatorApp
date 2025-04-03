package com.example.calculator;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        double a = 10;
        double b = 5;

        System.out.println("Add: " + calculator.add(a, b));
        System.out.println("Subtract: " + calculator.subtract(a, b));
        System.out.println("Multiply: " + calculator.multiply(a, b));
        System.out.println("Divide: " + calculator.divide(a, b));
    }
}
