package mvc.viewer;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import javax.swing.*;

import common.PersonType;
import container.IContainer;
import container.Person;
import mvc.StudentInfoDialog;
import mvc.WorkerInfoDialog;
import mvc.BasicPersonInfoDialog;
public class GUIViewer implements Viewer{

    private JLabel label_list;
    private JButton button_list, button_insert, button_query, button_delete, button_modify;
    private JList<Person> list_listResult;
    private DefaultListModel  listModel_list;
    private BasicPersonInfoDialog basicInfo_dialog;
    private JFrame jframe;
    private JDialog dialog;
    public GUIViewer(PersonType viewType){
        switch (viewType)
        {
            case Worker:
                jframe = new JFrame("工人管理系统");
                basicInfo_dialog = new WorkerInfoDialog();
                dialog = new JDialog(jframe,"工人管理系统",true);
                break;
            case Student:
                jframe = new JFrame("学生管理系统");
                basicInfo_dialog = new StudentInfoDialog();
                dialog = new JDialog(jframe,"学生管理系统",true);
                break;
        }
        label_list = new JLabel("当前的对象：（0）");
        list_listResult = new JList<Person>();
        //设置list的样式
        listModel_list = new DefaultListModel();
        list_listResult.setModel(listModel_list);
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(list_listResult);
        //btn_actions = new ActionListener(target_model, this);
        button_delete = new JButton("删除");
        button_insert = new JButton("插入");
        button_list = new JButton("显示");
        button_modify = new JButton("修改");
        button_query = new JButton("查询");
        button_insert.setName("insert");
        button_modify.setName("modify");
        button_list.setName("list");
        button_delete.setName("delete");
        button_query.setName("query");

        JPanel panel_buttons = new JPanel();
        panel_buttons.setLayout(new FlowLayout());
        panel_buttons.add(button_list);
        panel_buttons.add(button_insert);
        panel_buttons.add(button_query);
        panel_buttons.add(button_delete);
        panel_buttons.add(button_modify);

        BorderLayout layout_border = new BorderLayout();
        dialog.setLayout(layout_border);
        dialog.add("North", label_list);
        dialog.add("South", panel_buttons);
        dialog.add("Center", scrollPane);
        dialog.setSize(500,200);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.setVisible(false);
            }
        });
        //统一设置字体
        Font btn_font = new Font("微软雅黑", Font.PLAIN, 18);
        label_list.setFont(new Font("微软雅黑", Font.BOLD, 11));
        button_delete.setFont(btn_font);
        button_query.setFont(btn_font);
        button_insert.setFont(btn_font);
        button_list.setFont(btn_font);
        button_modify.setFont(btn_font);
        list_listResult.setFont(new Font("微软雅黑", Font.PLAIN, 14));

    }


    @Override
    public void showCriticalMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg, "出现了异常",JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public boolean askUserYesOrNo(String text) {
        if(JOptionPane.showConfirmDialog(null,
                text, "是否继续？",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
            return true;

        else
            return false;
    }

    @Override
    public void showAllPersons(IContainer c) {
        listModel_list.removeAllElements();
        for(Person obj : c)
        {
            listModel_list.addElement(obj);
        }
    }

    @Override
    public void changeTitleText(String title) {
        this.label_list.setText(title);
    }

    @Override
    public String getUserInput(String title, String text) {
        String inputValue = JOptionPane.showInputDialog(null, text, title, JOptionPane.PLAIN_MESSAGE);
        return inputValue;
    }

    @Override
    public Person getSelectedPerson() {
        Object selected = list_listResult.getSelectedValue();
        if(selected == null)
            return null;
        return (Person)selected;
    }

    @Override
    public void showInfoMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg, "消息",JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void setSelectedPerson(Person person) {
        if(person !=null)
        {
            for(int i=0;i<listModel_list.getSize();++i)
                if(listModel_list.get(i).equals(person))
                {
                    list_listResult.setSelectedIndex(i);
                    break;
                }
        }
    }

    @Override
    public void showPersonInfo(Person p) {
        basicInfo_dialog.justShowInfo(p);
    }

//    @Override
//    public void setModel(String modelId, Model model) {
//        if(controler != null)
//            controler.setModel(modelId, model);
//    }

    @Override
    public Person getNewPersonFromUserInput(boolean idEditable, Person oldPerson) {
        if(idEditable == false)
        {
            basicInfo_dialog.justModify(oldPerson);
        }
        else
        {
            //获取新的对象
            basicInfo_dialog.show(true);
        }
        if (basicInfo_dialog.DialogResult == BasicPersonInfoDialog.EDialogResult.OK) {
            Person np = basicInfo_dialog.getNewPersonObject();
            return np;
        }
        else
            return null;

    }


    @Override
    public boolean askToRefreshList() {
        return askUserYesOrNo("是否立即更新视图？");
    }

//    @Override
//    public void showQueryPersonsResult(String[] res) {
//
//    }


    @Override
    public int askManyOptions(String title, String text, String[] optionsText){
        if(optionsText ==null || optionsText.length == 0)
            return -1;
        int m = JOptionPane.showOptionDialog(null, text, title,JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, optionsText, optionsText[0]);
        return m;
    }

    @Override
    public int showQueryPersonsResultAndGetSelectResult(String title, String text, Collection res) {
        //assert(res.length > 0);
        if (res != null && res.size() > 0) {
            //有一个就可以
            Object[] objects = res.toArray();
            Object s = JOptionPane.showInputDialog(null, text, title, JOptionPane.PLAIN_MESSAGE, null, objects, objects[0]);
            if (s == null)
                return -1;
            for (int i = 0; i < objects.length; ++i) {
                if (s.equals(objects[i])) {
                    return i;
                }
            }
        }
        return -1;
    }
    @Override
    public void addActionListenerToButtons(ActionListener actionListener)
    {
        button_list.addActionListener(actionListener);
        button_insert.addActionListener(actionListener);
        button_query.addActionListener(actionListener);
        button_delete.addActionListener(actionListener);
        button_modify.addActionListener(actionListener);
    }
    @Override
    public boolean needAddBuutonActionListener(){
        return button_list.getActionListeners() == null || button_list.getActionListeners().length == 0;
    }

    public void show(){
        dialog.setVisible(true);
    }
}
