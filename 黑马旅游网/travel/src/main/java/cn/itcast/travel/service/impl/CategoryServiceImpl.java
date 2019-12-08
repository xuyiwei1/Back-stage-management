package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao dao = new CategoryDaoImpl();
    /**
     * 查询目录分类
     * @return
     */
    @Override
    public List<Category> findAll() {


        //通过Redis缓存优化查询目录,使用sortedSet是查询出来的分类数据有顺序
        Jedis jedis = JedisUtil.getJedis();

        Set<Tuple> categories = jedis.zrangeWithScores("category", 0, -1);
        List<Category> list = null;
        if(categories == null || categories.size() == 0) {
            //redis缓存中没有分类数据
            //1.从数据库查询分类数据
            list = dao.findAll();
            //2.遍历list将分类数据存储在redis缓存中
            for (Category category : list) {
                jedis.zadd("category",category.getCid(),category.getCname());
            }

        }else {
            //redis中有数据
            //将set集合转换为list
            list = new ArrayList<Category>();
            for (Tuple tuple : categories) {
                Category category = new Category();
                category.setCid((int)tuple.getScore());
                category.setCname(tuple.getElement());
                list.add(category);

            }
        }


        return list;
    }
}
