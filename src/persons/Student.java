package persons;
import container.Person;
public class Student extends Person {
	private float grades;
	public Student(String id, String name, int age, float grades)
	{
		super(id, name, age);
		this.grades = grades;
	}
	public float getGrades(){return grades;}
	public void setGrades(float grades){ this.grades = grades;}
	@Override
	public String toString(){
		return String.format("ID=%s, 姓名=%s, 年龄=%d, 成绩=%.1f",
				this.getId(), this.getName(), this.getAge(), grades);
	}
	@Override
	public void copyFrom(Person p)
	{
		super.copyFrom(p);
		//特有的内容
		if(p instanceof Student)
		{
			grades = ((Student) p).grades;
		}
	}

	public static Student makeStudent(String text){
		String[] data = text.split(", ");
		try{
			if(data.length == 4)
			{
				//只有三个字段
				String id = data[0].substring(3);//ID=
				String name = data[1].substring(3);//Name=
				int age = Integer.valueOf(data[2].substring(3));//Age=
				float grades = Float.valueOf(data[3].substring(3));//Grades=
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
