package com.itheima.ssm.controller;

import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Role;
import com.itheima.ssm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;


    @RequestMapping("/addPermissionToRole.do")
    public String addPermissionToRole(@RequestParam(name = "roleId" ,required = true) String roleId ,@RequestParam(name = "ids" , required = true) String[] permissionIds) {

        roleService.addPermissionToRole(roleId,permissionIds);

        return "redirect:findAll.do";
    }



    @RequestMapping("/findRoleByIdAndAllPermission.do")
    public ModelAndView findRoleByIdAndAllPermission(@RequestParam(name = "id",required = true) String roleId) {
        ModelAndView mv = new ModelAndView();
        //根据id查询角色
        Role role = roleService.findById(roleId);
        //根据id查询角色还可以添加的资源权限
        List<Permission> permissionList = roleService.findAllPermissionById(roleId);

        mv.addObject("role",role);
        mv.addObject("permissionList",permissionList);

        mv.setViewName("role-permission-add");

        return mv;
    }



    @RequestMapping("/save.do")
    public String save(Role role) {

        roleService.save(role);

        return "redirect:/role/findAll.do";
    }


    @RequestMapping("/findAll.do")
    public ModelAndView findAll() {
        ModelAndView mv = new ModelAndView();

        List<Role> roleList = roleService.findAll();

        mv.addObject("roleList",roleList);
        mv.setViewName("role-list");

        return mv;
    }

}
