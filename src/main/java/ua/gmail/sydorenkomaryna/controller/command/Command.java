package ua.gmail.sydorenkomaryna.controller.command;

/**
 * Interface for Command pattern
 */

public interface Command {
    boolean isExecutable(String inputCommand);
    void execute(String inputCommand);
}
