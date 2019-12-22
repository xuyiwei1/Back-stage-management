package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Permission;
import com.itheima.ssm.domain.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface IRoleDao {

    @Select("select * from role where id in (select roleId from users_role where userId = #{userId} )")
    @Results(
            value = {
                    @Result(id = true,property = "id",column = "id"),
                    @Result(property = "roleName",column = "roleName"),
                    @Result(property = "roleDesc",column = "roleDesc"),
                    @Result(property = "permissions",column = "id",javaType = java.util.List.class ,many = @Many(select = "com.itheima.ssm.dao.IPermissionDao.findById")),
            }
    )
    List<Role> findRolesById(String userId);


    @Select("select * from role")
    List<Role> findAll();

    @Insert("insert into role (roleName,roleDesc) values (#{roleName},#{roleDesc})")
    void save(Role role);

    @Select("select * from role where id = #{roleId}")
    Role findById(String roleId);

    @Select("select * from permission where id not in (select permissionId from role_permission where roleId = #{roleId})")
    List<Permission> findAllPermissionById(String roleId);

    @Insert("insert into role_permission (permissionId,roleId) values (#{permissionId} , #{roleId})")
    void addPermissionToRole(@Param("roleId") String roleId,@Param("permissionId") String permissionId);
}
