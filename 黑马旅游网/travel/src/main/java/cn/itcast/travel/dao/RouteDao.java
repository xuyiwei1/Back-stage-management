package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;

import java.util.List;

public interface RouteDao {
    /**
     * 分页查询每页的记录
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    List<Route> findByPage(int cid, int start, int pageSize,String rname);

    /**
     * 查询当前分类的所有记录数
     * @param cid
     * @return
     */
    int findTotalCount(int cid,String rname);

    /**
     * 根据rid查询该路线的想洗信息
     * @param rid
     * @return
     */
    Route findRouteMsg(int rid);

    /**
     * 根据rid查询旅游图片
     * @param rid
     * @return
     */
    List<RouteImg> findRouteImgList(int rid);

    /**
     * 根据sid查询卖家信息
     * @param sid
     * @return
     */
    Seller findSeller(int sid);

    /**
     * 查询该旅游路线收藏次数
     * @param parseInt
     * @return
     */
    int findFavoriteCount(int parseInt);

    /**
     * 根据用户的uid和旅游路线的rid查询用户是否收藏该路线
     * @param parseInt
     * @param uid
     * @return
     */
    Favorite findByUidAndRid(int parseInt, int uid);

    /**
     * 添加收藏方法
     * @param parseInt
     * @param uid
     */
    void add(int parseInt, int uid);
}
