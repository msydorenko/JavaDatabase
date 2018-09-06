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
    public boolean isExecute(String inputCommand) {
        return inputCommand.startsWith(COMMAND);
    }

    @Override
    public void execute(String inputCommand) {
        String[] commandCreate = inputCommand.split("\\|");
        if (commandCreate.length <= 2) {
            errorMessage(inputCommand);
            return;
        }
        String tableName = commandCreate[1].trim();
        if (tableName.length() == 0) {
            errorMessage(inputCommand);
            return;
        }
        int result = -1;
        try {
            Set<String> columnsName = new LinkedHashSet<>();
            for (int index = 2; index < commandCreate.length; index++) {
                columnsName.add(commandCreate[index].trim());
            }
            result = dbManager.createTables(tableName, columnsName);
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
