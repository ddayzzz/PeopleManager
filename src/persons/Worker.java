package persons;
import container.Person;
public class Worker extends Person {
    private String job;
    private float salary;
    public Worker(String id, String name, int age, String job, float salary)
    {
        super(id, name, age);
        this.salary = salary;
        this.job = job;
    }
    public float getSalary(){return salary;}
    public void setSalary(float salary){ this.salary = salary;}
    public String getJob(){return job;}
    public void setJob(String job){ this.job = job;}
    @Override
    public String toString(){
        return String.format("ID=%s, Name=%s, Age=%d, Job=%s, Salary=%f",
                this.getId(), this.getName(), this.getAge(), job, salary);
    }
    @Override
    public void copyFrom(Person p)
    {
        super.copyFrom(p);
        //特有的内容
        if(p instanceof Worker)
        {
            Worker wok = (Worker)p;
            this.salary = wok.salary;
            this.job = wok.job;
        }
    }

    public static Worker makeWorker(String text){
        String[] data = text.split(", ");
        try{
            if(data.length == 5)
            {
                //只有三个字段
                String id = data[0].substring(3);//ID=
                String name = data[1].substring(5);//Name=
                int age = Integer.valueOf(data[2].substring(4));//Age=
                String job = data[3].substring(4);//Job=
                float salary = Float.valueOf(data[4].substring(7));//Salary=
                return new Worker(id, name, age, job,salary);
            }
        }catch (Exception e)
        {
            //System.err.println("无法从文本构造Worker对象！");
            //e.printStackTrace();
        }
        return null;
    }

}
