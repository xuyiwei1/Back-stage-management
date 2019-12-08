package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/findUserServlet")
public class FindUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取登陆成功的用户对象
        Object user = request.getSession().getAttribute("user");
        //将User对象写回客户端
        ObjectMapper mapper = new ObjectMapper();
        //设置传回的数据类型为Json格式
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
