package mvc;

import container.Person;
import persons.Student;
import persons.Worker;

import javax.swing.*;

public final class WorkerInfoDialog extends  BasicPersonInfoDialog {
    private JLabel label_job, label_salary;
    private JTextField textField_job, textField_salary;
    public WorkerInfoDialog()
    {
        super(5,2);
        dialog.setSize(dialog.getSize().width, dialog.getSize().height + 30);
        label_job = new JLabel("工作：");
        textField_job = new JTextField();
        label_salary = new JLabel("薪水：");
        textField_salary = new JTextField();
        panel_fields.add(label_job);
        panel_fields.add(textField_job);
        panel_fields.add(label_salary);
        panel_fields.add(textField_salary);
    }
    public Worker getNewPersonObject() {
        Worker ss = new Worker(textField_id.getText(), textField_name.getText(), Integer.valueOf(textField_age.getText()),
                textField_job.getText(), Float.valueOf(textField_salary.getText()));
        //可能需要清空字段的值
        restore();
        return ss;
    }
    @Override
    public void justShowInfo(Person s)
    {
        assert(s instanceof Worker);
        super.justShowInfo(s);
        Worker stu = (Worker) s;
        //特别的字段
        textField_salary.setEditable(false);
        textField_salary.setText(String.valueOf(stu.getSalary()));
        textField_job.setEditable(false);
        textField_job.setText(String.valueOf(stu.getJob()));
        //显示
        show(false);
        restore();

    }
    @Override
    public  boolean isValid()
    {
        return super.isValid() && !textField_job.getText().equals("") && !textField_salary.getText().equals("");
    }
    @Override
    public void justModify(Person old){
        assert(old instanceof Worker);
        super.justModify(old);
        Worker s= (Worker)old;
        textField_salary.setText(String.valueOf(s.getSalary()));
        textField_job.setText(String.valueOf(s.getJob()));
        show(false);
    }
    @Override
    protected void restore(){
        super.restore();
        textField_job.setText("");
        textField_job.setEditable(true);
        textField_salary.setText("");
        textField_salary.setEditable(true);
    }
}
