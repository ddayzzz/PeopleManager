import com.sun.org.apache.xpath.internal.operations.Mod;
import common.PersonType;
import container.Person;
import database.adapter.DatabaseModelAdapter;
import database.daoImpl.StudentDaoImpl;
import mvc.controler.CommandControler;
import mvc.controler.Controler;
import mvc.model.StandardModel;
import mvc.model.SerializableModel;
import mvc.model.TextModel;
import mvc.viewer.CommandViewer;
import mvc.viewer.Viewer;
import persons.Student;
import mvc.viewer.GUIViewer;
import mvc.controler.GUIControler;
import mvc.model.Model;


public class Test {

    public static void main(String[] args) {
//        StudentDaoImpl stu = new StudentDaoImpl();
//        try{
//            //stu.add(new Student("123456789", "wang shu sql", 63,36.3f));
//            System.out.println(stu.count());
//            //stu.delete("123456");
//            stu.update(new Student("123456789",null,2,2.3f), new Student("12", "wangshu", 89,33.3f));
//            Iterator<Student> it = stu.iterator();
//            while(it.hasNext())
//            {
//                System.out.println(it.next());
//            }
//            System.out.println(stu.find("1234567"));
//        }catch (Exception e)
//        {
//            System.out.println(e.getMessage());
//            e.printStackTrace();
//        }
        //设置何种数据模型
//        Model stuModel = new DatabaseModelAdapter(PersonType.Student);//new SerializableModel("stu.obj");
        Model stuModel = new SerializableModel("stu.obj");
        Model workModel =new DatabaseModelAdapter(PersonType.Worker); //new TextModel("worker.txt", PersonType.Worker);
        //那种视图？
//        Viewer stuViewer = new GUIViewer(PersonType.Student);
//        Viewer wViewer = new GUIViewer(PersonType.Worker);
        Viewer stuViewer = new CommandViewer(PersonType.Student);
        Viewer wViewer = new CommandViewer(PersonType.Worker);
//        模型与视图关联
        stuModel.setViewer(stuViewer);
        workModel.setViewer(wViewer);
        //何种控制器？
//        Controler controler = new GUIControler();
        Controler controler = new CommandControler();
        //控制器与模型关联
        controler.setModel(PersonType.Student, stuModel);
        controler.setModel(PersonType.Worker, workModel);
        //控制器显示
        controler.show();
        //阻塞，直到控制器退出。保存所有模型的更改
        controler.saveAllModel();
        //退出
        System.exit(0);
    }
}
