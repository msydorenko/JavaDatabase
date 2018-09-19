package ua.gmail.sydorenkomaryna.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ua.gmail.sydorenkomaryna.controller.command.Command;
import ua.gmail.sydorenkomaryna.controller.command.ViewData;
import ua.gmail.sydorenkomaryna.model.DBDataSet;
import ua.gmail.sydorenkomaryna.model.DBManager;
import ua.gmail.sydorenkomaryna.model.DataSet;
import ua.gmail.sydorenkomaryna.view.View;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ViewDataTest {
    private static DBManager dbManager;
    private static View view;
    private static Command command;

    @BeforeEach
    void setup(){
        dbManager = mock(DBManager.class);
        view  = mock(View.class);
        command = new ViewData(view, dbManager);
    }

    @Test
    void testExecuteWithCorrectData(){
        try {
            Set<String> columnsName = new LinkedHashSet<>();
            columnsName.add("id");
            columnsName.add("name");
            columnsName.add("password");
            when(dbManager.getNameColumns("test"))
                    .thenReturn(columnsName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DataSet row1 = new DBDataSet();
        row1.put("id", "1");
        row1.put("name", "Jon");
        row1.put("password", "Sims");
        DataSet row2 = new DBDataSet();
        row2.put("id", "2");
        row2.put("name", "Eva");
        row2.put("password", "Sims");
        List<DataSet> rows = new ArrayList<>();
        rows.add(row1);
        rows.add(row2);

        try {
            when(dbManager.getTableData("test"))
                    .thenReturn(rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        command.execute("find|test");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        assertEquals("[Table 'test':, \r\n" +
                "+--------------------+--------------------+--------------------+\r\n" +
                "|id                  |name                |password            |\r\n" +
                "+--------------------+--------------------+--------------------+\r\n" +
                "|1                   |Jon                 |Sims                |\r\n" +
                "|2                   |Eva                 |Sims                |\r\n" +
                "+--------------------+--------------------+--------------------+\r\n" +
                ", The End!]", captor.getAllValues().toString());
    }


}
