package ua.gmail.sydorenkomaryna.model;

import java.util.List;
import java.util.Set;

public interface DataSet {
    void put(String name, Object object);

    Object get(String name);

    void update(DataSet newData);

    Set<String> getNames();

    List<Object> getValues();
}
