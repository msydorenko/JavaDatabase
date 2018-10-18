package ua.gmail.sydorenkomaryna.controller.command;

import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.view.View;

public class TablesList extends CommonCommand {
    private final static String COMMAND = "tables";

    public TablesList(View view, DBManager dbManager) {
        super(view, dbManager);
    }

    @Override
    public boolean isExecutable(String inputCommand) {
        return inputCommand.startsWith(COMMAND);
    }

    @Override
    public void execute(String inputCommand) {
        LOG.traceEntry();
        try {
            view.write(dbManager.getNamesAllTablesInDataBase());
        } catch (Exception e) {
            LOG.error("", e);
            view.write("Can't print tables names. The reason is :");
            view.write(e.getMessage());
        }
        LOG.traceExit();
    }
}
