package ua.gmail.sydorenkomaryna.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DBDataSetTest {
    private static DataSet dataSet;

/*    @BeforeEach
    protected static void setup(){
        dataSet = new DBDataSet();


    }*/

    /*@BeforeAll
    public static void setup(){
        DBDataSetTest dbDataSetTest = new DBDataSetTest();
        dbDataSetTest.dataSet = new DBDataSet();
    }*/

    @BeforeAll
    public static void init() {
        dataSet = new DBDataSet();
        dataSet.put("keyName1", "value1");
        dataSet.put("keyName2", "value2");
        dataSet.put("keyName3", "value3");
    }

    @AfterAll
    public static void tearDown() {
        dataSet = null;
    }

    @Test
    public void testUpdate() {
        DataSet newDataSet1 = new DBDataSet();
        newDataSet1.put("keyName3", "newValue");
        DataSet expectedData = new DBDataSet();
        expectedData.put("keyName1", "value1");
        expectedData.put("keyName2", "value2");
        expectedData.put("keyName3", "newValue");
        dataSet.update(newDataSet1);

        assertEquals(expectedData, dataSet);
    }

    @Test
    public void testGetNames() {
        Set<String> expected = new LinkedHashSet<>(3);
        expected.add("keyName1");
        expected.add("keyName2");
        expected.add("keyName3");

        assertEquals(expected, dataSet.getNames());
    }

    @Test
    public void testGetValues() {
        List<Object> expected = new ArrayList<>(2);
        expected.add("value1");
        expected.add("value2");
        expected.add("value3");

        assertEquals(expected, dataSet.getValues());
    }

    @Test
    public void testEquals() {
        DataSet newDataSet = new DBDataSet();
        newDataSet.put("keyName1", "value1");
        newDataSet.put("keyName2", "value2");
        newDataSet.put("keyName3", "newValue");

        assertEquals(newDataSet, dataSet);
    }

    @Test
    public void testEqualsWithNull() {
        DataSet newDataSet = null;

        assertNotEquals(newDataSet, dataSet);
    }

    @Test
    public void testHashCode() {
        int expecteed = "keyName1".hashCode() +
                "value1".hashCode() + "keyName2".hashCode() +
                "value2".hashCode() + "keyName3".hashCode() +
                "newValue".hashCode();

        assertEquals(expecteed, dataSet.hashCode());
    }

    @Test
    public void testToString() {
        assertEquals("[name1, name2, name3]", dataSet.toString());
    }
}