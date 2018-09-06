package ua.gmail.sydorenkomaryna.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public interface DBManager {

    void makeConnection(String dbName, String userName, String password);

    int createTables(String name, Set<String> columns) throws SQLException;

    void closeConnection() throws SQLException;

    int insertData(String tableName, DataSet data) throws SQLException;

    int deleteRow(String nameTable, DataSet dataForDelete) throws SQLException;

    int dropTable(String tableName) throws SQLException;

    int updateRows(String tableName, DataSet condition, DataSet dataFrom) throws SQLException;

    int truncateTable(String nameTable);

    List<DataSet> getTableData(String nameTable) throws SQLException;

    Set<String> getNameColumns(String nameTable) throws SQLException;

    String getNamesAllTablesInDataBase() throws SQLException;

    boolean isConnected();
}
