package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {

    /**
     * 注册用户
     * @return 注册成功返回true 否则翻译false
     */
    boolean registUser(User user);

    /**
     * 通过查询激活码是否存在，激活用户
     * @param code
     * @return
     */
    boolean active(String code);

    /**
     * 用户是否登陆成功，成功返回一个用户，否则返回null
     * @param user
     * @return
     */
    User login(User user);
}
