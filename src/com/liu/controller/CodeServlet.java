package com.liu.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CodeServlet",urlPatterns = "/CodeServlet")
public class CodeServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //使用hutool 工具 创建验证码
//        //1.创建验证码
//        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
//        CircleCaptcha captcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 20);
//        //2.拿到验证码
//        String code = captcha.getCode();
//        //3.获取session
//        HttpSession session = req.getSession();
//        //4.把验证码放入到session中
//        session.setAttribute("code",code);
//        //5.将验证码发送到浏览器
//        ServletOutputStream outputStream = resp.getOutputStream();
//        captcha.write(outputStream);
//        outputStream.close();

        //定义图形验证码的长和宽
        //线形验证码
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        String code1 = lineCaptcha.getCode();
        HttpSession session1 = req.getSession();
        session1.setAttribute("code1",code1);
        ServletOutputStream outputStream1 = resp.getOutputStream();
        lineCaptcha.write(outputStream1);
        outputStream1.close();
    }
}
