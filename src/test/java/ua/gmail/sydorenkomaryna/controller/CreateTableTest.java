package ua.gmail.sydorenkomaryna.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.gmail.sydorenkomaryna.controller.command.Command;
import ua.gmail.sydorenkomaryna.controller.command.CreateTable;
import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.view.View;

import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CreateTableTest {
    private static DBManager dbManager;
    private static View view;
    private static Command command;

    @BeforeEach
    void setup() {
        dbManager = mock(DBManager.class);
        view = mock(View.class);
        command = new CreateTable(view, dbManager);
    }

    @Test
    void testIsExecutable() {
        assertTrue(command.isExecutable("create|book"));
    }

    @Test
    void testIsNotExecutable() {
        assertFalse(command.isExecutable("cccreate|book"));
    }

    @Test
    void testExecuteWithCorrectParamWithSpace() throws SQLException {
        Set<String> columnsName = new LinkedHashSet<>();
        columnsName.add("title");
        columnsName.add("author");

        command.execute("create| book|title |author");

        verify(dbManager).createTables("book", columnsName);
        verify(view).write("Congratulates! Table 'book' was successfully created");
    }

    @Test
    void testExecuteWithoutNameColumn() {
        command.execute("create|book|");
        verify(view).write("Command 'create|book|' is not allowed! ");
    }

    @Test
    void testExecuteWithoutTableName() {
        command.execute("create|   |title|author");
        verify(view).write("Command 'create|   |title|author' is not allowed! ");
    }
}
