package ua.gmail.sydorenkomaryna.model;

import java.util.*;

/**
 * Implementation of DataSet interface using Map for data from database
 */
public class DBDataSet implements DataSet {
    private final Map<String, Object> data = new LinkedHashMap<>();

    /**
     * Adds assosiation of given name and value to the DataSet
     *
     * @param name
     * @param object
     */
    @Override
    public void put(String name, Object object) {
        data.put(name, object);
    }

    /**
     * Updates DataSet using new data
     *
     * @param DataSet new data
     */
    @Override
    public void update(DataSet newData) {
        Set<String> columns = newData.getNames();
        for (String column : columns) {
            data.put(column, newData.get(column));
        }
    }

    /**
     * Returns value which specied name is mapped
     *
     * @param name is name key from DataSet
     * @return Object from data Map
     */
    @Override
    public Object get(String name) {
        return data.get(name);
    }

    /**
     * Returns Set of String names keys from DataSet
     *
     * @return Set of String names keys
     */
    @Override
    public Set<String> getNames() {
        return data.keySet();
    }

    /**
     * Returns List of values from DataSet
     *
     * @param name is value of key
     * @return List<Object>
     */
    @Override
    public List<Object> getValues(String name) {
        return new ArrayList<Object>(data.values());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DBDataSet)) return false;
        DBDataSet dbDataSet = (DBDataSet) o;
        return Objects.equals(data, dbDataSet.data);
    }

    @Override
    public int hashCode() {

        return Objects.hash(data);
    }

    @Override
    public String toString() {
        return Arrays.deepToString(data.keySet().toArray());
    }
}

