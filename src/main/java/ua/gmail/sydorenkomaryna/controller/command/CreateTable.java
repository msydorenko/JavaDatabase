package ua.gmail.sydorenkomaryna.controller.command;

import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.view.View;

import java.util.LinkedHashSet;
import java.util.Set;

public class CreateTable extends CommonCommand {
    private final String COMMAND = "create";

    public CreateTable(View view, DBManager dbManager) {
        super(view, dbManager);
    }

    @Override
    public boolean isExecutable(String inputCommand) {
        return inputCommand.startsWith(COMMAND);
    }

    @Override
    public void execute(String inputCommand) {
        String[] dataForCreatingTable = inputCommand.split("\\|");
        if (dataForCreatingTable.length <= 2) {
            errorMessage(inputCommand);
            return;
        }
        String tableName = dataForCreatingTable[1].trim();
        if (tableName.length() == 0) {
            errorMessage(inputCommand);
            return;
        }
        try {
            Set<String> columnsName = new LinkedHashSet<>();
            for (int index = 2; index < dataForCreatingTable.length; index++) {
                columnsName.add(dataForCreatingTable[index].trim());
            }
            int result = dbManager.createTables(tableName, columnsName);
            if (result == -1) {
                view.write(String.format("This table '%s' wasn't created. You can see logs and find reasons", tableName));
            }else {
                view.write(String.format("Congratulates! Table '%s' was successfully created", tableName));
            }
        } catch (Exception e) {
            view.write(String.format("This table '%s' wasn't created. The reason is: %s", tableName, e.getMessage()));
        }
    }
}
