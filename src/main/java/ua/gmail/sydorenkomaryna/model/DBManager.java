package ua.gmail.sydorenkomaryna.model;

import java.sql.SQLException;
import java.util.Set;

public interface DBManager {
    void makeConnection(String dbName, String userName, String password);

    int createTables(String name, Set<String> columns) throws SQLException;

    void closeConnection() throws SQLException;

    int insertData(String tableName, DataSet data) throws SQLException;

    int dropTable(String tableName) throws SQLException;

    int updateRows(String tableName, DataSet condition, DataSet data) throws SQLException;

    boolean isConnected();
}
