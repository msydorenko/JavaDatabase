package ua.gmail.sydorenkomaryna.controller;

import ua.gmail.sydorenkomaryna.controller.command.*;
import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.view.View;

import java.util.ArrayList;

/**
 * The controller accepts input data and converts them into commands for the model and view
 */

public class Controller {
    private View view;
    private ArrayList<Command> commands;

    /**
     * Constructor meneges DBManager and View
     *
     * @param dbManager
     * @param view
     */
    public Controller(DBManager dbManager, View view) {
        this.view = view;
        commands = new ArrayList<>();
        commands.add(new Help(view));
        commands.add(new ConnectToDB(view, dbManager));
        commands.add(new CreateTable(view, dbManager));
        commands.add(new DeleteRow(view, dbManager));
        commands.add(new Drop(view, dbManager));
        commands.add(new Exit(view, dbManager));
        //last command
        commands.add(new UnsupportedCommand(view, dbManager));
    }

    public void start() {
        view.write("Hello, my dear friend! You are using JavaRock application =)");
        view.write("Please, enter a command. For help use command help");

        try {
            while (true) {
                try {
                    String input = view.read().trim();
                    for (Command command : commands) {
                        if (command.isExecute(input)) {
                            command.execute(input);
                            break;
                        }
                    }
                } catch (Exception e) {
                    if (e instanceof ExitException)
                        throw e;
                    else {
                        view.write(e.getMessage());
                    }
                    break;
                }
            }
        } catch (ExitException e) {
            //NOP
        }

    }
}
