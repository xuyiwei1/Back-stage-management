package com.itheima.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.itheima.ssm.domain.Orders;
import com.itheima.ssm.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private IOrderService orderService;

    //未分页代码
    /*@RequestMapping("/findAll.do")
    public ModelAndView findAll() {
        ModelAndView mv = new ModelAndView();

        List<Orders> ordersList = orderService.findAll();
        mv.addObject("ordersList",ordersList);
        mv.setViewName("orders-list");

        return mv;
    }*/

    //分页代码
    @Secured("ROLE_ADMIN")
    @RequestMapping("/findAll.do")
    public ModelAndView findAll(@RequestParam(name = "page",required = true,defaultValue = "1")Integer page,@RequestParam(name = "pageSize",required = true,defaultValue = "4")Integer pageSize) {

        ModelAndView mv = new ModelAndView();
        List<Orders> ordersList = orderService.findAll(page,pageSize);
        PageInfo pageInfo = new PageInfo(ordersList);//封装了分页后的订单数据

        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("orders-page-list");

        return mv;

    }

    @RequestMapping("/findById.do")
    public ModelAndView findById(@RequestParam(name = "id",required = true)String ordersId) {
        ModelAndView mv = new ModelAndView();

        Orders orders = orderService.findById(ordersId);

        mv.addObject("orders",orders);
        mv.setViewName("orders-show");

        return mv;
    }

}
