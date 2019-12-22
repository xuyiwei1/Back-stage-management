package com.itheima.ssm.service.impl;

        import com.itheima.ssm.dao.IUserDao;
        import com.itheima.ssm.domain.Role;
        import com.itheima.ssm.domain.UserInfo;
        import com.itheima.ssm.service.IUserService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.security.core.authority.SimpleGrantedAuthority;
        import org.springframework.security.core.userdetails.User;
        import org.springframework.security.core.userdetails.UserDetails;
        import org.springframework.security.core.userdetails.UsernameNotFoundException;
        import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
        import org.springframework.stereotype.Service;
        import org.springframework.transaction.annotation.Transactional;

        import java.util.ArrayList;
        import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * 用户登陆
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用dao查询用户信息
        UserInfo userInfo = userDao.findUserByName(username);

        //创建一个UserDetails的子类，通过构造赋予用户权限和信息,noop表示该密码是加密密码,如果在spring-security.xml中配置了加密和加密方式就不用写noop了
        User user = new User(userInfo.getUsername(),userInfo.getPassword(),userInfo.getStatus() == 1?true:false,true,true,true,getAuthority(userInfo.getRoles()));

        return user;
    }

    /**
     * 给用户添加角色
     * @param roles
     * @return
     */
    public List<SimpleGrantedAuthority> getAuthority(List<Role> roles){
        List<SimpleGrantedAuthority> list = new ArrayList<SimpleGrantedAuthority>();
        for(Role role:roles) {
            list.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
        }

        return list;
    }

    /**
     * 查询所有用户信息
     * @return
     */
    @Override
    public List<UserInfo> findAll() {
        return userDao.findAll();
    }

    /**
     * 添加用户信息
     * @param userInfo
     */
    @Override
    public void save(UserInfo userInfo) {

        //给密码加密
        String password = bCryptPasswordEncoder.encode(userInfo.getPassword());
        userInfo.setPassword(password);

        userDao.save(userInfo);
    }

    /**
     * 根据用户id查询用户的所有信息，包括角色信息和权限信息
     * @param id
     * @return
     */
    @Override
    public UserInfo findById(String id) {
        return userDao.findById(id);
    }

    /**
     * 查询用户还可以添加的角色
     * @param userId
     * @return
     */
    @Override
    public List<Role> findUserByIdAndAllRole(String userId) {
        return userDao.findUserByIdAndAllRole(userId);
    }

    /**
     * 给用户添加角色
     * @param userId
     * @param roleIds
     */
    @Override
    public void addRoleToUser(String userId, String[] roleIds) {

        //遍历所有使用这选择的角色集合
        for (String roleId : roleIds) {
            userDao.addRoleToUser(userId,roleId);
        }

    }
}
