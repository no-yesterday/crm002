package com.liu.controller;

import com.alibaba.fastjson.JSONObject;
import com.liu.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;



//接收登陆传来的参数
@WebServlet(name = "LoginServlet",urlPatterns = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //修改字符编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");
        //接收login.html传来的三个参数
        String username = req.getParameter("username");
        String userpwd = req.getParameter("userpwd");
        String code = req.getParameter("code");
        //登陆的时候要验证 验证码是否正确
        //获取后台的验证码
        HttpSession session = req.getSession();
        String codeFromSession = (String) session.getAttribute("code");
        System.out.println("codeFromSession = " + codeFromSession);
        if(!codeFromSession.equals(code)){
            //验证错误
            //向前端 输入一段json 告知json 验证码错误
            PrintWriter writer = resp.getWriter();
            Map map = new HashMap();
            map.put("code",400);
            map.put("msg","验证码不正确");
            //把map变为json
            String jsonString= JSONObject.toJSONString(map);
            writer.println(jsonString);
            writer.close();
        }else{
            //验证码正确 继续判断账号和密码是否正确
            System.out.println("验证码正确 判断账户密码");
            //需要service和dao层判断 业务不是很多 就不需要service层
            UserService userService = new UserService();
            Map login = userService.login(username, userpwd, req);
            String s = JSONObject.toJSONString(login);
            PrintWriter writer=resp.getWriter();
            writer.print(s);
            writer.close();
        }

    }
}
