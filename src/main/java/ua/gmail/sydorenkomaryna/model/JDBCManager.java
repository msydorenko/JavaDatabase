package ua.gmail.sydorenkomaryna.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Manages data in database
 */
public class JDBCManager implements DBManager {
    private Connection connection;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/";

    /**
     * Opens connection specified user name and password
     *
     * @param dbName
     * @param userName
     * @param password
     */
    @Override
    public void makeConnection(String dbName, String userName, String password) {
        if (connection != null) {
            return;
        }
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("JDBC Driver PostgreSQL is not in the library path!", e);
        }
        try {
            connection = DriverManager.getConnection(DB_URL + dbName, userName, password);
        } catch (Exception e) {
            throw new RuntimeException(String.format("Connection failed to " +
                    "database: %s as user: %s.", dbName, userName), e);
        }
    }

    /**
     * Creates table with specified names and columns.
     * First column is autoincrement primary key, other columns are varchar(40)
     */
    @Override
    public int createTables(String tableName, Set<String> columns) throws SQLException {
        checkIfConnected();
        StringBuilder query = new StringBuilder("CREATE TABLE " + tableName);
        query.append(" (id SERIAL CONSTRAINT " + tableName + "PrimaryKey PRIMARY KEY, ");
        for (String col : columns) {
            query.append(col).append(" varchar(40),");
        }
        //remove last comma sign
        query.replace(query.length() - 1, query.length(), ");");
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
    @Override
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
    @Override
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
        try (Statement statement = connection.createStatement()) {
            numRows = statement.executeUpdate(query);
        } catch (SQLException e) {
            throw e;
        }
        return numRows;
    }

    /**
     * Delete row in table
     *
     * @param tableName
     * @param dataForDelete as pair of column name and value for part of DELETE statement WHERE column=value
     * @return number of deleted rows
     */
    @Override
    public int deleteRow(String tableName, DataSet dataForDelete) throws SQLException {
        checkIfConnected();
        StringBuilder query = new StringBuilder();
        query.append("DELETTE FROM pulic." + tableName +
                " WHERE ");

        Set<String> nameColumns = dataForDelete.getNames();
        for (String name : nameColumns) {
            query.append(String.format("%1$s = %2$s", name, dataForDelete.get(name)));
        }
        int numRow = -1;
        try (Statement statement = connection.createStatement()) {
            numRow = statement.executeUpdate(query.toString());
        } catch (SQLException e) {
            throw e;
        }
        return numRow;
    }

    /**
     * Delete table in database
     *
     * @param tableName
     * @return 1 if table was deleted
     * @throws SQLException
     */
    @Override
    public int dropTable(String tableName) throws SQLException {
        checkIfConnected();
        String query = "DROP TABLE public." + tableName;
        try (Statement statement = connection.createStatement()) {
            if (statement.execute(query)) {
                return 1;
            }
        } catch (SQLException e) {
            throw e;
        }
        return -1;
    }

    /**
     * Updates specified table according to condition
     *
     * @param tableName
     * @param oldCondition as pair of column name and value for part of UPDATE statement "WHERE column=value"
     * @param newCondition for update as pairs of column name and value for part of UPDATE statement "SET column=value"
     * @return int number of updated rows
     * @throws SQLException
     */
    @Override
    public int updateRows(String tableName, DataSet oldCondition, DataSet newCondition) throws SQLException {
        checkIfConnected();
        StringBuilder query = new StringBuilder(String.format("UPDATE public.%s SET ", tableName));
        Set<String> columns = newCondition.getNames();
        for (String columnName : columns) {
            query.append(String.format("%1$s='%2$s',", columnName, newCondition.get(columnName)));
        }
        query.replace(query.length() - 1, query.length(), " WHERE ");
        columns = oldCondition.getNames();
        for (String columnName : columns) {
            query.append(String.format("%1$s = '%2$s'", columnName, oldCondition.get(columnName)));
        }
        int numRows = -1;
        try (Statement statement = connection.createStatement()) {
            numRows = statement.executeUpdate(query.toString());
        } catch (SQLException e) {
            throw e;
        }
        return numRows;
    }

    /**
     * Delete all rows in table
     *
     * @param tableName
     * @return int number of updated rows
     * @throws SQLException
     */
    @Override
    public int truncateTable(String tableName) {
        checkIfConnected();
        String query = "TRUNCATE TABLE public." + tableName;
        int numRows = -1;
        try (Statement st = connection.createStatement()) {
            numRows = st.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException("TRUNCATE caused an SQLException", e);
        }
        return numRows;
    }

    /**
     * Get data from specified table
     *
     * @param tableName
     * @return rows with data
     * @throws SQLException
     */
    @Override
    public List<DataSet> getTableData(String tableName) throws SQLException {
        checkIfConnected();
        List<DataSet> data = new ArrayList<>();
        String query = "SELECT * FROM public." + tableName;
        try (Statement statement = connection.createStatement();
             ResultSet resultset = statement.executeQuery(query)) {
            ResultSetMetaData metaData = resultset.getMetaData();
            int countColumn = metaData.getColumnCount();

            while (resultset.next()) {
                DataSet row = new DBDataSet();
                for (int index = 1; index <= countColumn; index++) {
                    row.put(metaData.getColumnName(index), resultset.getObject(index));
                }
                data.add(row);
            }
        } catch (SQLException e) {
            throw e;
        }
        return data;
    }

    /**
     * Get list of titles columns
     *
     * @param tableName
     * @return list
     * @throws SQLException
     */
    @Override
    public Set<String> getNameColumns(String tableName) throws SQLException {
        checkIfConnected();
        Set<String> nameColumn = new LinkedHashSet<>();
        String query = "SELECT * FROM public." + tableName;
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            ResultSetMetaData metadata = resultSet.getMetaData();
            int countColumn = metadata.getColumnCount();
            for (int index = 1; index <= countColumn; index++) {
                nameColumn.add(metadata.getColumnName(index));
            }
        } catch (SQLException e) {
            throw e;
        }
        return nameColumn;
    }

    /**
     * Get list of table names
     *
     * @return list of existing tables separate comma sign
     * @throws SQLException
     */
    @Override
    public String getNamesAllTablesInDataBase() throws SQLException {
        checkIfConnected();
        StringBuilder result = new StringBuilder();
        String query = "SELECT table_name FROM information_schema.tables WHERE table_schema='public' " +
                "AND table_type='BASE TABLE'";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                result.append(resultSet.getString("table_name")).append(", ");
            }
        } catch (SQLException e) {
            throw e;
        }
        return result.substring(0, result.length() - 2);
    }

    @Override
    public boolean isConnected() {
        return (connection != null);
    }

    private void checkIfConnected() {
        if (connection == null) {
            throw new RuntimeException("Connection to DB is not established. Please connect to your DB");
        }
    }
}
