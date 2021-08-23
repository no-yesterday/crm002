package com.liu.dao;
//dao层 应该是个接口，为什么 因为 可以使用 aop ， 目前 不用aop ， 就可以直接写成类了。
import com.liu.bean.User;
import com.liu.util.DBHelper;
import com.liu.util.PageBeanUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserDao {
    //增 删 改 查
    //查询 select * from t_user
    //很多框架都是 基于这个 jdbc 来的，所以必须 学好
    //要链接数据库，就必须用到刚刚 DBHelper.getConnection() 来创建一个 和 mysql 的一个链接的对象
    //这个对象可以负责和mysql链接

    //登录 select * from t_user where username = ? and password = ?
    public User login(String username,String password){
        User user = null;
        //1.创建连接
        Connection connection = DBHelper.getConnection();
        //2.sql
        String sql = " select * from t_user where username = ? and password = ? ";
        //3.使用链接对象获取 预编译对象
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1,username);
            ps.setString(2,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //4.执行 预编译对象 ,得出结果集
        try {
            rs = ps.executeQuery();
            if (rs.next()){
                user = new User();
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    //查询所有的业务员
    public List<User> selectAllByService() {
        List<User> users = new ArrayList<>();
        Connection connection = DBHelper.getConnection();
        //步骤二：创建sql语句
        String sql = "select * from t_user where type = 2";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //步骤三：使用链接对象 获取 预编译对象
            ps = connection.prepareStatement(sql);
            System.out.println("rs = " + rs);
            //步骤四：执行 预编译对象，得出结果集
            rs = ps.executeQuery();
            //步骤五：遍历结果集 ，一一的取对象
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
                users.add(user);
                //System.out.println("username =" + rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return users;
    }
    //查询
    //步骤一： 创建出 连接对象
    public List<User> selectAll() {
        List<User> users = new ArrayList<>();
        Connection connection = DBHelper.getConnection();
        //步骤二：创建sql语句
        String sql = "select * from t_user";
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //步骤三：使用链接对象 获取 预编译对象
            ps = connection.prepareStatement(sql);
            System.out.println("rs = " + rs);
            //步骤四：执行 预编译对象，得出结果集
            rs = ps.executeQuery();
            //步骤五：遍历结果集 ，一一的取对象
            while (rs.next()){
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
                users.add(user);
                //System.out.println("username =" + rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return users;
    }

    //动态的分页查询
    //页数
    //条数
    public List<User> selectAllByParam(Map map){

        String page = (String) map.get("page");//接收到前端的参数，放到map里
        String limit = (String) map.get("limit");
        String real_name = (String) map.get("real_name");
        String type = (String) map.get("type");
        String username = (String) map.get("username");
        List<User> lists = new ArrayList<>();
        //1.创建连接对象
        Connection connection = DBHelper.getConnection();
        //2.写sql
//        String sql = "select * from t_user limit ? , ?";
        String sql = "select * from t_user where 1 = 1 ";
        if(null != real_name && real_name.length()>0){
            sql = sql + " and real_name like '%"+real_name+"%' ";
        }
        if(null != type && type.length()>0){
            sql = sql + " and type like '%"+type+"%' ";
        }
        if(null != username && username.length()>0){
            sql = sql + " and username like '%"+username+"%' ";
        }
        sql = sql + "limit  ? , ? ";
        System.out.println("dao  de  sql = " + sql);
        PreparedStatement ps = null;
        ResultSet rs = null;
        PageBeanUtil pageBeanUtil = new PageBeanUtil(Integer.parseInt(page),Integer.parseInt(limit));
        try {
            //3.编译sql
            ps = connection.prepareStatement(sql);
            ps.setInt(1,pageBeanUtil.getStart());
            ps.setInt(2,Integer.parseInt(limit));

            //4.执行sql
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
                lists.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                //5.关闭
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return lists;
    }

    //查询总条数
    public int selectCount(Map map1){

        String real_name = (String) map1.get("real_name");
        String type = (String) map1.get("type");
        String username = (String) map1.get("username");

        int total = 0;
        //1.连接
        Connection connection = DBHelper.getConnection();
        //2.sql
//        String sql = "select count(*) total from t_user";
        String sql = "select * from t_user where 1 = 1 ";
        if(null != real_name && real_name.length()>0){
            sql = sql + " and real_name like '%"+real_name+"%' ";
        }
        if(null != type && type.length()>0){
            sql = sql + " and type like '%"+type+"%' ";
        }
        if(null != username && username.length()>0){
            sql = sql + " and username like '%"+username+"%' ";
        }
        System.out.println(" count de sql = " + sql);
        //3.编译
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            //4.执行
            rs = ps.executeQuery();
            if(rs.next()){
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //5.关闭流
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return total;
    }

    //新增
    public int addUser(User user){
        //第一：创建连接对象
        Connection connection = DBHelper.getConnection();
        //第二：sql语句,因为 添加 的数据是变量，所以要用问号替代
        String sql = " insert into t_user values (null , ? , ? , ? , ? , ? , ? , ? , ?) ";

        PreparedStatement ps = null;
        int i = 0;

        try {
            //第三步：预编译sql
            ps = connection.prepareStatement(sql);

            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getReal_name());
            ps.setString(4,user.getImg());
            ps.setInt(5,user.getType());
            ps.setInt(6,user.getIs_del());
            ps.setString(7,user.getCreate_time());
            ps.setString(8,user.getModify_time());
            //第四步：执行 编译对象
            i=ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }
    //删除
    public int delid(int id){
        //1.创建连接对象
        Connection connection = DBHelper.getConnection();
        //2.sql
        String sql = "delete from t_user where id = ?";
        //3.预编译
        PreparedStatement ps = null;
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            //4.执行 编译对象
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return i;
    }

    //修改
    public int update(User user){
        //1.创建连接对象
        Connection connection = DBHelper.getConnection();
        //2.sql
        String sql = "update t_user set username=?, password=?, real_name=?, img=?, type=?, is_del=?, create_time=?, modify_time=? where id =?";
        //3.预编译
        PreparedStatement ps = null;
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getReal_name());
            ps.setString(4,user.getImg());
            ps.setInt(5,user.getType());
            ps.setInt(6,user.getIs_del());
            ps.setString(7,user.getCreate_time());
            ps.setString(8,user.getModify_time());
            ps.setInt(9,user.getId());
            //4.执行预编译对象
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return i;
    }

    //修改是否能用
    public int updateUserById(Integer sfDel,Integer userId){
        //开连接
        Connection connection = DBHelper.getConnection();
        //写sql
        String sql = "update t_user set is_del = ? where id = ?";
        //编译
        PreparedStatement ps = null;
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,sfDel);
            ps.setInt(2,userId);
            //执行
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    //全部的修改
    public int updateUser(User user){
        //1.开链接
        Connection connection = DBHelper.getConnection();
        //2.写sql
        String sql = "update t_user set create_time = ? ,ing = ? ,is_del = ? ,modify_time = ? ,password = ? , real_name = ? ,type = ? ,username = ? where id = ?";
        //编译sql
        PreparedStatement ps = null;
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1,user.getCreate_time());
            ps.setString(2,user.getImg());
            ps.setInt(3,user.getIs_del());
            ps.setString(4,user.getModify_time());
            ps.setString(5,user.getPassword());
            ps.setString(6,user.getReal_name());
            ps.setInt(7,user.getType());
            ps.setString(8,user.getUsername());
            ps.setInt(9,user.getId());
            //执行
            i = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return i;
    }

    //根据id查询
    public User selectUserById(Integer id){
        User user = new User();
        //1.开链接
        Connection connection = DBHelper.getConnection();
        //2.写sql
        String sql = "select * from t_user where id = ?";
        //编译
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            //执行
            rs = ps.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setCreate_time(rs.getString("create_time"));
                user.setImg(rs.getString("img"));
                user.setIs_del(rs.getInt("is_del"));
                user.setModify_time(rs.getString("modify_time"));
                user.setPassword(rs.getString("password"));
                user.setReal_name(rs.getString("real_name"));
                user.setType(rs.getInt("type"));
                user.setUsername(rs.getString("username"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
    public static void main(String[] args){
        UserDao dao = new UserDao();
        //全查
//        List<User> users = dao.selectAll();
//        for (User user: users) {
//            System.out.println("user = " + user);
//        }

//        //增加
//        User user = new User();
//        user.setUsername("liushunshun1");
//        user.setType(1);
//        user.setReal_name("刘顺顺");
//        user.setPassword("123");
//        user.setModify_time("2000-10-16");
//        user.setIs_del(1);
//        user.setImg("xxx");
//        user.setCreate_time("2000-10-16");
//        int i = dao.addUser(user);
//        System.out.println("i = " + i);

//        //删除
//        User user = new User();
//        int i = dao.delid(7);
//        System.out.println("i = " + i);

//        //修改
//        User user = new User();
//        user.setUsername("liushunshun2");
//        user.setType(1);
//        user.setReal_name("刘顺顺");
//        user.setPassword("123");
//        user.setModify_time("2000-10-16");
//        user.setIs_del(1);
//        user.setImg("xxx");
//        user.setCreate_time("2000-10-16");
//        user.setId(8);
//        int i = dao.update(user);
//        System.out.println("i = " + i);

//        //登录
//        User liu = dao.login("liushunshun","123");
//        System.out.println("liu = " + liu);

//        //分页查询的测试
//        List<User> users = dao.selectAllByParam(1, 5);
//        System.out.println("users = " + users);

//        //查询条数测试
//        int i = dao.selectCount();
//        System.out.println("i = " + i);

        //修改是否能用开关
        int i = dao.updateUserById(2, 25);
        System.out.println("i = " + i);
    }
}
