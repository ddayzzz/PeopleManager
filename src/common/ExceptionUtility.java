package common;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtility {
    /**
    * @Author: Shu Wang
    * @Desciption: 通用的函数，把异常的类的堆栈信息保存下来
    * @param e:抛出的错误对象
    * @Date:15:45 2017/12/27
    */
    public static final String getStackTrace(Throwable e)
    {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        return stringWriter.toString();
    }
}
