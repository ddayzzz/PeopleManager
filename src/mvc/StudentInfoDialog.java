package mvc;

import container.Person;
import persons.Student;

import javax.swing.*;

public final class StudentInfoDialog extends BasicPersonInfoDialog {
    private JLabel label_grade;
    private JTextField textfield_grade;
    public StudentInfoDialog()
    {
        super(4,2);
        label_grade = new JLabel("成绩：");
        textfield_grade = new JTextField();
        panel_fields.add(label_grade);
        panel_fields.add(textfield_grade);
    }
    public Student getNewPersonObject(){
        Student ss =  new Student(textField_id.getText(), textField_name.getText(), Integer.valueOf(textField_age.getText()),
                Float.valueOf(textfield_grade.getText()));
        //可能需要清空字段的值
        restore();
        return ss;
    }
    @Override
    public void justShowInfo(Person s)
    {
        assert(s instanceof Student);
        super.justShowInfo(s);
        Student stu = (Student)s;
        //特别的字段
        textfield_grade.setEditable(false);
        textfield_grade.setText(String.valueOf(stu.getGrades()));
        //显示
        show(false);
        restore();

    }
    @Override
    public  boolean isValid()
    {
        return super.isValid() && !textfield_grade.getText().equals("");
    }
    @Override
    public void justModify(Person old){
        assert(old instanceof Student);
        super.justModify(old);
        Student s= (Student)old;
        textfield_grade.setText(String.valueOf(s.getGrades()));
        show(false);
    }
    @Override
    protected void restore(){
        super.restore();
        textfield_grade.setText("");
        textfield_grade.setEditable(true);
    }
}
