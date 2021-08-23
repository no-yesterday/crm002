package com.liu.service;

import com.liu.bean.User;
import com.liu.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    //登录
    public Map login(String username, String password, HttpServletRequest request){
        Map map = new HashMap();
        //service 层要调用 dao层
        UserDao dao = new UserDao();
        User userFromDB = dao.login(username, password);
        if(null == userFromDB){
            //不对，或没查出，即用户名密码不正确
            map.put("code",4001);
            map.put("msg","账户名或密码不正确");
            return map;
        }else {
            //当登录成功后，需要把登陆的用户信息放入到 session 中
            HttpSession session = request.getSession();
            session.setAttribute("user",userFromDB);
            map.put("code",0);
            map.put("msg","登录成功");
            return map;
        }
    }
    //带参数的分页查询
    public Map selectAllByParam(Map map1){
        UserDao userDao = new UserDao();
        List<User> users = userDao.selectAllByParam(map1);
        int i = userDao.selectCount(map1);
        Map map = new HashMap();
        map.put("code",200);//必须和 layui 的 json 的返回的格式一样，不一样数据出不来
        map.put("msg","查询成功");
        map.put("count",i);//把死的写活
        map.put("data",users);
        //根据layui的 返回的 json 格式 去返回给你的数据,如果不一样，需要layui 解析
//        {
//            code:0,
//            msg,"",
//            count:1000,
//            data:[每条数据]
//        }

        //故意写错，需要layui解析
        Map map2 = new HashMap();
        map2.put("number",2001);
        map2.put("message","查询成功");
        map2.put("object",map);

        return map2;
    }

    //修改是否能用
    public Map updateUserById(Integer sfDel,Integer userId){
        UserDao dao = new UserDao();
        int i = dao.updateUserById(sfDel, userId);
        Map map = new HashMap();
        if (i == 1){
            map.put("code",0);
            map.put("msg","修改成功");
        }else {
            map.put("code",400);
            map.put("msg","修改不成功");
        }
        return map;
    }

    //修改全部
    public Map updateUser(User user){
        Map codeMap = new HashMap();
        UserDao dao = new UserDao();
        int i = dao.updateUser(user);
        if(i == 1){
            codeMap.put("code",0);
            codeMap.put("msg","请求成功");
        }else {
            codeMap.put("code",400);
            codeMap.put("msg","请求失败");
        }
        return codeMap;
    }

    //按id 查询1个user
    public Map selectUserById(Integer id){
        UserDao userDao = new UserDao();
        User user = userDao.selectUserById(id);
        Map codeMap = new HashMap();
        codeMap.put("code",0);
        codeMap.put("mag","查询成功");
        codeMap.put("data",user);
        return codeMap;
    }

    //全查 业务员
    public Map selectAllByService(){
        UserDao userDao = new UserDao();
        List<User> users = userDao.selectAllByService();
        Map codeMap = new HashMap();
        codeMap.put("code",0);
        codeMap.put("msg","ok");
        codeMap.put("data",users);
        return codeMap;
    }
}
