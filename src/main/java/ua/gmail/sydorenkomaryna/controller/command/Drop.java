package ua.gmail.sydorenkomaryna.controller.command;

import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.view.View;

public class Drop extends CommonCommand {

    private static final String COMMAND = "drop";

    public Drop(View view, DBManager dbManager) {
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
        if (cmdParams.length < 2 || cmdParams[1].trim().length() == 0) {
            errorMessage(inputCommand);
            return;
        }
        String tableName = cmdParams[1].trim();
        int result = -1;
        try {
            result = dbManager.dropTable(tableName);
            if (result == -1) {
                LOG.warn("Table '{}' wasn't deleted. Please see the reason in logs", tableName);
                view.write(String.format("Table '%s' wasn't deleted. Please see the reason in logs", tableName));
            } else
                view.write(String.format("Table '%s' was successfully deleted", tableName));
        } catch (Exception e) {
            LOG.error("", e);
            view.write(String.format("Table '%s' wasn't deleted. The reason is: %s", tableName, e.getMessage()));
        }
        LOG.traceExit();
    }
}
