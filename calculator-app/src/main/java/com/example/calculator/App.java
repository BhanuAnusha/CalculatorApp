package com.example.calculator;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    private final Calculator calculator;
    private final InputStream inputStream;
    private final PrintStream outputStream;

    // Constructor for dependency injection (useful for testing)
    public App(Calculator calculator, InputStream inputStream, PrintStream outputStream) {
        this.calculator = calculator;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    // Default constructor (uses System.in and System.out)
    public App() {
        this(new Calculator(), System.in, System.out);
    }

    public void run() {
        try (Scanner scanner = new Scanner(inputStream)) {
            while (true) {
                outputStream.println("\nSimple Calculator");
                outputStream.println("1. Add");
                outputStream.println("2. Subtract");
                outputStream.println("3. Multiply");
                outputStream.println("4. Divide");
                outputStream.println("5. Exit");
                outputStream.print("Choose operation (1-5): ");

                try {
                    int choice = scanner.nextInt();
                    
                    if (choice == 5) {
                        outputStream.println("Exiting calculator...");
                        break;
                    }

                    if (choice < 1 || choice > 5) {
                        outputStream.println("Invalid choice! Please enter 1-5");
                        continue;
                    }

                    outputStream.print("Enter first number: ");
                    double num1 = scanner.nextDouble();
                    outputStream.print("Enter second number: ");
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
                                outputStream.println("Error: " + e.getMessage());
                                continue;
                            }
                            break;
                    }

                    outputStream.println("Result: " + result);
                    
                } catch (InputMismatchException e) {
                    outputStream.println("Invalid input! Please enter numbers only.");
                    scanner.nextLine(); // Clear the invalid input
                }
            }
        }
    }

    public static void main(String[] args) {
        new App().run(); // Delegate to the instance method
    }
}