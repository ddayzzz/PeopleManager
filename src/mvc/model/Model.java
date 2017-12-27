package mvc.model;
import java.io.*;
import java.util.*;
import java.util.function.Function;

import common.ExceptionUtility;
import container.*;
import mvc.viewer.Viewer;


public class Model {
    protected static final String[] optionsText = new String[]{"按照ID","按照名字模糊查询"};
    protected IContainer container;//保存数据的接口
    protected Viewer viewer;//视图
    public Model() {
        container = new PersonsArray();
    }

    public  void process_insert(){
        if(viewer !=null)
        {
            try {
                Person person = viewer.getNewPersonFromUserInput(true, null);
                if (person != null) {
                    container.insert(person);
                    viewer.showInfoMessage("插入成功");
                    if(viewer.askToRefreshList())
                        process_list();

                }
            }catch (PersonExistsException e) {
                viewer.showCriticalMessage(e.getMessage() );
            }
            catch (Exception e) {
                viewer.showCriticalMessage(e.getMessage() + "，调用堆栈：\n" + ExceptionUtility.getStackTrace(e));
            }
        }
        else
        {
            throw new NullPointerException("没有指定的视图进行操作");
        }
    }

    public void process_list(){
        if(viewer !=null)
        {
            viewer.changeTitleText("当前的对象：（" + container.count() + "）");
            viewer.showAllPersons(container);
        }
        else
        {
            throw new NullPointerException("没有指定的视图进行操作");
        }

    }

    public  void process_query() {

        if(viewer !=null)
        {

            int searchType = viewer.askManyOptions("请选择查找的方式", "ID是精确查询；名字是模糊查询（存在多个结果）", optionsText);
            if(searchType == 0) {
                String id = viewer.getUserInput("输入", "请输入ID：");
                if (id != null && !id.equals("")) {
                    ArrayList<Person> tar = container.query(new Person(id, null, 0), (Person l, Person r) -> l.getId().equals(r.getId()));
                    if (tar.size() > 0) {
                        for (Person p: tar)
                        {
                            viewer.showPersonInfo(p);
                            viewer.setSelectedPerson(p);//只会循环一次
                        }
                    } else {
                        viewer.showCriticalMessage("没有找ID为“" + id + "”的对象");
                    }
                }
            }
            else if(searchType == 1)
            {
                //按照名称模糊查找
                String name = viewer.getUserInput("输入", "输入一个名字：");
                if (name != null && !name.equals("")) {
                    //设置模糊排序，而不是精确排序
                    ArrayList<Person> tar = container.query(new Person(null, name, 0), this::query_byName);
                    if (tar.size() > 0) {
                        String[] res = new String[tar.size()];
                        for(int i=0;i<tar.size();++i)
                            res[i] = tar.get(i).toString();
                        String selected = viewer.showQueryPersonsResultAndGetSelectResult("所有匹配结果", "满足的模糊查找的名字", res);
                        if(selected !=null)
                        {
                            for(Person p:tar)
                            {
                                if(p.toString().equals(selected))
                                {
                                    viewer.setSelectedPerson(p);
                                    break;
                                }
                            }
                        }
                    } else {
                        viewer.showCriticalMessage("没有找名称为“" + name + "”的对象");
                    }
                }
            }
        }
        else
            throw new NullPointerException("没有指定的视图进行操作");

    }

    public void  process_modify() {
        if(viewer ==null)
            throw new NullPointerException("没有指定的视图进行操作");
        try {
            Person tar = viewer.getSelectedPerson();//从视图上获取用户的输入
            if (tar != null) {
                Person full = viewer.getNewPersonFromUserInput(false, tar);
                if (full != null) {
                    container.modify(tar, full);
                    viewer.showInfoMessage("修改成功");

                    if(viewer.askToRefreshList())
                        process_list();
                    viewer.setSelectedPerson(null);
                } else {
                    viewer.showCriticalMessage("修改失败，没有更改信息");
                }
            } else {
                process_query();
                tar = viewer.getSelectedPerson();
                if (tar != null) {
                    Person full = viewer.getNewPersonFromUserInput(false, tar);
                    container.modify(tar, full);
                    viewer.showInfoMessage("修改成功");
                    if(viewer.askToRefreshList())
                        process_list();
                    viewer.setSelectedPerson(null);
                }
            }

        }catch (PersonNotFoundException e) {
            viewer.showCriticalMessage(e.getMessage());
        }
        catch (Exception e) {
            viewer.showCriticalMessage(e.getMessage() + "，调用堆栈：\n" + ExceptionUtility.getStackTrace(e));
        }
    }

    public void process_delete() {
        if(viewer ==null)
            throw new NullPointerException("没有指定的视图进行操作");
        try {
            Person tar = viewer.getSelectedPerson();//从视图上获取用户的选择
            if (tar != null) {
                if (viewer.askUserYesOrNo("是否删除：“" + tar + "”？")) {
                    container.delete(tar);
                    viewer.showInfoMessage("删除成功");
                    if(viewer.askToRefreshList())
                        process_list();
                    viewer.setSelectedPerson(null);//清空
                }
            } else {
                //要求进行一次查询
                process_query();
                tar = viewer.getSelectedPerson();//再次查询选择
                if (tar != null) {
                    if (viewer.askUserYesOrNo("是否删除：“" + tar + "”？")) {
                        container.delete(tar);
                        viewer.showInfoMessage("删除成功");
                        if(viewer.askToRefreshList())
                            process_list();
                        viewer.setSelectedPerson(null);//清空
                    }
                }
            }
        }catch (PersonNotFoundException e) {
            viewer.showCriticalMessage(e.getMessage());
        }
        catch (Exception e) {
            viewer.showCriticalMessage(e.getMessage() + "，调用堆栈：\n" + ExceptionUtility.getStackTrace(e));
        }
    }
    public void setViewer(Viewer viewer){

        this.viewer = viewer;
    }
    public Viewer getViewer()
    {
        return viewer;
    }
    protected boolean query_byName(Person l, Person r){
        //模糊查找，l和r具有公共字段
        if(l.getName().startsWith(r.getName()))
            return true;
        if(r.getName().startsWith(l.getName()))
            return true;
        return false;
    }


    public void save(){

    }

}
