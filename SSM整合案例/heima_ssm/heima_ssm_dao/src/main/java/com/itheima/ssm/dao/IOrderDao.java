package com.itheima.ssm.dao;

import com.itheima.ssm.domain.Member;
import com.itheima.ssm.domain.Orders;
import com.itheima.ssm.domain.Product;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderDao {

    @Select("select * from orders")
    @Results(
            id = "orderMap",
            value = {
                    @Result(id = true, property = "id", column = "id"),
                    @Result(property = "orderNum", column = "orderNum"),
                    @Result(property = "orderTime", column = "orderTime"),
                    @Result(property = "orderStatus", column = "orderStatus"),
                    @Result(property = "peopleCount", column = "peopleCount"),
                    @Result(property = "payType", column = "payType"),
                    @Result(property = "orderDesc", column = "orderDesc"),
                    @Result(property = "product", column = "productId", one = @One(select = "com.itheima.ssm.dao.IProductDao.findById"))
            }

    )
    List<Orders> findAll();


    @Select("select * from orders where id = #{ordersId}")
    @Results(
            value = {
                    @Result(id = true, property = "id", column = "id"),
                    @Result(property = "orderNum", column = "orderNum"),
                    @Result(property = "orderTime", column = "orderTime"),
                    @Result(property = "orderStatus", column = "orderStatus"),
                    @Result(property = "peopleCount", column = "peopleCount"),
                    @Result(property = "payType", column = "payType"),
                    @Result(property = "orderDesc", column = "orderDesc"),
                    @Result(property = "product", column = "productId", javaType = Product.class,one = @One(select = "com.itheima.ssm.dao.IProductDao.findById")),
                    @Result(property = "member",column = "memberId",javaType = Member.class, one = @One(select = "com.itheima.ssm.dao.IMemberDao.findById")),
                    @Result(property = "travellers",column = "id",javaType = java.util.List.class, many = @Many(select = "com.itheima.ssm.dao.ITravellerDao.findByOrdersId"))
            }
    )
    Orders findById(String ordersId);
}
