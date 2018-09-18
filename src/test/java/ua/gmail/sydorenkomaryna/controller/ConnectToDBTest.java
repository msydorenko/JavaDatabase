package ua.gmail.sydorenkomaryna.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.gmail.sydorenkomaryna.controller.command.Command;
import ua.gmail.sydorenkomaryna.controller.command.ConnectToDB;
import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.view.View;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ConnectToDBTest {
    private static DBManager dbManager;
    private static View view;
    private static Command command;

    @BeforeAll
    static void setup() {
        dbManager = mock(DBManager.class);
        view = mock(View.class);
        command = new ConnectToDB(view, dbManager);
    }

    @Test
    void testIsExecutableFalse() {
        assertFalse(command.isExecutable("incorrectCommand"));
    }

    @Test
    void testExecuteWithCorrectParameters() {
        command.execute("connect|sqlcmd|postgres|postgres");
        verify(dbManager).makeConnection("sqlcmd", "postgres", "postgres");
        verify(view).write("You are connected to your DB!");
    }

    @Test
    void testExecuteWithWrongParameters() {
        command.execute("connect|sqlcmd|postgres|postgres|something");
        verify(view).write("Command 'connect|sqlcmd|postgres|postgres|something' is not allowed! ");
        verify(view).write("If you want to see list of all commands use command help.");
    }
}
