package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取用户输入的参数
        Map<String, String[]> map = request.getParameterMap();
        //2.将map数据封装为一个User
        User user = new User();
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用service中的login方法登陆
        UserService service = new UserServiceImpl();
        User u = service.login(user);

        //封装一个ResultInfo的对象 存储信息
        ResultInfo info = new ResultInfo();
        if(u == null) {
            //用户名或密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名或者密码错误");

        }

        if(u != null && !"Y".equals(u.getStatus())) {
            //用户已经注册但是没有被激活
            info.setFlag(false);
            info.setErrorMsg("用户没有被激活,请先激活");
        }

        if(u != null && "Y".equals(u.getStatus())) {

            request.getSession().setAttribute("user",u);//登录成功标记，用于向客户端传递用户信息并且显示在页面上
            //用户已经成功注册,并且已经激活
            info.setFlag(true);
        }

        //将info转为Json格式，写回客户端
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");

        mapper.writeValue(response.getOutputStream(),info);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
