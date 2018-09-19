package ua.gmail.sydorenkomaryna.controller.command;

import ua.gmail.sydorenkomaryna.model.DataSet;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Create table view for console output
 */

public class TableCreator {
    private static final String SEPARATOR = "|";
    private static final String HSEPARATOR = "+";
    private static final String HBORDER = "-";
    private static final int SIZE_COLUMN = 20;

    /**
     * Create table for output data from database
     *
     * @param data    which needs to be presented in table
     * @param columns is names for table header
     * @return String which needs to be presented in table
     */
    public static String createView(List<DataSet> data, Set<String> columns) {
        StringBuilder result = new StringBuilder();
        result.append(System.lineSeparator());
        result.append(borderRow(columns.size()));
        result.append(header(columns));
        result.append(borderRow(columns.size()));
        for (DataSet row : data) {
            result.append(SEPARATOR);
            List<Object> values = row.getValues();
            for (Object value : values) {
                result.append(String.format("%-" + SIZE_COLUMN + "s", (value == null) ? "" : value.toString()));
                result.append(SEPARATOR);
            }
            result.append(System.lineSeparator());
        }
        result.append(borderRow(columns.size()));
        return result.toString();
    }

    private static String borderRow(int columnCount) {
        StringBuilder result = new StringBuilder(HSEPARATOR);
        for (int i = 0; i < columnCount; i++) {
            result.append(String.join("", Collections.nCopies(SIZE_COLUMN, HBORDER)));
            result.append(HSEPARATOR);
        }
        result.append(System.lineSeparator());
        return result.toString();
    }

    private static String header(Set<String> columns) {
        StringBuilder result = new StringBuilder(SEPARATOR);
        for (String column : columns) {
            result.append(String.format("%-" + SIZE_COLUMN + "s", column)).append(SEPARATOR);
        }
        result.append(System.lineSeparator());
        return result.toString();
    }
}
