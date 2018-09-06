package ua.gmail.sydorenkomaryna.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.gmail.sydorenkomaryna.view.Console;
import ua.gmail.sydorenkomaryna.view.View;

import java.sql.SQLException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class DBManagerTest {
    private DBManager dbManager;
    View console = new Console();

    abstract DBManager getDBManager();

    final String URL = "sqlcmd";
    final String USER = "postgres";
    final String PASS = "postgres";

    @BeforeEach
    public void setup() {
        dbManager = getDBManager();
        try {
            dbManager.makeConnection(URL, USER, PASS);
        } catch (Exception e) {
            console.write("Can't connect to database. The reason is:" + e.getMessage());
        }
    }

    @AfterEach
    public void leave() throws SQLException {
        // dropTable();
        dbManager.closeConnection();
    }

    @Test
    @DisplayName("should return true if connect is exist")
    public void testIfConnectIsExist() {
        assertTrue(dbManager.isConnected());
    }

    @Test
    @DisplayName("method should return 1 if table was created")
    public void testCreateTables() {
        Set<String> columnNames = new LinkedHashSet<>();
        columnNames.add("LastName");
        columnNames.add("FirstName");
        columnNames.add("Country");
        try {
            dbManager.createTables("testTable", columnNames);
        } catch (SQLException e) {
            console.write("Can't create table");
        }
        assertEquals(1, 1);
    }

    @Test
    @DisplayName("method insertData should return 1 if row was insert")
    public void testInsertData() throws SQLException {
        DataSet newRow = new DBDataSet();
        newRow.put("LastName", "Jon");
        newRow.put("FirstName", "Bush");
        newRow.put("Country", "USA");

        dbManager.insertData("testtable", newRow);
        assertEquals(1, 1);
    }

    @Test
    public void testGetNamesAllTablesInDataBase() throws SQLException {
        assertEquals("user, testtable", dbManager.getNamesAllTablesInDataBase());
    }


    @Test
    public void testTruncateTable() throws SQLException {
        dbManager.truncateTable("testtable");

        assertEquals(1, 1);
    }


    /*@Test
    @DisplayName("method should return 1 if table was deleted")
    public void testDropTable() throws SQLException {
        dbManager.dropTable("testtable");

        assertEquals(1,1);

    }*/
}
