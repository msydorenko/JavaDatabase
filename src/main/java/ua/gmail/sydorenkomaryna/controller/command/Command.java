package ua.gmail.sydorenkomaryna.controller.command;

/**
 * Interface for Command pattern
 */

public interface Command {
    boolean isExecute(String command);
    void execute(String command);
}
