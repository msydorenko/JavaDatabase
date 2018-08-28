package ua.gmail.sydorenkomaryna.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            return in.readLine();
        } catch (IOException e) {
            return null;
        }
    }
}
