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

@WebServlet("/user/*") //user表示用户的相关操作的servlet、*表示user这个servlet下的所有方法
public class UserServlet extends BaseServlet {

    private UserService service = new UserServiceImpl();

    /**
     * 用户登陆
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        service = new UserServiceImpl();
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

    /**
     * 查询单个用户
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取登陆成功的用户对象
        Object user = request.getSession().getAttribute("user");
        //将User对象写回客户端
        ObjectMapper mapper = new ObjectMapper();
        //设置传回的数据类型为Json格式
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),user);
    }


    /**
     * 用户注册
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        service = new UserServiceImpl();
        boolean flag = service.registUser(user);
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

    /**
     * 用户退出
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.删除session域中的user对象 invalidate()无效的
        request.getSession().invalidate();
        //2.跳转到首页
        response.sendRedirect(request.getContextPath()+"/index.html");
    }

    /**
     * 用户激活
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取激活码
        String code = request.getParameter("code");
        //判断激活码是否为空，防止有人恶意访问服务器
        if(code != null) {
            //调用service中的active方法激活用户
            service = new UserServiceImpl();
            boolean flag = service.active(code);


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
}
