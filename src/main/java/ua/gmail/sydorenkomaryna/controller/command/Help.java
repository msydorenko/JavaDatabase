package ua.gmail.sydorenkomaryna.controller.command;

import ua.gmail.sydorenkomaryna.view.View;

public class Help implements Command {
    private static final String COMMAND = "help";
    private final View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean isExecute(String command) {
        return command.equals(COMMAND);
    }

    @Override
    public void execute(String command) {
        printHelp();
    }

    private void printHelp() {
        view.write("All list of commands:");
        view.write("To see list of commands use command help");
        view.write("    'help'");
        view.write(" To exit");
        view.write("    'exit'");
    }
}
