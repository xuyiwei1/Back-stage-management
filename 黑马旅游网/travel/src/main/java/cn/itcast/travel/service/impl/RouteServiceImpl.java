package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.*;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    private RouteDao dao = new RouteDaoImpl();

    /**
     * 查询当前分类中页面的所有信息
     * @param cid 当前页面的分类id
     * @param currentPage 当前页数
     * @param pageSize 每页显示的记录数
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        //从哪条记录开始查询 1 0  2 5  3 10  ( currentPage -1 ) * pageSize
        int start = (currentPage -1) * pageSize;
        //封装PageBean
        PageBean<Route> pageBean = new PageBean<Route>();

        List<Route> list = dao.findByPage(cid,start,pageSize,rname);
        pageBean.setList(list);
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        int totalCount = dao.findTotalCount(cid,rname);
        pageBean.setTotalCount(totalCount);

        //计算总页数 总记录是否能被每页显示的记录数整除
        int totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : (totalCount / pageSize) + 1;
        pageBean.setTotalPage(totalPage);


        return pageBean;
    }

    @Override
    public Route findOne(String ridStr) {


        //查询旅游线路的详细信息
        Route route = dao.findRouteMsg(Integer.parseInt(ridStr));

        //查询旅游线路的图片
        List<RouteImg> routeImgList = dao.findRouteImgList(Integer.parseInt(ridStr));

        //查询该旅游路线收藏次数
        int count = dao.findFavoriteCount(Integer.parseInt(ridStr));

        //查询旅游线路的卖家信息
        Seller seller = dao.findSeller(route.getSid());

        //将信息设置到route中
        route.setRouteImgList(routeImgList);
        route.setSeller(seller);
        route.setCount(count);


        return route;
    }

    @Override
    public Boolean isFavorite(String rid, int uid) {

        Favorite favorite = dao.findByUidAndRid(Integer.parseInt(rid),uid);


        return favorite != null;
    }

    @Override
    public void add(String rid, int uid) {
        //调用dao中的添加收藏方法
        dao.add(Integer.parseInt(rid),uid);
    }
}
