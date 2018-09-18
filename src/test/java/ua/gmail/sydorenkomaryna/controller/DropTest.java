package ua.gmail.sydorenkomaryna.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.gmail.sydorenkomaryna.controller.command.Command;
import ua.gmail.sydorenkomaryna.controller.command.Drop;
import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.view.View;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DropTest {
    private static DBManager dbManager;
    private static View view;
    private static Command command;

    @BeforeEach
    void setup() {
        dbManager = mock(DBManager.class);
        view = mock(View.class);
        command = new Drop(view, dbManager);
    }

    @Test
    void testIsExecutable() {
        assertTrue(command.isExecutable("drop|title"));
    }

    @Test
    void testIsNotExecutable() {
        assertFalse(command.isExecutable("dddrop|title"));
    }

    @Test
    void testExecuteWithCorrectParam() throws SQLException {
        command.execute("drop|title");
        verify(dbManager).dropTable("title");
        verify(view).write("Table 'title' was successfully deleted");
    }

    @Test
    void testExecuteWithoutParam(){
        command.execute("drop|  |");
        verify(view).write("Command 'drop|  |' is not allowed! ");
    }

}
