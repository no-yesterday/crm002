package com.liu.controller;

import com.alibaba.fastjson.JSONObject;
import com.liu.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "CustomerSelectAllServlet" , urlPatterns ="/CustomerSelectAllServlet" )
public class CustomerSelectAllServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. 修正 编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8"); // 注意 , 别写错

        // 2. 接收  2个参数 page limit
        String page = req.getParameter("page");
        String limit = req.getParameter("limit");

        String cname = req.getParameter("cname");
        String lookTime = req.getParameter("lookTime");
        String phoneNumber = req.getParameter("phoneNumber");
        String sex = req.getParameter("sex");
        String uname = req.getParameter("uname");

        Map paramMap=new HashMap<>();
        paramMap.put("page",page);
        paramMap.put("limit",limit);

        paramMap.put("cname",cname);
        paramMap.put("lookTime",lookTime);
        paramMap.put("phoneNumber",phoneNumber);
        paramMap.put("sex",sex);
        paramMap.put("uname",uname);

        CustomerService service = new CustomerService();
        Map map = service.selectAllByParam(paramMap);

        PrintWriter writer = resp.getWriter();
        String s= JSONObject.toJSONString(map);
        writer.println(s);
        writer.close();
    }
}