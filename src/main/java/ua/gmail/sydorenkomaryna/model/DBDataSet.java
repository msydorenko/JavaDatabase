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
     * @param name is name of column
     * @param object is value of column
     */
    @Override
    public void put(String name, Object object) {
        data.put(name, object);
    }

    /**
     * Updates DataSet using new data
     *
     * @param newData to update current DBDataSet with
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
        return new ArrayList<>(data.values());
    }

    @Override
    public boolean equals(Object dataSet) {
        if (dataSet == null) return false;
        if (this == dataSet) return true;
        if (!(dataSet instanceof DBDataSet)) return false;

        Set<String> names = this.getNames();
        Set<String> dataSetNames = ((DBDataSet) dataSet).getNames();
        if (names.size() != dataSetNames.size()) return false;

        for (String name : names){
            try {
                if(!this.get(name).equals(((DBDataSet) dataSet).get(name)))
                    return false;
            }catch (Exception e){
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        Set<String> names = this.getNames();
        for (String name : names) {
            try {
                hashCode += name.hashCode() + this.get(name).hashCode();
            } catch (NullPointerException e) {
                hashCode += 0;
            }
        }
        return hashCode;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(data.keySet().toArray());
    }
}

