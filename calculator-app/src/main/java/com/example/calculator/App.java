package com.example.calculator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import java.util.Scanner;

@RestController
@RequestMapping("/api/calculator")
@Component
public class App implements CommandLineRunner {
    
    private final Calculator calculator = new Calculator();

    // REST API Endpoints
    @GetMapping("/menu")
    public String showMenu() {
        return "Available operations: add, subtract, multiply, divide";
    }

    @PostMapping("/calculate")
    public String calculate(
            @RequestParam String operation,
            @RequestParam double num1,
            @RequestParam double num2) {
        
        try {
            double result = performCalculation(operation, num1, num2);
            return String.format("Result: %.2f", result);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    // Console Interface (runs on startup)
    @Override
    public void run(String... args) {
        if (args.length > 0 && args[0].equals("console")) {
            startConsoleInterface();
        }
    }

    private void startConsoleInterface() {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("\n=== Calculator Console ===");
            System.out.println("1. Add");
            System.out.println("2. Subtract");
            System.out.println("3. Multiply");
            System.out.println("4. Divide");
            System.out.print("Choose operation (1-4): ");

            int choice = scanner.nextInt();
            System.out.print("Enter first number: ");
            double num1 = scanner.nextDouble();
            System.out.print("Enter second number: ");
            double num2 = scanner.nextDouble();

            String operation = getOperationFromChoice(choice);
            double result = performCalculation(operation, num1, num2);
            
            System.out.printf("Result: %.2f%n", result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private double performCalculation(String operation, double num1, double num2) {
        switch (operation.toLowerCase()) {
            case "add":
            case "1":
                return calculator.add(num1, num2);
            case "subtract":
            case "2":
                return calculator.subtract(num1, num2);
            case "multiply":
            case "3":
                return calculator.multiply(num1, num2);
            case "divide":
            case "4":
                if (num2 == 0) throw new ArithmeticException("Cannot divide by zero");
                return calculator.divide(num1, num2);
            default:
                throw new IllegalArgumentException("Invalid operation");
        }
    }

    private String getOperationFromChoice(int choice) {
        switch (choice) {
            case 1: return "add";
            case 2: return "subtract";
            case 3: return "multiply";
            case 4: return "divide";
            default: throw new IllegalArgumentException("Invalid choice");
        }
    }
}