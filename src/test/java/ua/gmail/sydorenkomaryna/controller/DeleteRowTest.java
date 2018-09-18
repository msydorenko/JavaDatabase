package ua.gmail.sydorenkomaryna.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.gmail.sydorenkomaryna.controller.command.Command;
import ua.gmail.sydorenkomaryna.controller.command.DeleteRow;
import ua.gmail.sydorenkomaryna.model.DBDataSet;
import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.model.DataSet;
import ua.gmail.sydorenkomaryna.view.View;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class DeleteRowTest {
    private static DBManager dbManager;
    private static View view;
    private static Command command;

    @BeforeEach
    void setup() {
        dbManager = mock(DBManager.class);
        view = mock(View.class);
        command = new DeleteRow(view, dbManager);
    }

    @Test
    void testIsExecutable() {
        assertTrue(command.isExecutable("delete|book|id|1"));
    }

    @Test
    void testIsNotExecutable() {
        assertFalse(command.isExecutable("dddelete|book|id|1"));
    }

    @Test
    void testExecuteWithCorrectParam() throws SQLException {
        command.execute("delete | book|title |Master");
        DataSet data = new DBDataSet();
        data.put("title", "Master");
        verify(dbManager).deleteRow("book", data);
        verify(view).write("0 rows were successfully  deleted from table 'book'");
    }

    @Test
    void testExecuteWithWrongLength() {
        command.execute("delete|book");
        verify(view).write("Command 'delete|book' is not allowed! ");
    }

    @Test
    void testExecuteWithoutTableName() {
        command.execute("delete| |book");
        verify(view).write("Command 'delete| |book' is not allowed! ");
    }

}
