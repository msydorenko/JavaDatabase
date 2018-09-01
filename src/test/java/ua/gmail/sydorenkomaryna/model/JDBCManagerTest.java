package ua.gmail.sydorenkomaryna.model;

public class JDBCManagerTest extends DBManagerTest {
    @Override
    DBManager getDBManager(){
        return new JDBCManager();
    }
}
