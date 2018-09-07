package ua.gmail.sydorenkomaryna.controller.command;

import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.view.View;

public class Drop extends CommonCommand {

    private static final String COMMAND = "drop";

    public Drop(View view, DBManager dbManager) {
        super(view, dbManager);

    }

    @Override
    public boolean isExecute(String inputCommand) {
        return inputCommand.startsWith(COMMAND);
    }

    @Override
    public void execute(String inputCommand) {
        String[] commandDrop = inputCommand.split("\\|");
        if (commandDrop.length < 2 && commandDrop[1].trim().length() == 0) {
            errorMessage(inputCommand);
            return;
        }
        String nameTable = commandDrop[1].trim();
        try {
            int result = dbManager.dropTable(nameTable);
            if (result == -1) {
                view.write(String.format("Table '%s' wasn't deleted. Pleas see logs.", nameTable));
            } else {
                view.write(String.format("Table '%s' was successfully deleted", nameTable));
            }
        } catch (Exception e) {
            view.write(String.format("Table '%s' wasn't deleted. The reason is: %s", nameTable, e.getMessage()));
        }
    }
}
