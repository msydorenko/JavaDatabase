package ua.gmail.sydorenkomaryna.controller.command;

import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.view.View;

public class Clear extends CommonCommand {
    private final static String COMMAND = "clear";

    public Clear(View view, DBManager dbManager) {
        super(view, dbManager);
    }

    @Override
    public boolean isExecutable(String inputCommand) {
        return inputCommand.startsWith(COMMAND);
    }

    @Override
    public void execute(String inputCommand) {
        LOG.traceEntry();

        String[] cmdParams = inputCommand.split("\\|");
        if (cmdParams.length < 2 || cmdParams[1].trim().length() < 1) {
            errorMessage(inputCommand);
            return;
        }
        String tableName = cmdParams[1].trim();
        try {
            int numRows = dbManager.truncateTable(tableName);
            if (numRows == -1) {
                view.write(String.format("Table '%s' wasn't cleared", tableName));
                LOG.warn("Table '{}' wasn't cleared", tableName);
            } else {
                view.write(String.format("Table '%s' was successfully cleared", tableName));
                LOG.trace("Table '{}' was successfully cleared", inputCommand);
            }
        } catch (Exception e) {
            view.write(String.format("Table '%s' wasn't cleared", tableName));
            view.write(e.getMessage());
            LOG.error("", e);
        }
        LOG.traceExit();
    }
}
