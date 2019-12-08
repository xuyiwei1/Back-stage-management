package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//此servlet不需要被浏览器访问
public class BaseServlet extends HttpServlet {
   //service方法为tomcat服务器的入口函数,所有访问该servlet的要执行该方法
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //功能：实现根据不同方法执行，方法的分发
        //1.获取uri
        String uri = req.getRequestURI();// /travel/user/login
        //2.获取方法名
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);//截取方法名login

        try {
            //3.根据方法名通过反射创建Method对象.this表示调用service的servlet
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this,req,resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将对象转为Json格式写回页面
     * @param obj
     * @param response
     * @throws IOException
     */
    public void writeValue(Object obj , HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),obj);
    }

    /**
     * 将对象转为Json
     * @param obj
     * @return
     * @throws JsonProcessingException
     */
    public String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }


}
