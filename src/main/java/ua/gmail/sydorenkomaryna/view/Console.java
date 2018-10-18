package ua.gmail.sydorenkomaryna.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Implementation of View interface for Console input and output
 */

public class Console implements View {

    /**
     * Writes specified message to Console
     *
     * @param message print at console
     */
    @Override
    public void write(String message) {
        System.out.println(message);
    }

    /**
     * Returns String from standard input stream
     *
     * @return String or null if input stream is empty
     */
    @Override
    public String read() {
        try {
            Scanner scanner = new Scanner(System.in);
            return scanner.nextLine();
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
