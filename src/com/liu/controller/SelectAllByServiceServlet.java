package com.liu.controller;

import com.alibaba.fastjson.JSONObject;
import com.liu.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "SelectAllByServiceServlet",urlPatterns = "/SelectAllByServiceServlet")
public class SelectAllByServiceServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.修改字符编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=UTF-8");

        UserService userService = new UserService();
        Map map = userService.selectAllByService();

        String string = JSONObject.toJSONString(map);
        PrintWriter writer = resp.getWriter();
        writer.println(string);
        writer.close();

    }
}
