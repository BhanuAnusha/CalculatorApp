package com.example.calculator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppTest {
    
    private App app;
    private Calculator calculator;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    void setUp() throws Exception {
        calculator = mock(Calculator.class);
        app = new App();
        
        // Replace var with explicit type for Java 11
        java.lang.reflect.Field field = App.class.getDeclaredField("calculator");
        field.setAccessible(true);
        field.set(app, calculator);
        
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void testShowMenu() {
        String result = app.showMenu();
        assertEquals("Available operations: add, subtract, multiply, divide", result);
    }

    @Test
    void testCalculate_Add() {
        when(calculator.add(5.0, 3.0)).thenReturn(8.0);
        String result = app.calculate("add", 5.0, 3.0);
        assertEquals("Result: 8.00", result);
    }

    @Test
    void testConsoleInterfaceThroughRun() {
        String input = "1\n5.0\n3.0\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        when(calculator.add(5.0, 3.0)).thenReturn(8.0);
        
        app.run("console");
        
        String output = outputStream.toString();
        assertTrue(output.contains("=== Calculator Console ==="));
        assertTrue(output.contains("Result: 8.00"));
    }
}