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

    Exit(View view, DBManager dbManager) {
        super(view, dbManager);
    }

    @Override
    public boolean isExecute(String command) {
        return command.startsWith(COMMAND);
    }

    @Override
    public void execute(String command) {
        view.write("Goodbye!");
        try {
            dbManager.closeConnection();
        } catch (SQLException e) {
            view.write(e.getMessage());
        }
        throw new ExitException();
    }
}
