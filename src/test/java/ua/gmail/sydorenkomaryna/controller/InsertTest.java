package ua.gmail.sydorenkomaryna.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.gmail.sydorenkomaryna.controller.command.Command;
import ua.gmail.sydorenkomaryna.controller.command.Insert;
import ua.gmail.sydorenkomaryna.model.DBDataSet;
import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.model.DataSet;
import ua.gmail.sydorenkomaryna.view.View;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class InsertTest {
    private static DBManager dbManager;
    private static View view;
    private static Command command;

    @BeforeEach
    void setup() {
        dbManager = mock(DBManager.class);
        view = mock(View.class);
        command = new Insert(view, dbManager);
    }

    @Test
    void testIsExecutable() {
        assertTrue(command.isExecutable("insert|book"));
    }

    @Test
    void testIsNotExecutable() {
        assertFalse(command.isExecutable("iiiinsert|book"));
    }

    @Test
    void testExecuteWithCorrectData() throws SQLException {
        DataSet data = new DBDataSet();
        data.put("title", "Harry Potter");
        data.put("author", "J. Rowling");

        command.execute("insert|book|title|Harry Potter|author|J. Rowling");
        verify(dbManager).insertData("book", data);
        verify(view).write("0 rows were successfully inserted into table 'book'");
    }

    @Test
    void testExecuteWithTrimSpace() throws SQLException {
        DataSet data2 = new DBDataSet();
        data2.put("title", "Harry Potter");
        data2.put("author", "J. Rowling");

        command.execute("insert | book| title  | Harry Potter  | author|J. Rowling ");
        verify(dbManager).insertData("book", data2);
        verify(view).write("0 rows were successfully inserted into table 'book'");
    }

    @Test
    void testExecuteWithWrongParam() {
        command.execute("insert | | title  | Harry Potter  | author|J. Rowling ");
        verify(view).write("Command 'insert | | title  | Harry Potter  | author|J. Rowling ' is not allowed! ");
        verify(view).write("If you want to see list of all commands use command help.");
    }

    @Test
    void testExecuteWithWrongLength() {
        command.execute("insert|book|title|Harry Potter|author|");
        verify(view).write("Command 'insert|book|title|Harry Potter|author|' is not allowed! ");
        verify(view).write("If you want to see list of all commands use command help.");
    }

    @Test
    void testExecuteWithoutParam() {
        command.execute("insert|book");
        verify(view).write("Command 'insert|book' is not allowed! ");
        verify(view).write("If you want to see list of all commands use command help.");
    }
}
