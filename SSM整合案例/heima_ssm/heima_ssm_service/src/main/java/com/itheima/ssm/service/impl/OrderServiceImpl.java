package com.itheima.ssm.service.impl;

import com.github.pagehelper.PageHelper;
import com.itheima.ssm.dao.IOrderDao;
import com.itheima.ssm.domain.Orders;
import com.itheima.ssm.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderDao orderDao;

    @Override
    public List<Orders> findAll(Integer page, Integer pageSize) {

        //调用PageHelper的分页方法，从page页开始每页显示几个，原理就是sql语句中的limit page,pageSize
        PageHelper.startPage(page,pageSize);
        return orderDao.findAll();
    }

    @Override
    public Orders findById(String ordersId) {
        return orderDao.findById(ordersId);
    }
}
