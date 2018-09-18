package ua.gmail.sydorenkomaryna.controller.command;

import ua.gmail.sydorenkomaryna.model.DBDataSet;
import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.model.DataSet;
import ua.gmail.sydorenkomaryna.view.View;

import java.sql.SQLException;

public class DeleteRow extends CommonCommand {

    private static final String COMMAND = "delete";

    public DeleteRow(View view, DBManager dbManager) {
        super(view, dbManager);
    }

    @Override
    public boolean isExecutable(String inputCommand) {
        return inputCommand.startsWith(COMMAND);
    }

    @Override
    public void execute(String inputCommand) {
        String commandDelete[] = inputCommand.split("\\|");
        if (commandDelete.length < 4 || commandDelete[1].trim().length() == 0) {
            errorMessage(inputCommand);
            return;
        }
        String tableName = commandDelete[1].trim();
        DataSet dataSet = new DBDataSet();
        dataSet.put(commandDelete[2].trim(), commandDelete[3].trim());
        try {
            int number = dbManager.deleteRow(tableName, dataSet);
            if (number == -1) {
                view.write(String.format("Tuple wasn't deleted from table '%s'. Please see logs", tableName));
            } else
                view.write(String.format("%d rows were successfully  deleted from table '%s'", number, tableName));
        } catch (SQLException e) {
            view.write(String.format("Data wasn't  deleted from table '%s'.The reason is:%s", tableName, e));
        }
    }
}
