package mvc.controler;

import common.PersonType;
import mvc.model.Model;
import mvc.model.StandardModel;
import mvc.viewer.CommandViewer;

import java.util.Scanner;

public class CommandControler extends Controler {
    private static final void showStudentMenu() {
        String s = "1.增加学生信息\n2.列出学生信息\n3.查询学生信息\n4.删除学生信息\n5.修改学生信息\n6.返回上一级菜单";
        System.out.println(s);
    }
    private static final void showWorkerMenu() {
        String s = "1.增加工作人员信息\n2.列出工作人员信息\n3.查询工作人员信息\n4.删除工作人员信息\n5.修改工作人员信息\n6.返回上一级菜单";
        System.out.println(s);
    }
    private static final void showMenus() {
        System.out.println("学校信息管理系统\n1.学生管理系统\n2.工人管理系统\n3.退出系统");
    }
    public CommandControler(){
        super();
    }
    private void stu(Scanner scanner) {
        Model stuModel = this.stringModelHashMap.getOrDefault(PersonType.Student, null);
        if (stuModel == null || stuModel.getViewer() == null || !(stuModel.getViewer() instanceof CommandViewer)) {
            System.err.println("没有指定数据模型和正确的命令行视图");
            return;
        }
        //显示

        showStudentMenu();
        int code = scanner.nextInt();
        while (code != 6) {
            switch (code) {
                case 1:
                    stuModel.process_insert();
                    break;
                case 2:
                    stuModel.process_list();
                    break;
                case 3:
                    stuModel.process_query();
                    break;
                case 4:
                    stuModel.process_delete();
                    break;
                case 5:
                    stuModel.process_modify();
                    break;
                case 6:
                    return;
            }
            showStudentMenu();
            code = scanner.nextInt();

        }
    }
    private void worker(Scanner scanner) {
        Model workModel = this.stringModelHashMap.getOrDefault(PersonType.Worker, null);
        if (workModel == null || workModel.getViewer() == null || !(workModel.getViewer() instanceof CommandViewer)) {
            System.err.println("没有指定数据模型和正确的命令行视图");
            return;
        }
        //显示
        showWorkerMenu();
        int code = scanner.nextInt();
        while (code != 6) {
            switch (code) {
                case 1:
                    workModel.process_insert();
                    break;
                case 2:
                    workModel.process_list();
                    break;
                case 3:
                    workModel.process_query();
                    break;
                case 4:
                    workModel.process_delete();
                    break;
                case 5:
                    workModel.process_modify();
                    break;
                case 6:
                    return;
            }
            showWorkerMenu();
            code = scanner.nextInt();
        }
    }
    private void doAction(Scanner scanner){
        showMenus();
        int code = scanner.nextInt();
        while (code != 3) {
            switch (code) {
                case 1:
                    stu(scanner);
                    break;
                case 2:
                    worker(scanner);
                    break;
            }
            showMenus();
            code = scanner.nextInt();
        }

        System.out.println("再见！");

    }

    @Override
    public void show() {
        doAction(new Scanner(System.in));
    }
}
