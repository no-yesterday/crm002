package com.liu.controller;

import com.alibaba.fastjson.JSONObject;
import com.liu.bean.Visit;
import com.liu.service.VisitService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@WebServlet(name = "VisitAddServlet",urlPatterns = "/VisitAddServlet")
public class VisitAddServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 修正 编码
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html; charset=UTF-8"); // 注意 , 别写错

        //2.接收参数
        String user_id = req.getParameter("user_id");
        String cust_id = req.getParameter("id");
        String visit_desc = req.getParameter("visit_desc");
        String visit_time = req.getParameter("visit_time");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String create_time = simpleDateFormat.format(new Date());

        //3.赋值到 实体类
        Visit visit = new Visit();
        visit.setUser_id(Integer.parseInt(user_id));
        visit.setCust_id(Integer.parseInt(cust_id));
        visit.setVisit_desc(visit_desc);
        visit.setVisit_time(visit_time);
        visit.setCreate_time(create_time);

        //4.调用service
        VisitService service = new VisitService();
        Map map = service.addVisit(visit);

        String string = JSONObject.toJSONString(map);
        PrintWriter writer = resp.getWriter();
        writer.println(string);
        writer.close();
    }
}
