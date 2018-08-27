package ua.gmail.sydorenkomaryna.model;

import javax.swing.plaf.nimbus.State;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

/**
 * Manages data in database
 */
public class JDBCManager {
    private Connection connection;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/sqlcmd";
    private static final String USER_NAME = "postgres";
    private static final String PASS = "postgres";

    /**
     * Opens connection specified user name and password
     *
     * @param dbName
     * @param userName
     * @param password
     */
    public void makeConnection(String dbName, String userName, String password) {
        if (connection != null) {
            return;
        }
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL JDBC Driver is not in the library path!", e);
        }
        try {
            DriverManager.getConnection(dbName, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException(String.format("Connection failed to " +
                    "database: %s as user: %s , url: %s ", dbName, userName), e);
        }
    }

    /**
     * Creates table with specified names and columns. All columns are varchar(40)
     */
    public int createTables(String name, Set<String> columns) throws SQLException {
        StringBuilder query = new StringBuilder("CREATE TABLE " + name + " (");
        for (String col : columns) {
            query.append(col).append(" varchar(40),");
        }
        //remove last coma sign
        query.replace(query.length() - 1, query.length(), ")");
        try (Statement statement = connection.createStatement()) {
            statement.execute(query.toString());
        } catch (SQLException e) {
            throw e;
        }
        return 1;
    }

    /**
     * Close database connection if it is open
     */
    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * Inserts data into specified table
     *
     * @param tableName String table name
     * @param data      DataSet object with data to be inserted (pairs of column name and value)
     * @return int number of inserted rows
     * @throws SQLException
     */
    public int insertData(String tableName, DataSet data) throws SQLException {
        checkIfConnected();
        Set<String> columns = data.getNames();
        StringBuilder columnList = new StringBuilder(" (");
        StringBuilder valuesList = new StringBuilder(" (");
        for (String colName : columns) {
            columnList.append(colName).append(",");
            valuesList.append("'").append(data.get(colName)).append("',");
        }
        columnList.replace(columnList.length() - 1, columnList.length(), ")");
        valuesList.replace(valuesList.length() - 1, valuesList.length(), ")");
        String query = String.format("INSERT INTO public.%1$s %2$s VALUES %3$s",
                tableName, columnList, valuesList);
        int numRows;
        try (Statement statement = connection.createStatement()){
           numRows = statement.executeUpdate(query);
        } catch (SQLException e) {
            throw e;
        }
        return numRows;
    }

    public boolean isConnected() {
        return (connection != null);
    }

    private void checkIfConnected() {
        if (connection == null) {
            throw new RuntimeException("Connection to DB is not established. Please connect to you DB");
        }
    }
}
