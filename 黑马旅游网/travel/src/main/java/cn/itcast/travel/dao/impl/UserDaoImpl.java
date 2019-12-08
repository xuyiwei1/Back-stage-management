package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {


    //使用spring的jdbc执行sql操作
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据用户名查询数据库看是否存在
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {

        //这里需要trycatch一下，因为jdbcTemplate如果查询不到对象会抛异常，而我们希望查询不到返回一个null
        User user = null;
        try {
            String sql = "select * from tab_user where username = ?";

            user = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username);
        } catch (DataAccessException e) {

        }

        return user;
    }

    /**
     * 保存用户
     * @param user
     */
    @Override
    public void save(User user) {

        String sql = "insert into tab_user (username,password,name,birthday,sex,telephone,email,status,code) values (?,?,?,?,?,?,?,?,?)";

        jdbcTemplate.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getBirthday(),user.getSex(),user.getTelephone(),user.getEmail(),user.getStatus(),user.getCode());


    }

    /**
     * 根据客户端生成的激活码，查询用户是否有激活码
     * @param code
     * @return
     */

    @Override
    public User findByCode(String code) {

        User user = null;

        try {
            String sql = "select * from tab_user where code = ?";
            user = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return user;
    }


    /**
     * 修改用户激活状态
     * @param user
     */
    @Override
    public void updateStatus(User user) {
        String sql = "update tab_user set status = 'Y' where uid = ?";
        jdbcTemplate.update(sql,user.getUid());
    }

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    @Override
    public User findByUsernameAndPassword(String username, String password) {
        User user = null;
        try {
            String sql = "select * from tab_user where username = ? and password = ?";

            user = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username,password);
        } catch (DataAccessException e) {

        }

        return user;
    }
}
