package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    User findByUsername(String username);


    /**
     * 保存用户
     * @param user
     */
    void save(User user);

    /**
     * 根据激活码查询用户
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 更新用户的激活状态
     * @param user
     */
    void updateStatus(User user);

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String username, String password);
}
