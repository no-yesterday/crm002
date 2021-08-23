package com.liu.dao;

import com.liu.bean.Customer;
import com.liu.bean.User;
import com.liu.util.DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CustomerDaoTest {
    //1.带参数的全查（2个表的）  //作业1:
    public Map<Customer, User> selectAll() {
        //dao层如何和数据库做对接，我们用的知识点叫做jdbc，很基础的一个必须的技术
        //步骤1：创建出连接对象
        Map<Customer, User> maps = new HashMap<>();
        Connection connection = DBHelper.getConnection();
        //步骤2：创建出sql语句
        String sql = "select tc.id,tc.cust_name,tc.cust_company,tc.cust_birth,tc.cust_sex,tc.cust_phone,tc.cust_position,tc.create_time,tc.modify_time,tu.username from t_user tu,t_customer tc where tu.id=tc.id";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            //步骤3：使用连接对象，获取预编译对象
            ps = connection.prepareStatement(sql);
            //步骤4：执行预编译对象，得出结果集
            rs = ps.executeQuery();
            //步骤5：遍历结果集，一个一个获取对象
            while (rs.next()) {
                User user = new User();
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setCust_name(rs.getString("cust_name"));
                customer.setCust_company(rs.getString("cust_company"));
                customer.setCust_birth(rs.getString("cust_birth"));
                customer.setCust_sex(rs.getInt("cust_sex"));
                customer.setCust_phone(rs.getString("cust_phone"));
                customer.setCust_position(rs.getString("cust_position"));
                customer.setCreate_time(rs.getString("create_time"));
                customer.setModify_time(rs.getString("modify_time"));
                user.setUsername(rs.getString("username"));
                maps.put(customer, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return maps;

    }
    // 2. 带参数的查总条数 (2个表的) // 作业2:
    public int countAll(){
        Connection connection= DBHelper.getConnection();
        ResultSet rs=null;
        PreparedStatement ps =null;
        int i=0;
        String sql="select count(*) from t_customer ta,t_user tu where ta.user_id=tu.id";
        try {
            ps=connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()){
                i=rs.getInt("count(*)");
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
        return i;

    }
    public static void main(String[] args) {
        CustomerDaoTest dao = new CustomerDaoTest();
        //全查
        Map<Customer, User> cm = dao.selectAll();
        for (Customer customer : cm.keySet()) {
            System.out.println("customer = " + customer);
        }
        //总数
        int i=dao.countAll();
        System.out.println("i = " + i);
    }
}
