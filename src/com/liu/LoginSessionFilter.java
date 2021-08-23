package com.liu;

import com.liu.bean.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//过滤器，过滤所有的  /* ,请求和响应信息
@WebFilter(filterName = "LoginSessionFilter",urlPatterns = "/*")
public class LoginSessionFilter implements Filter {
    public void destroy() {
        System.out.println("过滤器死亡了");
    }

    /**
     * @parm
     */

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        //根据session 来判断，有 则可以进入，没有，退出到登录页
        //服务器有session了 就不是null ，没登录就是 null
        HttpSession session = request.getSession();
        Object user = (User) session.getAttribute("user");
        System.out.println("user = " + user);
        StringBuffer requestURL = request.getRequestURL(); 
        //就是 login.html 的地址
        System.out.println("requestURL = " + requestURL);
        if(requestURL.equals("/login.html")
                || requestURL.equals("/CodeServlet")
                || requestURL.equals("/layui-v2.5.6/layui/css/layui.css")
                || requestURL.equals("/layui-v2.5.6/layui/layui.js")
                || requestURL.equals("/favicon.ico")
                || requestURL.equals("/layui-v2.5.6/layui/lay/modules/form.js")
                || requestURL.equals("/layui-v2.5.6/layui/lay/modules/layer.js")
                || requestURL.equals("/layui-v2.5.6/layui/lay/modules/layer/default/layer.css")
                || requestURL.equals("/layui-v2.5.6/layui/lay/modules/jquery.js")
        ){
            chain.doFilter(req, resp);//放行的意思，否则不让进
        } else {
            if (user != null) {
                chain.doFilter(req, resp);//放行的意思，否则不让进
                //   .js   .css  .png 等等 ， 都不需要过滤
            } else {
                //跳转到 登录页面
                response.sendRedirect("/login.html");
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

        System.out.println("过滤器出生了，出生特别早，项目一运行，他就出生了");
    }

}
