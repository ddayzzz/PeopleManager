package database.adapter;

import com.sun.xml.internal.ws.api.pipe.FiberContextSwitchInterceptor;
import common.PersonType;
import container.IContainer;
import container.Person;
import database.daoImpl.StudentDaoImpl;
import database.daoImpl.WorkerDaoImpl;
import mvc.model.StandardModel;
import persons.Student;
import persons.Worker;

import java.util.ArrayList;

public class DatabaseModelAdapter extends StandardModel {
    protected PersonType personType;

    public DatabaseModelAdapter(PersonType personType) {
        super();
        //设置自定义的Container
        if (personType == PersonType.Student)
            container = new DatabaseContainerAdapter<Student>(new StudentDaoImpl());
        else
            container = new DatabaseContainerAdapter<Worker>(new WorkerDaoImpl());
        this.personType = personType;
    }

    @Override
    public void process_query() {
        if (viewer != null) {
            int searchType = viewer.askManyOptions("请选择查找的方式", "ID是精确查询；名字是模糊查询（存在多个结果）", optionsText);
            if (searchType == 0) {
                String id = viewer.getUserInput("输入", "请输入ID：");
                if (id != null && !id.equals("")) {
                    Person result = null;
                    if (personType == PersonType.Student) {
                        //精确查找
                        //使用特定的接口，直接查询id,注意这是精确查询
                        ArrayList<Student> st = getQueryResultFromDB("id", "^" + id + "$");
                        if (st!=null && st.size() >= 1)
                            result = st.get(0);
                    } else {
                        //Worker
                        ArrayList<Worker> st = getQueryResultFromDB("id", "^" + id + "$");
                        if (st!=null && st.size() >= 1)
                            result = st.get(0);
                    }
                    //显示结果
                    if (result != null) {
                        viewer.showPersonInfo(result);
                    } else {
                        viewer.showCriticalMessage("没有找到ID为“" + id + "”的对象");
                    }
                }
            } else if (searchType == 1) {
                //按照名称模糊查找
                String name = viewer.getUserInput("输入", "输入一个名字：");
                if (name != null && !name.equals("")) {
                    //设置模糊搜索
                    if (personType == PersonType.Student) {
                        //模糊查找
                        //使用特定的接口，查询name
                        ArrayList<Student> st = getQueryResultFromDB("name", name);
                        if (st==null || st.size() == 0) {
                            viewer.showCriticalMessage("没有结果");
                            return;
                        }
                        int choose = viewer.showQueryPersonsResultAndGetSelectResult("名字的查询结果", "以下是模糊查询的结果：", st);
                        if (choose >= 0) {
                            viewer.setSelectedPerson(st.get(choose));
                        }
                    } else {
                        //模糊查找
                        //使用特定的接口，查询name
                        ArrayList<Worker> st = getQueryResultFromDB("name", name);
                        if (st == null || st.size() == 0) {
                            viewer.showCriticalMessage("没有结果");
                            return;
                        }
                        int choose = viewer.showQueryPersonsResultAndGetSelectResult("名字的查询结果", "以下是模糊查询的结果：", st);
                        if (choose >= 0) {
                            viewer.setSelectedPerson(st.get(choose));
                        }
                    }
                }
            }
        } else
            throw new NullPointerException("没有指定的视图进行操作");
    }

    private <T extends Person> DatabaseContainerAdapter<T> convertToDBContainer(IContainer container) {
        return (DatabaseContainerAdapter<T>) container;
    }

    private <T extends Person> ArrayList<T> getQueryResultFromDB(String field, String regex) {
        DatabaseContainerAdapter<T> adapter = convertToDBContainer(container);
        ArrayList<T> st = adapter.query(field, regex);
        return st;
    }
}
