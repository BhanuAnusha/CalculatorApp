package com.example.calculator;

import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("\nSimple Calculator");
                System.out.println("1. Add");
                System.out.println("2. Subtract");
                System.out.println("3. Multiply");
                System.out.println("4. Divide");
                System.out.println("5. Exit");
                System.out.print("Choose operation (1-5): ");

                try {
                    int choice = scanner.nextInt();
                    
                    if (choice == 5) {
                        System.out.println("Exiting calculator...");
                        break;
                    }

                    if (choice < 1 || choice > 5) {
                        System.out.println("Invalid choice! Please enter 1-5");
                        continue;
                    }

                    System.out.print("Enter first number: ");
                    double num1 = scanner.nextDouble();
                    System.out.print("Enter second number: ");
                    double num2 = scanner.nextDouble();

                    double result = 0;
                    switch (choice) {
                        case 1:
                            result = calculator.add(num1, num2);
                            break;
                        case 2:
                            result = calculator.subtract(num1, num2);
                            break;
                        case 3:
                            result = calculator.multiply(num1, num2);
                            break;
                        case 4:
                            try {
                                result = calculator.divide(num1, num2);
                            } catch (ArithmeticException e) {
                                System.out.println("Error: " + e.getMessage());
                                continue;
                            }
                            break;
                    }

                    System.out.println("Result: " + result);
                    
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input! Please enter numbers only.");
                    scanner.nextLine(); // Clear the invalid input
                }
            }
        }
    }
}