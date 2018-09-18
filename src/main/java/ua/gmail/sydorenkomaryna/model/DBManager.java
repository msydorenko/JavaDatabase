package ua.gmail.sydorenkomaryna.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface DBManager {

    void makeConnection(String dbName, String userName, String password);

    int createTables(String tableName, Set<String> columns) throws SQLException;

    void closeConnection() throws SQLException;

    int insertData(String tableName, DataSet data) throws SQLException;

    int deleteRow(String tableName, DataSet dataForDelete) throws SQLException;

    int dropTable(String tableName) throws SQLException;

    int updateRows(String tableName, DataSet condition, DataSet dataFrom) throws SQLException;

    int truncateTable(String tableName);

    List<DataSet> getTableData(String tableName) throws SQLException;

    Set<String> getNameColumns(String tableName) throws SQLException;

    String getNamesAllTablesInDataBase() throws SQLException;

    boolean isConnected();
}
