package ua.gmail.sydorenkomaryna.controller;

import ua.gmail.sydorenkomaryna.model.JDBCManager;
import ua.gmail.sydorenkomaryna.view.Console;

public class Main {
    public static void main(String[] args) {
        Console console = new Console();
        JDBCManager dbManager = new JDBCManager();

        Controller controller = new Controller(dbManager, console);
        controller.start();
    }
}
