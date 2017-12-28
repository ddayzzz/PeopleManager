package database.dao;

import container.Person;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PersonDao<T extends Person> extends Iterable<T>{
    void add(T t) throws SQLException, SQLPrimaryKeyInTheSameException;
    void update(T old, T newT) throws SQLException, SQLPrimaryKeyNotFoundException;
    void delete(String id) throws SQLException, SQLPrimaryKeyNotFoundException;
    T find(String id) throws SQLException;
    ArrayList<T> find(String field, String regex) throws SQLException;
    int count() throws SQLException;
}
