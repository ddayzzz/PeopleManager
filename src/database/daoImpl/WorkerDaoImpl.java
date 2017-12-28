package database.daoImpl;

import container.Person;
import database.dao.PersonDao;
import database.dao.SQLPrimaryKeyInTheSameException;
import database.dao.SQLPrimaryKeyNotFoundException;
import database.util.DBUtil;
import persons.Student;
import persons.Worker;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class WorkerDaoImpl implements PersonDao<Worker> {
    private DBUtil dbUtil = new DBUtil();
    @Override
    public void add(Worker worker) throws SQLException, SQLPrimaryKeyInTheSameException {
        Connection conn = null;
        PreparedStatement ps =null;
        String sql = "insert into workers(id,name,age,job,salary)values(?,?,?,?,?)";
        try{
            //是否已经存在，避免这种问题抛出很长的错误
            if(find(worker.getId()) != null)
                throw new SQLPrimaryKeyInTheSameException(worker + "插入失败，存在ID重复的对工人数据");
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, worker.getId());
            ps.setString(2, worker.getName());
            ps.setInt(3, worker.getAge());
            ps.setString(4, worker.getJob());
            ps.setFloat(5, worker.getSalary());
            ps.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new SQLException("数据工人添加失败");
        }
        finally {
            DBUtil.closeConnection(null, null,conn);
        }
    }

    @Override
    public void delete(String id) throws SQLException,SQLPrimaryKeyNotFoundException {
        Connection conn = null;
        PreparedStatement ps =null;
        String sql = "delete from workers where id=?";
        try{
            //是否已经存在，避免这种问题抛出很长的错误
            if(find(id) == null)
                throw new SQLPrimaryKeyNotFoundException("删除ID为“" + id + "”的工人失败：没有找到这个工人对象");
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
    public Worker find(String id)throws SQLException {
        Connection conn = null;
        PreparedStatement ps =null;
        String sql = "select name,age,job,salary from workers where id=?";
        ResultSet rs=null;
        Worker stu = null;
        try{
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1,id);
            rs= ps.executeQuery();
            if(rs.next())
            {
                stu = new Worker(id, rs.getString(1), rs.getInt(2), rs.getString(3),rs.getFloat(4));
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
    public void update(Worker old, Worker student) throws SQLException, SQLPrimaryKeyNotFoundException{
        Connection conn = null;
        PreparedStatement ps =null;
        String sql = "update workers set name=?,age=?,job=?,salary=? where id=?";
        try{
            //是否已经存在，避免这种问题抛出很长的错误
            if(find(old.getId()) == null)
                throw new SQLPrimaryKeyNotFoundException("修改" + old + "工人失败：没有找到这个工人对象");
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, student.getName());
            ps.setInt(2, student.getAge());
            ps.setString(3, student.getJob());
            ps.setFloat(4, student.getSalary());
            ps.setString(5, old.getId());
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
        String sql = "select count(id) as size from workers";
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
            throw new SQLException("获取Workers数据表失败");
        }
        finally {
            DBUtil.closeConnection(rs,null,conn);
        }
        return c;
    }
    //充当find all
    @Override
    public Iterator<Worker> iterator() {
        Connection conn = null;
        PreparedStatement ps =null;
        String sql = "select id,name,age,job,salary from workers";
        ResultSet rs=null;
        ArrayList<Worker> sts = new ArrayList<Worker>();
        try{
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs= ps.executeQuery();
            while(rs.next()){
                sts.add(new Worker(rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getFloat(5)));
            }
            return sts.iterator();

        }
        catch (SQLException e){
            System.err.println("枚举所有的工人对象失败");
            e.printStackTrace();

        }
        finally {
            DBUtil.closeConnection(rs,null,conn);
        }
        return null;
    }
    @Override
    public ArrayList<Worker> find(String field, String regex) throws SQLException {
        Connection conn = null;
        PreparedStatement ps =null;
        String sql = "select id,name,age,job,salary from workers where " + field + " REGEXP '" + regex +"'";
        ResultSet rs=null;
        ArrayList<Worker> stus = new ArrayList<>();
        try{
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
//            ps.setString(1,field);
            //ps.setString(2, regex);‘？’默认作为字符
            rs= ps.executeQuery();
            while(rs.next())
            {
                stus.add(new Worker(rs.getString(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getFloat(5)));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new SQLException("按照正则表达式语句“" +regex +"”查询表workers中的字段“" + field + "”的所有匹配项失败：" + e.getMessage());
        }
        finally {
            DBUtil.closeConnection(rs,null,conn);
        }
        return stus;
    }


}
