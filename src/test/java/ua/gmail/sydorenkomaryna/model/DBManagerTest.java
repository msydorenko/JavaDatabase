package ua.gmail.sydorenkomaryna.model;

import org.junit.jupiter.api.*;
import ua.gmail.sydorenkomaryna.view.Console;
import ua.gmail.sydorenkomaryna.view.View;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public abstract class DBManagerTest {
    private DBManager dbManager;
    abstract DBManager getDBManager();

    final String URL = "jdbc:postgresql://localhost:5432/sqlcmd";
    final String USER = "postgres";
    final String PASS = "postgres";

    @BeforeEach
    public void setup(){
        dbManager = getDBManager();
        View console = new Console();
        try {
            dbManager.makeConnection(URL, USER, PASS);
        }catch (Exception e){
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
    public void testIfConnectIsExist(){
        assertTrue(dbManager.isConnected());
    }


}
