package mvc.model;

import common.PersonType;
import container.Person;
import container.PersonExistsException;
import persons.Student;
import persons.Worker;

import java.io.*;

public class TextModel extends StandardModel {
    String filepath;
    public TextModel(String filepath, PersonType personType)
    {
        super();
        this.filepath = filepath;
        //从文本读取
        BufferedReader reader = null;
        try {
            File file = new File(filepath);
            reader = new BufferedReader(new FileReader(file));//会抛出NOTFOUND的异常
            String line;
            Person person = null;
            while((line = reader.readLine()) !=null)
            {
                if(personType == PersonType.Student)
                    person = Student.makeStudent(line);
                else if(personType == PersonType.Worker)
                    person = Worker.makeWorker(line);
                if(person == null)
                {
                    if(viewer !=null)
                        viewer.showCriticalMessage("无法从：“" + line + "”构造Person的一个子类实例。");
                    continue;
                }

                container.insert(person);
            }
        }catch (FileNotFoundException e)
        {
            if(viewer !=null)
                viewer.showCriticalMessage("文件：“" + filepath + "”不存在");
        }
        catch (IOException e)
        {
            if(viewer !=null)
                viewer.showCriticalMessage("读取文件：“" + filepath + "”发生了IO异常：" + e.getMessage());
        }
        catch (PersonExistsException e)
        {
            if(viewer !=null)
                viewer.showCriticalMessage("添加ID相同的对象，这个对象被抛弃。");
        }
        finally {
            if(reader !=null)
            {
                try{
                    reader.close();
                }catch(IOException E){}
            }
        }
    }

    @Override
    public void save() {
        //保存为文本
        //保存到序列化的文件
        BufferedWriter out = null;
        try {
            File fileInfo = new File(filepath);
            out = new BufferedWriter(new FileWriter(fileInfo));
            for(Object p : container)
            {
                out.write(p.toString());
                out.newLine();
            }

        } catch (Exception e) {
            if(viewer !=null)
                viewer.showCriticalMessage("将数据保存在“" + filepath + "”中失败：" + e.getMessage());
        }
        finally {
            if(out !=null)
            {
                try{
                    out.close();
                }
                catch (IOException E){}
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
