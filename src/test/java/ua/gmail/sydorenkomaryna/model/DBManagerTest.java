package ua.gmail.sydorenkomaryna.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.gmail.sydorenkomaryna.view.Console;
import ua.gmail.sydorenkomaryna.view.View;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class DBManagerTest {
    private DBManager dbManager;
    private View view = new Console();
    String DB_NAME = "sqlcmd";
    String USER_NAME = "postgres";
    String PASSWORD = "postgres";

    abstract DBManager getDBManager();

    @BeforeEach
    void setup() {
        dbManager = getDBManager();
        dbManager.makeConnection(DB_NAME, USER_NAME, PASSWORD);
        createTable();
    }

    private void createTable() {
        Set<String> columnName = new LinkedHashSet<>(3);
        columnName.add("FirstName");
        columnName.add("LastName");
        columnName.add("Country");

        try {
            dbManager.createTables("test", columnName);
        } catch (SQLException e) {
            view.write("Can't create table!");
        }
    }

    @AfterEach
    void tearDown() {
        dropTable();
        try {
            dbManager.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dropTable() {
        try {
            dbManager.dropTable("test");
        } catch (SQLException e) {
            view.write("Table wasn't delete!");
        }
    }

    @Test
    void testIsConnected() {
        assertTrue(dbManager.isConnected());
    }

    @Test
    void testInsertData() throws SQLException {
        DataSet row = new DBDataSet();
        row.put("firstname", "value1");
        row.put("lastname", "value2");
        row.put("country", "value3");
        dbManager.insertData("test", row);
        List<DataSet> result = dbManager.getTableData("test");
        DataSet expectedRowFromTable = new DBDataSet();
        expectedRowFromTable.put("id", "1");
        expectedRowFromTable.put("firstname", "value1");
        expectedRowFromTable.put("lastname", "value2");
        expectedRowFromTable.put("country", "value3");

        assertEquals(expectedRowFromTable.getValues().toString(), result.get(0).getValues().toString());
    }

    @Test
    void testUpdateRows() throws SQLException {
        DataSet row = new DBDataSet();
        row.put("firstname", "value1");
        row.put("lastname", "value2");
        row.put("country", "value3");
        dbManager.insertData("test", row);
        DataSet oldCondition = new DBDataSet();
        oldCondition.put("id", "1");
        DataSet newCondition = new DBDataSet();
        newCondition.put("country", "USA");
        dbManager.updateRows("test", oldCondition, newCondition);
        List<DataSet> result = dbManager.getTableData("test");

        DataSet expectedData = new DBDataSet();
        expectedData.put("id", "1");
        expectedData.put("firstname", "value1");
        expectedData.put("lastname", "value2");
        expectedData.put("country", "USA");

        assertEquals(expectedData.getValues().toString(), result.get(0).getValues().toString());
    }

    @Test
    void testGetNamesAllTablesInDataBase() throws SQLException {
        String result = dbManager.getNamesAllTablesInDataBase();
        assertTrue(result.contains("test"));
    }
}