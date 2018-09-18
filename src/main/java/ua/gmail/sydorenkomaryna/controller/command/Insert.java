package ua.gmail.sydorenkomaryna.controller.command;

import ua.gmail.sydorenkomaryna.model.DBDataSet;
import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.model.DataSet;
import ua.gmail.sydorenkomaryna.view.View;

public class Insert extends CommonCommand {
    private static final String COMMAND = "insert";

    public Insert(View view, DBManager dbManager) {
        super(view, dbManager);
    }

    @Override
    public boolean isExecutable(String inputCommand) {
        return inputCommand.startsWith(COMMAND);
    }

    @Override
    public void execute(String inputCommand) {
        String[] dataForInsert = inputCommand.split("\\|");
        if (dataForInsert.length < 4 || dataForInsert[1].trim().length() == 0 || dataForInsert.length % 2 != 0) {
            errorMessage(inputCommand);
            return;
        }

        String tableName = dataForInsert[1].trim();
        DataSet data = new DBDataSet();
        for (int i = 2; i < dataForInsert.length; i++) {
            data.put(dataForInsert[i].trim(), dataForInsert[++i].trim());
        }
        try{
            int num = dbManager.insertData(tableName, data);
            view.write(String.format("%d rows were successfully inserted into table '%s'", num, tableName));
        } catch (Exception e) {
            view.write(String.format("Data wasn't insert into table '%s'." +
                    " The reason is '%s'.", tableName, e.getMessage()));
        }
    }
}
