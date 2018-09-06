package ua.gmail.sydorenkomaryna.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.gmail.sydorenkomaryna.view.Console;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleTest {
    private final Console console = new Console();
    private final ByteArrayOutputStream outData = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errorData = new ByteArrayOutputStream();
    private ByteArrayInputStream inputData;

    @BeforeEach
    void setup() {
        System.setOut(new PrintStream(outData));
        System.setErr(new PrintStream(errorData));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(System.out);
        System.setErr(System.err);
    }

    @Test
    void writeTest() {
        console.write("javaDatabase");

        assertEquals("javaDatabase" + System.lineSeparator(), outData.toString());
    }

    @Test
    void readTest() {
        inputData = new ByteArrayInputStream(new byte[]{'m'});

        assertEquals('m', inputData.read());
    }
}
