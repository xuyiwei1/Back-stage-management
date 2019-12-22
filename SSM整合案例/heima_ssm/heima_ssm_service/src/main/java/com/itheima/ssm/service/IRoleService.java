package com.itheima.ssm.service;

import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Role;

import java.util.List;

public interface IRoleService {

    List<Role> findAll();

    void save(Role role);

    //根据roleId查询角色
    Role findById(String roleId);

    //根据roleId查询role还可以添加的资源权限
    List<Permission> findAllPermissionById(String roleId);

    //给角色添加资源权限
    void addPermissionToRole(String roleId, String[] permissionIds);
}
