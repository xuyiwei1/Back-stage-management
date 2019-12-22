package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Role;
import com.itheima.ssm.domain.UserInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface IUserDao {


    @Select("select * from users where username = #{userName}")
    @Results(
            value = {
                    @Result(property = "id",column = "id",id = true),
                    @Result(property = "username",column = "username"),
                    @Result(property = "email",column = "email"),
                    @Result(property = "password",column = "password"),
                    @Result(property = "phoneNum",column = "phoneNum"),
                    @Result(property = "status",column = "status"),
                    @Result(property = "roles",column = "id",javaType = java.util.List.class,many = @Many(select = "com.itheima.ssm.dao.IRoleDao.findRolesById")),
            }
    )
    UserInfo findUserByName(String userName);

    @Select("select * from users")
    List<UserInfo> findAll();

    @Insert("insert into users (email,username,password,phoneNum,status) values (#{email},#{username},#{password},#{phoneNum},#{status})")
    void save(UserInfo userInfo);

    @Select("select * from users where id = #{id}")
    @Results(value = {
            @Result(property = "id",column = "id",id = true),
            @Result(property = "username",column = "username"),
            @Result(property = "email",column = "email"),
            @Result(property = "password",column = "password"),
            @Result(property = "phoneNum",column = "phoneNum"),
            @Result(property = "status",column = "status"),
            @Result(property = "roles",column = "id",javaType = java.util.List.class,many = @Many(select = "com.itheima.ssm.dao.IRoleDao.findRolesById"))
    })
    UserInfo findById(String id);

    //从中间表查询已经存在的用户角色id，然后在从角色表中查询用户没有的角色
    @Select("select * from role where id not in (select roleId from users_role where userId = #{userId})")
    List<Role> findUserByIdAndAllRole(String userId);


    //将每个角色id和用户id添加到users_role的中间表里
    @Insert("insert into users_role (userId , roleId) values (#{userId} , #{roleId})")
    void addRoleToUser(@Param("userId") String userId, @Param("roleId") String roleId);//一个以上的方法参数需要添加注解
}
