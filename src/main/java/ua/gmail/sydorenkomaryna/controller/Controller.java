package ua.gmail.sydorenkomaryna.controller;

import ua.gmail.sydorenkomaryna.controller.command.Command;
import ua.gmail.sydorenkomaryna.controller.command.Exit;
import ua.gmail.sydorenkomaryna.controller.command.ExitException;
import ua.gmail.sydorenkomaryna.controller.command.Help;
import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.view.View;

import java.util.ArrayList;

public class Controller {
    private View view;
    private ArrayList<Command> commands;

    public Controller(DBManager dbManager, View view) {
        this.view = view;
        commands = new ArrayList<Command>();
        commands.add(new Help(view));
        commands.add(new Exit(view, dbManager));
    }

    public void start() {
        view.write("Hello, my dear friend! You are using JavaRock application =)");
        view.write("Please, enter a command. For help use command help");
        try{
            while (true){
                try{
                    String inputCommand = view.read().trim();
                    for (Command command : commands) {
                        if(command.isExecute(inputCommand)){
                            command.execute(inputCommand);
                        }
                    }

                }catch (Exception e) {
                    if (e instanceof ExitException){
                        throw e;
                    }else {
                        e.getMessage();
                    }
                }
            }
        }catch (ExitException e){
            //NOP
        }

    }
}
