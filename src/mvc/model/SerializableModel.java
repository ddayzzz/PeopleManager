package mvc.model;

import container.IContainer;

import java.io.*;

public class SerializableModel extends StandardModel {
    private String targetFile;
    public SerializableModel(String targetFile)
    {
        super();
        this.targetFile = targetFile;//保存为文件
        ObjectInputStream in = null;
        //反序列化
        try{
            File fi = new File(targetFile);
            if(fi.exists() == true)//反序列化的数据存在
            {
                in = new ObjectInputStream(new FileInputStream(fi));
                Object des = in.readObject();
                container = (IContainer)des;
            }

        }
        catch(Exception e)
        {
            if(viewer !=null)
                viewer.showCriticalMessage("从“" + targetFile + "”反序列化对象失败：" + e.getMessage() + "，调用堆栈：\n" + e.getStackTrace());
            else
            {
                System.err.println("从“" + targetFile + "”反序列化对象失败：" + e.getMessage() + "，调用堆栈：\n");
                e.printStackTrace();
            }

        }
        finally {
            if(in !=null)
            {
                try{
                    in.close();
                }
                catch (IOException e)
                {

                }
            }
        }
    }

    @Override
    public void save() {
        //super.finalize();
        //保存到序列化的文件
        ObjectOutputStream out = null;
        try {
            File fileInfo = new File(targetFile);
            out = new ObjectOutputStream(new FileOutputStream(fileInfo));
            out.writeObject(container);
            out.close();

        } catch (Exception e) {
            if(viewer !=null)
                viewer.showCriticalMessage("从“" + targetFile + "”序列化对象失败：" + e.getMessage() + "，调用堆栈：\n" + e.getStackTrace());
            else
            {
                System.err.println("从“" + targetFile + "”反序列化对象失败：" + e.getMessage() + "，调用堆栈：\n");
                e.printStackTrace();
            }

        }
    }

    @Override
    public void process_delete() {
        super.process_delete();
        //每次操作之后重新序列化
        save();
    }

    @Override
    public void process_insert() {
        super.process_insert();
        save();
    }

    @Override
    public void process_modify() {
        super.process_modify();
        save();
    }
}
