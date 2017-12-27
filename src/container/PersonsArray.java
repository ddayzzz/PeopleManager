package container;
import java.io.Serializable;
import java.util.function.BiFunction;
import java.util.*;
public class PersonsArray implements IContainer, Serializable, Iterable {
    private Person[] persons = new Person[10];
    private int next=0;
    private boolean isFull() {
        return next >= 10;
    }
    public void insert(Person p) throws PersonExistsException{
        assert(p != null);
        assert(!isFull());
        ArrayList<Person> tar = query(p, (Person l ,Person r)->l.getId().equals(r.getId()));
        if(tar.size() == 0)
        {
            persons[next++] = p;//不存在
        }
        else
            throw new PersonExistsException("插入失败，已经存在的目标：" + p.toString());
    }
    public void list(){
        for(int i=0;i<next;++i)
            System.out.println(persons[i]);
    }
    public ArrayList<Person> query(Person p, BiFunction<Person, Person, Boolean> pred){
        ArrayList<Person> results = new ArrayList<>();
        for(int i=0;i<next;++i)
        {
            if(pred.apply(persons[i], p))
                results.add(persons[i]);
        }
        return results;
    }
    public void delete(Person p) throws PersonNotFoundException{
        assert(p != null);
        //找到目标
        for(int i=0;i<next;++i)
        {
            if(persons[i].equals(p)) {
               persons[i] = persons[next - 1];
               persons[next -1 ] = null;
               --next;
               return;
            }
        }
        throw new PersonNotFoundException("删除失败，没有找到目标：" + p.toString());

    }
    public void modify(Person p, Person newInfo) throws PersonNotFoundException{
        assert(p != null);
        assert(newInfo != null);
        ArrayList<Person> target = query(p, (Person l ,Person r)->l.getId().equals(r.getId()));
        if(target.size() > 0)//必须存在
        {
            for(Person pp : target)
                pp.copyFrom(newInfo);
        }
        else{
            throw new PersonNotFoundException("修改失败，没有找到目标：" + p.toString());
        }
    }
    public int count(){
        return next;
    }

    public class ArrayIterator implements Iterator{
        private int ret = 0;
        private Person[] persons;
        private int length;
        public ArrayIterator(Person[] persons, int length)
        {
            this.persons = persons;
            this.length = length;
        }
        public boolean hasNext(){
            if(ret < length)
                return true;
            else
                return false;
        }
        public Person next(){
            return this.persons[ret++];
        }
    }
    public  ArrayIterator iterator(){
        return new ArrayIterator(persons, count());
    }
}
