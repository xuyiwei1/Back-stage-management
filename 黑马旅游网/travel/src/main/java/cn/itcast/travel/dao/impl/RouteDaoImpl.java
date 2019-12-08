package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RouteDaoImpl implements RouteDao {

    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    /**
     * 分页查询每页的记录
     * @param cid
     * @param start
     * @param pageSize
     * @return
     */
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize,String rname) {

//String sql = "select * from tab_route where cid = ? and rname like ?  limit ? , ?";
       /* String sql = " select * from tab_route where 1 = 1 ";
        //1.定义sql模板
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();//条件们
        //2.判断参数是否有值
        if(cid != 0){
            sb.append( " and cid = ? ");

            params.add(cid);//添加？对应的值
        }

        if(rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");

            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? ");//分页条件

        sql = sb.toString();

        params.add(start);
        params.add(pageSize);


        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());*/

       String sql = " select * from tab_route where 1=1 ";
       StringBuilder sb = new StringBuilder(sql);//拼sql语句
       List params = new ArrayList();//保存参数
        if(cid != 0 ) {
            sb.append(" and cid = ?");
            params.add(cid);
        }

        if(rname != null && rname.length() > 0 && !"null".equals(rname)) {
            sb.append(" and rname like ?");
            params.add("%"+rname+"%");
        }

        sb.append(" limit ? , ? ");
        params.add(start);
        params.add(pageSize);

        sql = sb.toString();

        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());

    }

    /**
     * 查询当前分类的所有记录数
     * @param cid
     * @return
     */
    @Override
    public int findTotalCount(int cid,String rname) {

//String sql = "select count(*) from tab_route where cid = ?";
        //1.定义sql模板
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();//条件们
        //2.判断参数是否有值
        if(cid != 0){
            sb.append( " and cid = ? ");

            params.add(cid);//添加？对应的值
        }

        if(rname != null && rname.length() > 0 && !"null".equals(rname)){
            sb.append(" and rname like ? ");

            params.add("%"+rname+"%");
        }

        sql = sb.toString();


        return template.queryForObject(sql,Integer.class,params.toArray());

    }

    @Override
    public Route findRouteMsg(int rid) {

        String sql = "select * from tab_route where rid = ?";


        return template.queryForObject(sql,new BeanPropertyRowMapper<Route>(Route.class),rid);
    }

    @Override
    public List<RouteImg> findRouteImgList(int rid) {

        String sql = "select * from tab_route_img where rid = ?";


        return template.query(sql,new BeanPropertyRowMapper<RouteImg>(RouteImg.class),rid);
    }

    @Override
    public Seller findSeller(int sid) {

        String sql = "select * from tab_seller where sid = ?";


        return template.queryForObject(sql,new BeanPropertyRowMapper<Seller>(Seller.class),sid);
    }

    @Override
    public int findFavoriteCount(int rid) {

        String sql = "select count(*) from tab_favorite where rid = ?";

        return template.queryForObject(sql,Integer.class,rid);
    }

    @Override
    public Favorite findByUidAndRid(int rid, int uid) {

        Favorite favorite = null;
        try {
            String sql = "select * from tab_favorite where rid = ?  and uid = ?";
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {

        }


        return favorite;
    }

    @Override
    public void add(int rid, int uid) {
        String sql = "insert into tab_favorite values (?,?,?)";
        template.update(sql,rid,new Date(),uid);
    }


}
