package com.itheima.ssm.controller;

import com.itheima.ssm.domain.SysLog;
import com.itheima.ssm.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    //注入request对象
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ISysLogService sysLogService;

    //开始访问的时间
    private Date visitTime;

    //记录访问的方法
    private Method method;

    //记录访问的类
    private Class clazz;


    //在切入点之前执行的方法
    @Before("execution(* com.itheima.ssm.controller.*.*(..))")
    public void before(JoinPoint jt) throws NoSuchMethodException {
        //记录访问的时间
        visitTime = new Date();

        //获取访问的类
        clazz = jt.getTarget().getClass();

        //获取访问的类的方法的方法名
        String methodName = jt.getSignature().getName();

        //获取方法中的所有参数
        Object[] args = jt.getArgs();

        if (args.length == 0 || args == null) {
            //方法没有参数
            method = clazz.getMethod(methodName);
        } else {
            //方法有参数
            Class[] classArgs = new Class[args.length];

            for (int i = 0; i < classArgs.length; i++) {
                classArgs[i] = args[i].getClass();
            }

            method = clazz.getMethod(methodName, classArgs);
        }
    }

    //方法执行结束执行
    @After("execution(* com.itheima.ssm.controller.*.*(..))")
    public void After(JoinPoint jt) {
        //获取方法持续的时间
        Long executionTime = new Date().getTime() - visitTime.getTime();

        //访问的url
        String url = "";

        //访问的类和方法不为空并且类不是日志切面且不是查询日志本身
        if(clazz != null && method != null && clazz != LogAop.class && clazz != SysLogController.class) {

            //获取类上的url  @RequestMapping("/user")
            RequestMapping clazzAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if(clazzAnnotation != null ) {
                //存在该注解
                //获取类上注解的值
                String[] clazzValue = clazzAnnotation.value();//获取注解中的值的数组
                //获取方法上注解
                RequestMapping methodAnnotation = this.method.getAnnotation(RequestMapping.class);
                if(methodAnnotation != null) {
                    //方法存在
                    //获取方法上注解的值
                    String[] methodValue = methodAnnotation.value();
                    //获取url
                    url = clazzValue[0] + methodValue[0];
                    //获取ip地址
                    String ip = request.getRemoteAddr();

                    //获取操作者，spring security 中有
                    SecurityContext context = SecurityContextHolder.getContext();
                    User user = (User) context.getAuthentication().getPrincipal();
                    String username = user.getUsername();

                    SysLog sysLog = new SysLog();

                    //将日志信息封装到SysLog对象中
                    sysLog.setIp(ip);
                    sysLog.setExecutionTime(executionTime);
                    sysLog.setUrl(url);
                    sysLog.setUsername(username);
                    sysLog.setMethod("[类名] "+clazz.getName()+"[方法名] "+method.getName());
                    sysLog.setVisitTime(visitTime);

                    //调用service将日志保存到数据库

                    sysLogService.save(sysLog);
                }
            }
        }




    }

}
