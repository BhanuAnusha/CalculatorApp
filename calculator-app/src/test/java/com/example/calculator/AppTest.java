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
        // Simulate user entering "5" (exit)
        String input = "5\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        app = new App(calculator, inputStream, new PrintStream(outputStream));

        app.run(); // Should exit immediately

        String output = outputStream.toString();
        assertTrue(output.contains("Exiting calculator..."));
    }

    @Test
    void testAddOperation() {
        // Simulate: 1 (Add), 5.0, 3.0, then 5 (Exit)
        String input = "1\n5.0\n3.0\n5\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        app = new App(calculator, inputStream, new PrintStream(outputStream));

        when(calculator.add(5.0, 3.0)).thenReturn(8.0);

        app.run();

        String output = outputStream.toString();
        assertTrue(output.contains("Result: 8.0"));
    }

    @Test
    void testInvalidInput() {
        // Simulate: "abc" (invalid), then 5 (exit)
        String input = "abc\n5\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        app = new App(calculator, inputStream, new PrintStream(outputStream));

        app.run();

        String output = outputStream.toString();
        assertTrue(output.contains("Invalid input! Please enter numbers only."));
    }
}