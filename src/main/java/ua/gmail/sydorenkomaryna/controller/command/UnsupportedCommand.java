package ua.gmail.sydorenkomaryna.controller.command;

import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.view.View;

/**
 * Incorrect input command
 */
public class UnsupportedCommand extends CommonCommand {

    public UnsupportedCommand(View view, DBManager dbManager) {
        super(view, dbManager);
    }

    @Override
    public boolean isExecute(String inputCommand) {
        return true;
    }

    @Override
    public void execute(String inputCommand) {
        errorMessage(inputCommand);
    }
}
