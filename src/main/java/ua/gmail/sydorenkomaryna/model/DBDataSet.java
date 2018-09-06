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
     * @param name   is name of column
     * @param object is value of column
     */
    @Override
    public void put(String name, Object object) {
        data.put(name, object);
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
     * @return List<Object>
     */
    @Override
    public List<Object> getValues() {
        return new ArrayList<>(data.values());
    }

    @Override
    public boolean equals(Object dataSet) {
        if (dataSet == null) return false;
        if (this == dataSet) return true;
        if (!(dataSet instanceof DBDataSet)) return false;

        Set<String> names = this.getNames();
        Set<String> anotherNames = ((DBDataSet) dataSet).getNames();
        if (names.size() != anotherNames.size()) return false;

        if (checkNames(names, anotherNames)) return false;

        if (checkValues((DBDataSet) dataSet, names)) return false;
        return true;
    }

    private boolean checkValues(DBDataSet dataSet, Set<String> names) {
        for (String name : names) {
            try {
                if (!this.get(name).equals(dataSet.get(name)))
                    return false;
            } catch (Exception e) {
                return true;
            }
        }
        return false;
    }

    private boolean checkNames(Set<String> names, Set<String> anotherNames) {
        for (String nameFirs : names) {
            for (String nameSecond : anotherNames) {
                try {
                    if (!nameFirs.equals(nameSecond))
                        return false;
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        Set<String> names = this.getNames();
        int hashCode = 0;
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

