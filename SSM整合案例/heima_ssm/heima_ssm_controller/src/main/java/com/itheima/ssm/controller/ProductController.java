package com.itheima.ssm.controller;

import com.itheima.ssm.domain.Product;
import com.itheima.ssm.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @RequestMapping("/save.do")
    public String save(Product product) {

        productService.save(product);

        //保存商品完成后访问findAll.do重新查询所有商品
        return "redirect:findAll.do";
    }


    @RequestMapping("/findAll.do")
    @RolesAllowed("ADMIN")//进行角色权限的控制，只有ADMIN权限的用户才能访问findAll方法
    public ModelAndView findAll() {
        ModelAndView mv = new ModelAndView();
        List<Product> products = productService.findAll();

        mv.addObject("productList",products);
        mv.setViewName("product-list");


        return mv;
    }

}
