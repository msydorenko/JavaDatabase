package ua.gmail.sydorenkomaryna.controller.command;

import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.view.View;

/**
 * Abstract super class for commands
 */

public abstract class CommonCommand implements Command {
    View view;
    DBManager dbManager;

    CommonCommand(View view, DBManager dbManager){
        this.view = view;
        this.dbManager = dbManager;
    }
    void ErrorMessage(String command) {
        view.write(String.format("Command '%s' is not allowed", command));
    }
}
