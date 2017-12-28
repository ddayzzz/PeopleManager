package database.adapter;

import container.IContainer;
import container.Person;
import container.PersonExistsException;
import container.PersonNotFoundException;
import database.dao.PersonDao;
import database.dao.SQLPrimaryKeyInTheSameException;
import database.dao.SQLPrimaryKeyNotFoundException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiFunction;

public class DatabaseContainerAdapter<T extends Person> implements IContainer{

    private PersonDao<T> personDao;

    public DatabaseContainerAdapter(PersonDao<T> dao){
        personDao = dao;
    }
    @Override
    public int count() {
        int cnt = 0;
        try{
            cnt =  personDao.count();
        }catch (SQLException e){

        }
        return cnt;
    }

    @Override
    public void delete(Person p) throws PersonNotFoundException{
        if(p !=null)
        {
            try{
                personDao.delete(p.getId());
            }catch (SQLException e){

            }
            catch (SQLPrimaryKeyNotFoundException e){
                throw new PersonNotFoundException(e.getMessage());
            }
        }
    }

    @Override
    public void insert(Person p) throws PersonExistsException{
        if(p !=null){
            try{
                personDao.add((T)p);
            }catch (SQLException e){

            }
            catch (SQLPrimaryKeyInTheSameException e){
                throw new PersonExistsException(e.getMessage());
            }
        }
    }

    //为了兼容的传递函数的版本
    @Override
    public ArrayList<Person> query(Person p, BiFunction<Person, Person, Boolean> pred) {
        ArrayList<Person> result =new ArrayList<Person>();
        for(Person pp : personDao){
            if(pred.apply(p, pp)){
                result.add(pp);
            }
        }
        return result;
    }

    @Override
    public Iterator<Person> iterator() {
        return (Iterator<Person>) personDao.iterator();
    }

    @Override
    public void modify(Person p, Person n)throws PersonNotFoundException {
        if(p !=null && n !=null){
            try{
                personDao.update((T)p,(T)n);
            }catch (SQLException e){

            }
            catch (SQLPrimaryKeyNotFoundException e){
                throw new PersonNotFoundException(e.getMessage());
            }
        }
    }
    //直接是用正则表达式的版本查询某个字段
    public ArrayList<T> query(String field, String regex){
        ArrayList<T> result =null;
        try{
            result= personDao.find(field, regex);
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
        return result;
    }
}
