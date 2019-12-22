package com.itheima.ssm.service;

import com.itheima.ssm.domain.Product;

import java.util.List;

public interface IProductService {

    //查找所有商品
    public List<Product> findAll();

    //保存商品
    void save(Product product);
}
