package persons;
import container.Person;
public class Student extends Person {
	private float grade;
	public Student(String id, String name, int age, float grade)
	{
		super(id, name, age);
		this.grade = grade;
	}
	public float getGrade(){return grade;}
	public void setGrade(float grade){ this.grade = grade;}
	@Override
	public String toString(){
		return String.format("ID=%s, Name=%s, Age=%d, Grades=%f",
				this.getId(), this.getName(), this.getAge(), grade);
	}
	@Override
	public void copyFrom(Person p)
	{
		super.copyFrom(p);
		//特有的内容
		if(p instanceof Student)
		{
			grade = ((Student) p).grade;
		}
	}

	public static Student makeStudent(String text){
		String[] data = text.split(", ");
		try{
			if(data.length == 4)
			{
				//只有三个字段
				String id = data[0].substring(3);//ID=
				String name = data[1].substring(5);//Name=
				int age = Integer.valueOf(data[2].substring(4));//Age=
				float grades = Float.valueOf(data[3].substring(7));//Grades=
				return new Student(id, name, age, grades);
			}
		}catch (Exception e)
		{
			///System.err.println("无法从文本构造Student对象！");
			//e.printStackTrace();
		}
		return null;
	}
	
}
