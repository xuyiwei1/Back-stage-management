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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //判断验证码是否正确
        //获取用户输入的验证码
        String checkCode = request.getParameter("check");
        //获取服务器生成的随机验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //成功获取验证码后销毁session中随机的验证码，防止重复的验证码
        session.removeAttribute("CHECKCODE_SERVER");
        //忽略大小写判断验证码是否相同，没有输入验证码
        if(checkcode_server == null || !checkcode_server.equalsIgnoreCase(checkCode)) {
            //验证码输入错误或者是没有输入验证码
            //封装错误的结果信息
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码输入错误!!");

            //将结果信息转为Json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            //设置编码格式
            response.setContentType("application/json;charset=utf-8");
            //将错误信息写回客户端
            response.getWriter().write(json);

            //以下代码都不执行
            return;

        }



        //获取从客户端传来的数据
        Map<String, String[]> parameterMap = request.getParameterMap();
        //将获取到的数据封装为User对象
        User user = new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用service方法中的registUser()方法注册
        UserService userService = new UserServiceImpl();
        boolean flag = userService.registUser(user);
        ResultInfo info = new ResultInfo();

        //根据registUser()的返回值判断注册是否成功
        if(flag) {
            //注册成功
            info.setFlag(true);
        }else {
            //注册失败
            info.setFlag(false);
            info.setErrorMsg("注册失败！！！");
        }

        //将结果信息转为Json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        //设置编码格式
        response.setContentType("application/json;charset=utf-8");
        //将错误信息写回客户端
        response.getWriter().write(json);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
