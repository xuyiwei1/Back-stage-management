package cn.itcast.travel.service;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {
    /**
     * 查询当前分类中页面的所有信息
     * @param cid 当前页面的分类id
     * @param currentPage 当前页数
     * @param pageSize 每页显示的记录数
     * @return
     */
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname);

    /**
     * 根据路线的rid查询该路线的详细信息
     * @param ridStr
     * @return
     */
    Route findOne(String ridStr);


    /**
     * 判断用户是否收藏该旅游路线
     * @param rid
     * @param uid
     * @return
     */
    Boolean isFavorite(String rid, int uid);

    /**
     * 添加收藏方法
     * @param rid
     * @param uid
     */
    void add(String rid, int uid);
}
