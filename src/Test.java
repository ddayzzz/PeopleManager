import common.PersonType;
import mvc.controler.CommandControler;
import mvc.controler.Controler;
import mvc.controler.GUIControler;
import mvc.model.Model;
import mvc.model.SerializableModel;
import mvc.model.TextModel;
import mvc.viewer.CommandViewer;
import mvc.viewer.GUIViewer;
import mvc.viewer.Viewer;

import javax.swing.text.View;


public class Test {
    public static void main(String[] args) {
        //设置何种数据模型
        Model stuModel = new SerializableModel("stu.obj");
        Model workModel = new TextModel("worker.txt", PersonType.Worker);
        //那种视图？
        Viewer stuViewer = new GUIViewer(PersonType.Student);
        Viewer wViewer = new GUIViewer(PersonType.Worker);
//        Viewer stuViewer = new CommandViewer(PersonType.Student);
//        Viewer wViewer = new CommandViewer(PersonType.Worker);
        //模型与视图关联
        stuModel.setViewer(stuViewer);
        workModel.setViewer(wViewer);
        //何种控制器？
        Controler controler = new GUIControler();
//        Controler controler = new CommandControler();
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
