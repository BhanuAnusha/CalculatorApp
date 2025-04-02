package com.example.calculator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AppTest {
    private Calculator calculator;
    private ByteArrayOutputStream outputStream;
    private App app;

    @BeforeEach
    void setUp() {
        calculator = mock(Calculator.class);
        outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        app = new App(calculator, System.in, printStream);
    }

    @Test
    void testExitOption() {
        String input = "5\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        app = new App(calculator, inputStream, new PrintStream(outputStream));

        app.run();

        String output = outputStream.toString();
        assertTrue(output.contains("Exiting calculator..."));
    }

    @Test
    void testAddOperation() {
        String input = "1\n5.0\n3.0\n5\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        app = new App(calculator, inputStream, new PrintStream(outputStream));

        when(calculator.add(5.0, 3.0)).thenReturn(8.0);

        app.run();

        String output = outputStream.toString();
        assertTrue(output.contains("Result: 8.0"));
    }

    @Test
    void testSubtractOperation() {
        String input = "2\n10.0\n4.0\n5\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        app = new App(calculator, inputStream, new PrintStream(outputStream));

        when(calculator.subtract(10.0, 4.0)).thenReturn(6.0);

        app.run();

        String output = outputStream.toString();
        assertTrue(output.contains("Result: 6.0"));
    }

    @Test
    void testMultiplyOperation() {
        String input = "3\n6.0\n7.0\n5\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        app = new App(calculator, inputStream, new PrintStream(outputStream));

        when(calculator.multiply(6.0, 7.0)).thenReturn(42.0);

        app.run();

        String output = outputStream.toString();
        assertTrue(output.contains("Result: 42.0"));
    }

    @Test
    void testDivideOperation() {
        String input = "4\n20.0\n4.0\n5\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        app = new App(calculator, inputStream, new PrintStream(outputStream));

        when(calculator.divide(20.0, 4.0)).thenReturn(5.0);

        app.run();

        String output = outputStream.toString();
        assertTrue(output.contains("Result: 5.0"));
    }

    @Test
    void testDivideByZeroHandling() {
        String input = "4\n10.0\n0.0\n5\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        app = new App(calculator, inputStream, new PrintStream(outputStream));

        when(calculator.divide(10.0, 0.0)).thenThrow(new ArithmeticException("Division by zero"));

        app.run();

        String output = outputStream.toString();
        assertTrue(output.contains("Error: Division by zero"));
    }

    @Test
    void testInvalidChoiceHandling() {
        String input = "6\n5\n"; // User enters invalid choice (6), then exits
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        app = new App(calculator, inputStream, new PrintStream(outputStream));

        app.run();

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid choice! Please enter 1-5"));
    }

    @Test
    void testInvalidInputHandling() {
        String input = "abc\n5\n"; // User enters invalid input, then exits
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        app = new App(calculator, inputStream, new PrintStream(outputStream));

        app.run();

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid input! Please enter numbers only."));
    }
}
