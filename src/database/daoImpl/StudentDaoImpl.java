package database.daoImpl;

import database.dao.PersonDao;
import database.dao.SQLPrimaryKeyInTheSameException;
import database.dao.SQLPrimaryKeyNotFoundException;
import database.util.DBUtil;
import persons.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class StudentDaoImpl implements PersonDao<Student>{
    private DBUtil dbUtil = new DBUtil();
    @Override
    public void add(Student student) throws SQLException, SQLPrimaryKeyInTheSameException {
        Connection conn = null;
        PreparedStatement ps =null;
        String sql = "insert into students(id,name,age,grades)values(?,?,?,?)";
        try{
            //是否已经存在，避免这种问题抛出很长的错误
            if(find(student.getId()) != null)
                throw new SQLPrimaryKeyInTheSameException(student + "插入失败，存在ID重复的对学生数据");
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, student.getId());
            ps.setString(2, student.getName());
            ps.setInt(3, student.getAge());
            ps.setFloat(4, student.getGrades());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new SQLException("数据添加失败");
        }
        finally {
            DBUtil.closeConnection(null, null,conn);
        }
    }

    @Override
    public void delete(String id) throws SQLException,SQLPrimaryKeyNotFoundException {
        Connection conn = null;
        PreparedStatement ps =null;
        String sql = "delete from students where id=?";
        try{
            //是否已经存在，避免这种问题抛出很长的错误
            if(find(id) == null)
                throw new SQLPrimaryKeyNotFoundException("删除ID为“" + id + "”学生失败：没有找到这个学生对象");
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new SQLException("数据删除失败");
        }
        finally {
            DBUtil.closeConnection(null, null,conn);
        }

    }

    @Override
    public Student find(String id)throws SQLException {
        Connection conn = null;
        PreparedStatement ps =null;
        String sql = "select name,age,grades from students where id=?";
        ResultSet rs=null;
        Student stu = null;
        try{
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            rs= ps.executeQuery();
            if(rs.next())
            {
                stu = new Student(id, rs.getString(1), rs.getInt(2), rs.getFloat(3));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new SQLException("按照ID“" +id +"”查询失败");
        }
        finally {
            DBUtil.closeConnection(rs,null,conn);
        }
        return stu;
    }


    @Override
    public void update(Student old, Student student) throws SQLException, SQLPrimaryKeyNotFoundException{
        Connection conn = null;
        PreparedStatement ps =null;
        String sql = "update students set name=?,age=?,grades=? where id=?";
        try{
            //是否已经存在，避免这种问题抛出很长的错误
            if(find(old.getId()) == null)
                throw new SQLPrimaryKeyNotFoundException("修改" + old + "学生失败：没有找到这个学生对象");
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setFloat(3, student.getGrades());
            ps.setString(4, old.getId());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new SQLException("数据删除失败");
        }
        finally {
            DBUtil.closeConnection(null,null,conn);
        }
    }

    @Override
    public int count() throws SQLException{
        int c = 0;
        Connection conn = null;
        PreparedStatement ps =null;
        String sql = "select count(id) as size from students";
        ResultSet rs=null;
        try{
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next())
            {
                c = rs.getInt(1);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new SQLException("获取Students数据表失败");
        }
        finally {
            DBUtil.closeConnection(rs,null,conn);
        }
        return c;
    }
    //充当find all
    @Override
    public Iterator<Student> iterator() {
        Connection conn = null;
        PreparedStatement ps =null;
        String sql = "select id,name,age,grades from students";
        ResultSet rs=null;
        ArrayList<Student> sts = new ArrayList<Student>();
        try{
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs= ps.executeQuery();
            while(rs.next()){
                sts.add(new Student(rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getFloat(4)));
            }
            return sts.iterator();

        }
        catch (SQLException e){
            System.err.println("枚举所有的学生对象失败");
            e.printStackTrace();

        }
        finally {
            DBUtil.closeConnection(rs,null,conn);
        }
        return null;
    }

    @Override
    public ArrayList<Student> find(String field, String regex) throws SQLException {
        Connection conn = null;
        PreparedStatement ps =null;
        String sql = "select id,name,age,grades from students where " + field + " REGEXP '" + regex + "'";
        ResultSet rs=null;
        ArrayList<Student> stus = new ArrayList<>();
        try{
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs= ps.executeQuery();
            while(rs.next())
            {
                stus.add(new Student(rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getFloat(4)));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new SQLException("按照正则表达式语句“" +regex +"”查询表students中的字段“" + field + "”的所有匹配项失败：" + e.getMessage());
        }
        finally {
            DBUtil.closeConnection(rs,null,conn);
        }
        return stus;
    }

    //    class SQLIterator implements Iterator<Student>{
//        private ResultSet resultSet;
//        private  Connection connection;
//        private boolean isFirst=true;
//        public SQLIterator(Connection connection, ResultSet set){
//            resultSet = set;
//            this.connection = connection;
//        }
//        @Override
//        public boolean hasNext() {
//            try{
//                if(isFirst)
//                {
//                    isFirst = false;
//                    return true;
//                }
//                return resultSet.next();
//            }
//            catch (SQLException e){
//                return false;
//            }
//            finally {
//                try{
//                    resultSet.close();
//                    DBUtil.closeConnection(connection);
//                }catch (SQLException e1){
//                    e1.printStackTrace();
//                }
//            }
//        }
//
//        @Override
//        public Student next() {
//            Student s = null;
//            try{
//                s = new Student(resultSet.getString(1),
//                        resultSet.getString(2),
//                        resultSet.getInt(3),
//                        resultSet.getFloat(4));
//            }
//            catch (SQLException e){
//                //立即关闭
//                try{
//                    resultSet.close();
//                    DBUtil.closeConnection(connection);
//                }catch (SQLException e1){
//                    e1.printStackTrace();
//                }
//            }
//            return s;
//        }
//    }
}
