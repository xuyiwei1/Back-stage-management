package cn.itcast.travel.web.servlet;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class ActiveUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码
        String code = request.getParameter("code");
        //判断激活码是否为空，防止有人恶意访问服务器
        if(code != null) {
            //调用service中的active方法激活用户
            UserService userService = new UserServiceImpl();
            boolean flag = userService.active(code);


            String msg = null;
            //判断是否激活成功
            if(flag) {
                //激活成功
                msg = "恭喜你，激活成功，请<a href='login.html'>登录</a>";
            }else {
                //激活失败,大部分情况是由网络 等人为因素造成
                msg = "激活失败，请联系管理员";
            }

            //将响应信息写会客户端
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);


        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
