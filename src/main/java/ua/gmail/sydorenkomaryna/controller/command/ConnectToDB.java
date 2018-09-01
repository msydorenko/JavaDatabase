package ua.gmail.sydorenkomaryna.controller.command;

import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.view.View;

/**
 * Input request connection to database
 */
public class ConnectToDB extends CommonCommand {
    private static final String COMMAND = "connect";

    public ConnectToDB(View view, DBManager dbManager) {
        super(view, dbManager);
    }

    @Override
    public boolean isExecute(String inputCommand) {
        return inputCommand.startsWith(COMMAND);
    }

    @Override
    public void execute(String inputCommand) {
        connectToDB(inputCommand);
    }

    private void connectToDB(String inputCommand) {
        try {
            String[] dbParam = inputCommand.split("\\|");
            if (dbParam.length != 4) {
                errorMessage(inputCommand);
                throw new IllegalArgumentException(
                        "Format of command mast be: connect | database | username | password.");
            }
            String dbName = dbParam[1].trim();
            String userName = dbParam[2].trim();
            String password = dbParam[3].trim();

            dbManager.makeConnection(dbName, userName, password);
        } catch (Exception e) {
            view.write("You made a mistake!");
            view.write("See details below.");

            view.write(e.getMessage());
        }
    }
}
