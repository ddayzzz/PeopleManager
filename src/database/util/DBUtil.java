package database.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
/*
* 整个DAO模型参考了：http://www.cnblogs.com/smyhvae/p/4059514.html
* 连接方式：http://www.cnblogs.com/smyhvae/p/4054235.html
* */
public class DBUtil {
    private static String url="jdbc:mysql://localhost:3306/javadao";
    private static String userName="root";
    private static String password="";
    private static String driver="com.mysql.jdbc.Driver";
    static {
        try{
            Class.forName(driver);
        }catch (ClassNotFoundException e){
            System.err.println("无法找到数据库驱动类型，调用堆栈：");
            e.printStackTrace();
        }
    }
    public static Connection getConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(url, userName, password);
        }
        catch (SQLException e){
            System.err.println("数据库连接失败，调用堆栈：");
            e.printStackTrace();
        }
        return connection;
    }

    public static void closeConnection(ResultSet rs,Statement stat,Connection conn) {

        try {
            if(rs !=null) rs.close();
            if(stat !=null) stat.close();
            if(conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("关闭数据库连接失败，调用堆栈：");
            e.printStackTrace();
        }
    }

}
