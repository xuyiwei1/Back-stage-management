package com.itheima.ssm.service;

import com.itheima.ssm.domain.Orders;

import java.util.List;

public interface IOrderService {

    List<Orders> findAll(Integer page,Integer pageSize);

    Orders findById(String ordersId);
}
