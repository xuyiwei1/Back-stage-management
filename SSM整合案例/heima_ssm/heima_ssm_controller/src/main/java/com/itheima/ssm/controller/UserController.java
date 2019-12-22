package com.itheima.ssm.controller;

import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import com.itheima.ssm.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;


    @RequestMapping("/addRoleToUser.do")
    public String addRoleToUser(@RequestParam(name = "userId", required = true) String userId , @RequestParam(name = "ids" , required = true) String[] roleIds) {

        userService.addRoleToUser(userId,roleIds);


        //给用户添加角色完成后再次查询所有用户，使用这可以点击详情查看用户角色
        return "redirect:findAll.do";
    }


    @RequestMapping("/findUserByIdAndAllRole.do")
    public ModelAndView findUserByIdAndAllRole(@RequestParam(name = "id" , required = true) String userId) {
        ModelAndView mv = new ModelAndView();

        UserInfo user = userService.findById(userId);
        List<Role> roleList = userService.findUserByIdAndAllRole(userId);

        mv.addObject("user",user);
        mv.addObject("roleList",roleList);

        mv.setViewName("user-role-add");

        return mv;
    }


    @RequestMapping("/findById.do")
    public ModelAndView findById(String id) {
        ModelAndView mv = new ModelAndView();
        UserInfo userInfo = userService.findById(id);

        mv.addObject("user",userInfo);

        mv.setViewName("user-show");

        return mv;
    }


    @RequestMapping("/save.do")
    public String save(UserInfo userInfo) {

        userService.save(userInfo);


        //添加完用户后，应该在查询一次用户信息，展示给管理员看
        return "redirect:/user/findAll.do";
    }


    @RequestMapping("/findAll.do")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView findAll() {
        ModelAndView mv = new ModelAndView();

        List<UserInfo> userList = userService.findAll();

        mv.addObject("userList",userList);

        mv.setViewName("user-list");
        return mv;

    }


}
