package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    private RouteService service = new RouteServiceImpl();

    /**
     * 查询当前页面信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取页面传来的参数
        String cidStr = request.getParameter("cid");
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");

        //获取rname旅游查询名称
        String rname = request.getParameter("rname");
        //更改rname的编码格式
        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");

        int cid = 0;//类别id
        if(cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {

            //cidStr有值 将cidStr转换为int型
            cid = Integer.parseInt(cidStr);
        }

        int currentPage = 0;//当前页码，如果不传递，默认为1
        if(currentPageStr != null  && currentPageStr.length() > 0) {
            //currentPageStr有值，将currentPageStr转换为int型
            currentPage = Integer.parseInt(currentPageStr);
        }else{
            currentPage = 1;
        }

        int pageSize = 0; //当前页面的记录数量，默认为5
        if(pageSizeStr != null && pageSizeStr.length() > 0 ) {
            pageSize = Integer.parseInt(pageSizeStr);
        }else{
            pageSize = 5;
        }


        //调用service中的pageQuery()方法
        PageBean<Route> routeList =  service.pageQuery(cid,currentPage,pageSize,rname);

        //将pageBean转为Json格式写回客户端
        super.writeValue(routeList,response);
    }

    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取rid
        String ridStr = request.getParameter("rid");
        //调用service方法查询页面的详细信息，并且封装在Route对象中
        Route routeDetail = service.findOne(ridStr);

        //将页面的详细信息转为Json发送到客户端
        super.writeValue(routeDetail,response);
    }

    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String rid = request.getParameter("rid");//获取旅游线路的rid

        //从session中获取用户的uid
        User user = (User) request.getSession().getAttribute("user");
        int uid;//用户id
        if(user == null){
            //用户尚未登录
            return;
        }else{
            //用户已经登录
            uid = user.getUid();
        }

        //调用service中的isFavorite方法返回一个Favorite对象
        Boolean flag = service.isFavorite(rid, uid);

        writeValue(flag,response);

    }

    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取rid旅游路线id
        String rid = request.getParameter("rid");
        //获取session中的user的uid
        User user = (User) request.getSession().getAttribute("user");
        int uid;
        if(user == null) {
            //用户尚未登陆
            return;
        }else {
            //用户已经登陆
            uid = user.getUid();
        }

        //调用service中的添加收藏方法
        service.add(rid,uid);

    }


}
