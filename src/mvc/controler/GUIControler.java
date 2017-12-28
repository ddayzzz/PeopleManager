package mvc.controler;

import common.PersonType;
import mvc.model.Model;
import mvc.viewer.GUIViewer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import mvc.model.StandardModel;
import mvc.viewer.Viewer;

public class GUIControler extends Controler {
    private JDialog dialog;

    @Override
    public void show() {
        dialog.setVisible(true);

    }

    public GUIControler() {
        super();
        dialog = new JDialog(new JFrame(), "管理系统", true);
        dialog.setLayout(new FlowLayout());
        JButton btn_stu = new JButton("运行学生管理系统");
        JButton btn_worker = new JButton("运行工人管理系统");
        btn_stu.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        btn_worker.setFont(new Font("微软雅黑", Font.PLAIN, 20));
        dialog.add(btn_stu);
        dialog.add(btn_worker);
        dialog.setSize(300, 200);
        //设置响应器
        btn_worker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model work = stringModelHashMap.getOrDefault(PersonType.Worker, null);
                if (work != null) {
                    Viewer viewer = work.getViewer();
                    if (viewer != null && viewer instanceof GUIViewer) {
                        GUIViewer guiViewer = (GUIViewer) viewer;
                        if (guiViewer.needAddBuutonActionListener())
                            guiViewer.addActionListenerToButtons(new ButtonAction(work));
                        guiViewer.show();
                    }
                    else
                        JOptionPane.showMessageDialog(null, "没有指定正确的GUI视图", "无法显示视图", JOptionPane.ERROR_MESSAGE);


                } else
                    JOptionPane.showMessageDialog(null, "没有数据工人的数据模型", "无法显示视图", JOptionPane.ERROR_MESSAGE);


            }
        });
        btn_stu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Model stu = stringModelHashMap.getOrDefault(PersonType.Student, null);
                if (stu != null) {
                    Viewer viewer = stu.getViewer();
                    if (viewer != null && viewer instanceof GUIViewer) {
                        GUIViewer guiViewer = (GUIViewer) viewer;
                        if (guiViewer.needAddBuutonActionListener())
                            guiViewer.addActionListenerToButtons(new ButtonAction(stu));
                        guiViewer.show();
                    } else {
                        JOptionPane.showMessageDialog(null, "没有指定正确的GUI视图", "无法显示视图", JOptionPane.ERROR_MESSAGE);

                    }
                } else {
                    JOptionPane.showMessageDialog(null, "没有数据学生的数据模型", "无法显示视图", JOptionPane.ERROR_MESSAGE);

                }

            }
        });
        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dialog.setVisible(true);
            }
        });
    }


}
class ButtonAction implements java.awt.event.ActionListener {
    private Model model;
    public ButtonAction(Model model)
    {
        this.model = model;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //一般是按钮发出的消息
        JButton sender = (JButton) e.getSource();
        if (sender.getName().equals("list"))
            model.process_list();
        else if (sender.getName().equals("insert"))
            model.process_insert();
        else if (sender.getName().equals("query"))
            model.process_query();
        else if (sender.getName().equals("modify")) {
            model.process_modify();
        } else if (sender.getName().equals("delete")) {
            model.process_delete();
        }
    }
}
