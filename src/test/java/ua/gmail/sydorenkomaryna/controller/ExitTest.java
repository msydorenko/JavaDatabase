package ua.gmail.sydorenkomaryna.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.gmail.sydorenkomaryna.controller.command.Command;
import ua.gmail.sydorenkomaryna.controller.command.Exit;
import ua.gmail.sydorenkomaryna.controller.command.ExitException;
import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.view.View;

import static org.junit.jupiter.api.Assertions.*;

public class ExitTest {
    private static View view;
    private static DBManager dbManager;
    private static Command command;

    @BeforeAll
    static void setup() {
        view = Mockito.mock(View.class);
        dbManager = Mockito.mock(DBManager.class);
        command = new Exit(view, dbManager);
    }

    @Test
    public void testIsExecutableTrue() {
        boolean result = command.isExecutable("exit");
        assertTrue(result);
    }

    @Test
    public void testIsExecutableFalse() {
        boolean result = command.isExecutable("qwe");
        assertFalse(result);
    }

    @Test
    public void testExecuteWriteMessage() {
        try {
            command.execute("exit");
            fail("Expected ExitException");
        } catch (ExitException e) {
            /*NOP*/
        }

        Mockito.verify(view).write("Goodbye!");
    }

    @Test
    public void testExecuteThrowsExitException() {
        assertThrows(ExitException.class, () -> command.execute("exit"));
    }
}
