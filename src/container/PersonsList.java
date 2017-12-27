package container;
import java.util.*;
import java.io.*;
import java.util.function.BiFunction;

public class PersonsList implements IContainer, Serializable, Iterable {
	private ArrayList<Person> persons;//类型安全的版本
	public PersonsList(){
		persons = new ArrayList<Person>();
	}
	public void insert(Person p) throws PersonExistsException {
		assert (p != null);
		ArrayList<Person> tar = query(p, (Person l ,Person r)->l.getId().equals(r.getId()));
		if (tar.size() == 0) {
			persons.add(p);//不存在
		} else {
			throw new PersonExistsException("插入失败，已经存在的目标：" + p.toString());
		}

	}
	public void list(){
		for(Person p : persons)
		{
			System.out.println(p.toString());
		}
	}
	public ArrayList<Person> query(Person p, BiFunction<Person, Person, Boolean> pred){
		assert(p != null);
		ArrayList<Person> results = new ArrayList<>();
		for(Person pp : persons)
		{
			if(pred.apply(p, pp))
				results.add(pp);
		}
		return results;
	}
	public void delete(Person p) throws PersonNotFoundException{
		assert(p != null);
		ArrayList<Person> target = query(p, (Person l ,Person r)->l.getId().equals(r.getId()));
		if(target.size() > 0)
		{
			//必须存在 由于只有一个ID，所以删除p即可
			for(Person pp : target)
				persons.remove(pp);
		}
		else{
			throw new PersonNotFoundException("删除失败，没有找到目标：" + p.toString());
		}
	}
	public int count(){
		return persons.size();
	}

	public void modify(Person p, Person newInfo)throws PersonNotFoundException{
		assert(p != null);
		assert(newInfo != null);
		ArrayList<Person> target = query(p, (Person l ,Person r)->l.getId().equals(r.getId()));
		if(target.size() > 0)//原对象
		{
			p.copyFrom(newInfo);
		}
		else{
			throw new PersonNotFoundException("修改失败，没有找到目标：" + p.toString());
		}
	}
	public Iterator iterator(){
		return ((Iterable)persons).iterator();
	}
	
}
