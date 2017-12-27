package container;
import java.io.*;
public class Person implements Serializable{
	private String id;
	private String name;
	private int age;
	public Person(String id,
			String name,
			int age)
	{
		this.setId(id);
		this.setName(name);
		this.setAge(age);
	}
	public  void copyFrom(Person person)
	{
		this.id = person.id;
		this.name = person.name;
		this.age = person.age;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public boolean equals(Object p)
	{
		if(p == null)
			return false;
		if(p instanceof Person)
		{
			Person pp = (Person)p;
			return id.equals(pp.id);
		}
		return false;
	}
	@Override
	public int hashCode()
	{
		return id.hashCode();
	}
	@Override
	public String toString(){
		return String.format("ID=%s, Name=%s, Age=%d",
				this.id, this.name, this.age);
	}
	//工厂函数，从文件变成一个Person对象
	public static Person makePerson(String text){
		String[] data = text.split(", ");
		try{
			if(data.length == 3)
			{
				//只有三个字段
				String id = data[0].substring(3);//ID=
				String name = data[1].substring(5);//Name=
				int age = Integer.valueOf(data[2].substring(4));//Age=
				return new Person(id, name, age);
			}
		}catch (Exception e)
		{
			//System.err.println("无法从文本构造Person对象！");
			//e.printStackTrace();
		}
		return null;
	}
}
