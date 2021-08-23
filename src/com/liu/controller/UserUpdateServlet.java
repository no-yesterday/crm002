package com.liu.controller;

import com.alibaba.fastjson.JSONObject;
import com.liu.bean.User;
import com.liu.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "UserUpdateServlet",urlPatterns = "/UserUpdateServlet")
public class UserUpdateServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.修改字符编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        //2.接收前端参数
        String is_del = req.getParameter("is_del");
        String modify_time = req.getParameter("modify_time");
        String password = req.getParameter("password");
        String real_name = req.getParameter("real_name");
        String type = req.getParameter("type");
        String username = req.getParameter("username");
        //缺少一个最重要的 参数，即： 主键id 因为 修改是按照主键id 来修改。
        String id = req.getParameter("id");
//        //打印
//        System.out.println("is_del = " + is_del);
//        System.out.println("modify_time = " + modify_time);
//        System.out.println("password = " + password);
//        System.out.println("real_name = " + real_name);
//        System.out.println("type = " + type);
//        System.out.println("username = " + username);
//        System.out.println("id = " + id);

        //调用service层
        UserService userService = new UserService();
        Map map = userService.selectUserById(Integer.parseInt(id));
        User data = (User) map.get("data");

        //把参数赋值成对象
        //在修改之前，先查询出来 前端没有的参数
        User user = new User();
        user.setCreate_time("??");
        user.setImg("??");
        user.setIs_del(Integer.parseInt(is_del));
        user.setModify_time(modify_time);
        user.setPassword(password);
        user.setReal_name(real_name);
        user.setType(Integer.parseInt(type));
        user.setUsername(username);
        user.setId(Integer.parseInt(id));

        Map map1 = userService.updateUser(user);
        String string = JSONObject.toJSONString(map1);
        PrintWriter writer = resp.getWriter();
        writer.println(string);
        writer.close();


    }
}
