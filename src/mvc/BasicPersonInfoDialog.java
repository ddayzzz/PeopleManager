package mvc;

import container.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//定义最基本的个人信息，返回一个新的Person对象
public abstract class BasicPersonInfoDialog {
    public enum EDialogResult{
        OK,
        Cancel
    }
    protected JButton button_ok, button_cancel;
    protected JTextField textField_id, textField_name, textField_age;
    protected JLabel label_id, label_name, label_age;
    protected JFrame frame_main;
    protected JPanel panel_fields, panel_buttons;
    protected JDialog dialog;
    public EDialogResult DialogResult;
    public BasicPersonInfoDialog(int rows, int cols)
    {
        frame_main = new JFrame("基本的个人信息");
        panel_buttons = new JPanel();
        panel_fields = new JPanel();
        panel_fields.setLayout(new GridLayout(rows, cols));
        button_cancel  = new JButton("关闭");
        button_ok = new JButton("确定并关闭");
        label_id = new JLabel("ID：");
        label_name = new JLabel("姓名：");
        label_age = new JLabel("年龄：");
        textField_age = new JTextField();
        textField_id = new JTextField();
        textField_name = new JTextField();
        textField_name.setSize(100,30);
        textField_id.setSize(100,30);
        textField_age.setSize(100,30);


        panel_fields.add(label_id);
        panel_fields.add(textField_id);
        panel_fields.add(label_name);
        panel_fields.add(textField_name);
        panel_fields.add(label_age);
        panel_fields.add(textField_age);

        panel_buttons.add(button_ok);
        panel_buttons.add(button_cancel);
        dialog = new JDialog(frame_main, "信息管理&修改对话框", true);
        dialog.setSize(700,400);
        dialog.setLayout(new BorderLayout());
        dialog.add("Center", panel_fields);
        dialog.add("South", panel_buttons);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DialogResult = EDialogResult.Cancel;
                dialog.setVisible(false);
            }
        });
        //添加事件
        button_ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!isValid())
                {
                    JOptionPane.showMessageDialog(null, "一个或多个信息项不完整", "保存新的对象失败",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                DialogResult = EDialogResult.OK;
                dialog.setVisible(false);
            }
        });
        button_cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogResult = EDialogResult.Cancel;
                dialog.setVisible(false);
            }
        });
    }
    public void show(boolean idEditable){
        textField_id.setEditable(idEditable);
        frame_main.pack();
        dialog.setVisible(true);

    }
    public abstract Person getNewPersonObject();
    public void justShowInfo(Person person){
        textField_id.setEditable(false);
        textField_age.setEditable(false);
        textField_name.setEditable(false);
        button_ok.setEnabled(false);
        assert(person != null);
        textField_name.setText(person.getName());
        textField_age.setText(String.valueOf(person.getAge()));
        textField_id.setText(person.getId());
    }
    public void justModify(Person oldPerson)
    {
        this.textField_name.setText(oldPerson.getName());
        this.textField_id.setText(oldPerson.getId());
        this.textField_age.setText(String.valueOf(oldPerson.getAge()));
    }
    public boolean isValid()
    {
        return !(textField_id.getText().equals("") ||
                textField_age.getText().equals("") ||
                textField_name.getText().equals(""));
    }
    protected void restore(){
        textField_name.setText("");
        textField_age.setText("");
        textField_id.setText("");
        button_ok.setEnabled(true);
        button_cancel.setEnabled(true);
        textField_age.setEditable(true);
        textField_id.setEditable(true);
        textField_name.setEditable(true);

    }

}
