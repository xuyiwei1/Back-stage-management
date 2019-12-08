package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*") //操作目录
public class CategoryServlet extends BaseServlet {

    private CategoryService service = new CategoryServiceImpl();

    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.调用查询所有目录的方法
        List<Category> categories = service.findAll();
        //2.将查询结果转换为Json写回页面
        super.writeValue(categories,response);

    }


}
