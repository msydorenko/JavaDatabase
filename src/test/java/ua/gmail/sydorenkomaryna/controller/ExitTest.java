package ua.gmail.sydorenkomaryna.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.gmail.sydorenkomaryna.controller.command.Command;
import ua.gmail.sydorenkomaryna.controller.command.Exit;
import ua.gmail.sydorenkomaryna.controller.command.ExitException;
import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.view.View;

import static org.junit.jupiter.api.Assertions.*;

class ExitTest {
    private static View view;
    private static Command command;

    @BeforeEach
    void setup() {
        DBManager dbManager = Mockito.mock(DBManager.class);
        view = Mockito.mock(View.class);
        command = new Exit(view, dbManager);
    }

    @Test
    void testIsExecutableTrue() {
        boolean result = command.isExecutable("exit");
        assertTrue(result);
    }

    @Test
    void testIsExecutableFalse() {
        boolean result = command.isExecutable("qwe");
        assertFalse(result);
    }

    @Test
    void testExecuteWriteMessage() {
        try {
            command.execute("exit");
            fail("Expected ExitException");
        } catch (ExitException e) {
            /*NOP*/
        }

        Mockito.verify(view).write("Goodbye!");
    }

    @Test
    void testExecuteThrowsExitException() {
        assertThrows(ExitException.class, () -> command.execute("exit"));
    }
}
