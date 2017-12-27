package mvc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FindDialog {
    public enum Result{
        OK,
        Cancel
    }
    private JDialog dialog;
    private JTextField textField;
    public String getQueryText(){
        return textField.getText();
    }
    public Result DialogResult;
    public FindDialog(String title)
    {
        textField = new JTextField();
        JButton ok = new JButton("确定");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textField.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(null, "查找的内容不完整", "查找失败",JOptionPane.ERROR_MESSAGE);
                    return;
                }
                DialogResult = Result.OK;
                dialog.setVisible(false);
            }
        });
        JFrame frame = new JFrame();
        dialog = new JDialog(frame, title, true);
        dialog.setSize(400,150);
        dialog.setLayout(new BorderLayout());
        dialog.add("Center", textField);
        dialog.add("North", new JLabel(title));
        dialog.add("South", ok);
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                DialogResult = Result.Cancel;
                dialog.setVisible(false);
            }
        });
    }
    public void show(){
        dialog.setVisible(true);
    }
    public void restore()
    {
        textField.setText("");
    }
}
