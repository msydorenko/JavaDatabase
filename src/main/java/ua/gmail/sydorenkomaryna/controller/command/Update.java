package ua.gmail.sydorenkomaryna.controller.command;

import ua.gmail.sydorenkomaryna.model.DBDataSet;
import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.view.View;

import java.sql.SQLException;

/**
 * Requests to update data in table according to specified condition
 */
public class Update extends CommonCommand {
    private final static String COMMAND = "update";

    public Update(View view, DBManager dbManager) {
        super(view, dbManager);
    }

    @Override
    public boolean isExecutable(String command) {
        return command.startsWith(COMMAND);
    }

    @Override
    public void execute(String command) {
        LOG.traceEntry();

        String[] cmdParams = command.split("\\|");
        if (cmdParams.length < 6 || cmdParams.length % 2 != 0 || cmdParams[1].trim().length() < 1) {
            errorMessage(command);
            return;
        }
        String tableName = cmdParams[1].trim();
        DBDataSet condition = new DBDataSet();
        condition.put(cmdParams[2].trim(), cmdParams[3].trim());
        DBDataSet data = new DBDataSet();
        for (int i = 4; i < cmdParams.length; i++) {
            data.put(cmdParams[i].trim(), cmdParams[++i].trim());
        }
        try {
            int num = dbManager.updateRows(tableName, condition, data);
            if (num == -1) {
                view.write(String.format("Data wasn't updated in table '%s'", tableName));
                LOG.warn("Data wasn't updated in table '{}'", tableName);
            } else
                view.write(String.format("%d rows were updated in table '%s'", num, tableName));
        } catch (SQLException e) {
            view.write(String.format("Data wasn't updated in table '%s'", tableName));
            view.write(e.getMessage());
            LOG.error("", e);
        }
        LOG.traceExit();
    }
}