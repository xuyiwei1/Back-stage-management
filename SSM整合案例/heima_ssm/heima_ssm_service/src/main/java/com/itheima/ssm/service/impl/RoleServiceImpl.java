package com.itheima.ssm.service.impl;

import com.itheima.ssm.dao.IRoleDao;
import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Role;
import com.itheima.ssm.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements IRoleService {

    @Autowired
    private IRoleDao roleDao;

    /**
     * 查询所有角色
     * @return
     */
    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    /**
     * 添加一个角色
     * @param role
     */
    @Override
    public void save(Role role) {
        roleDao.save(role);
    }

    /**
     * 根据roleId查询角色
     * @param roleId
     * @return
     */
    @Override
    public Role findById(String roleId) {
        return roleDao.findById(roleId);
    }

    /**
     * 查询角色还可以添加的资源权限
     * @param roleId
     * @return
     */
    @Override
    public List<Permission> findAllPermissionById(String roleId) {
        return roleDao.findAllPermissionById(roleId);
    }

    /**
     * 给角色添加资源权限
     * @param roleId
     * @param permissionIds
     */
    @Override
    public void addPermissionToRole(String roleId, String[] permissionIds) {

        for (String permissionId : permissionIds) {
            roleDao.addPermissionToRole(roleId,permissionId);
        }
    }
}
