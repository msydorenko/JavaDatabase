package ua.gmail.sydorenkomaryna.controller.command;

import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.model.DataSet;
import ua.gmail.sydorenkomaryna.view.View;

import java.sql.SQLException;
import java.util.List;

public class ViewData extends CommonCommand {
    private static final String COMMAND = "find";

    public ViewData(View view, DBManager dbManager) {
        super(view, dbManager);

    }

    @Override
    public boolean isExecutable(String inputCommand) {
        return inputCommand.startsWith(COMMAND);
    }

    @Override
    public void execute(String inputCommand) {
        String[] dataForFind = inputCommand.split("\\|");
        if (dataForFind.length != 2 || dataForFind[1].trim().length() == 0) {
            errorMessage(inputCommand);
            return;
        }
        String tableName = dataForFind[1].trim();
        try {
            List<DataSet> rows = dbManager.getTableData(tableName);
            if (rows == null) {
                view.write(String.format("Cannot show data from table '%s'!", tableName));
            } else if (rows.size() == 0) {
                view.write(String.format("Table '%s' is empty", tableName));
            } else {
                view.write(String.format("Table '%s':", tableName));
                view.write(TableCreator.createView(rows, dbManager.getNameColumns(tableName)));
                view.write("The End!");
            }
        } catch (SQLException e) {
            view.write(String.format("Can't show data of '%s' table. " +
                    "The reason is %s", tableName, e.getMessage()));
        }
    }
}
