package ua.gmail.sydorenkomaryna.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.view.View;

/**
 * Abstract super class for commands
 */

public abstract class CommonCommand implements Command {
    final static Logger LOG = LogManager.getLogger();
    final View view;
    final DBManager dbManager;

    CommonCommand(View view, DBManager dbManager) {
        this.view = view;
        this.dbManager = dbManager;
    }

    void errorMessage(String inputCommand) {
        view.write(String.format("Command '%s' is not allowed! ", inputCommand));
        view.write("If you want to see list of all commands use command help.");
        LOG.warn("Command {} is not allowed! ", inputCommand);
    }
}
