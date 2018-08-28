package ua.gmail.sydorenkomaryna.model;

import java.util.List;
import java.util.Set;

public interface DataSet {
    void put(String name, Object object);

    void update(DataSet newData);

    Object get(String name);

    Set<String> getNames();

    List<Object> getValues(String name);
}
