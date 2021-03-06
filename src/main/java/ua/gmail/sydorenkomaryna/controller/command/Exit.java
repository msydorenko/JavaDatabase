package ua.gmail.sydorenkomaryna.controller.command;

import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.view.View;

import java.sql.SQLException;

/**
 * Command to close database connection
 * and exit from application
 */

public class Exit extends CommonCommand {
    private static final String COMMAND = "exit";

    public Exit(View view, DBManager dbManager) {
        super(view, dbManager);
    }

    @Override
    public boolean isExecutable(String inputCommand) {
        return inputCommand.startsWith(COMMAND);
    }

    @Override
    public void execute(String inputCommand) {
        view.write("Goodbye!");
        try {
            dbManager.closeConnection();
        } catch (SQLException e) {
            view.write(e.getMessage());
        }
        throw new ExitException();
    }
}
