package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    @Override
    public boolean registUser(User user) {

        User u = userDao.findByUsername(user.getUsername());
        if(u == null) {
            //用户名不存在，可以注册用户
            //给新注册的用户添加激活码 UuidUtil可以根据计算机的硬件和时间生成唯一的字符串
            user.setCode(UuidUtil.getUuid());
            //设置激活状态未激活
            user.setStatus("N");
            //注册用户
            userDao.save(user);

            //发送激活邮件，邮件正文
            String context = "<a href='http://localhost/travel/user/active?code="+user.getCode()+"'>点击激活黑马旅游网</a>";

            MailUtils.sendMail("xuyiwei511@126.com",context,"黑马旅游网的激活邮件");
            return true;
        }else {
            //用户名已经存在，注册失败
            return false;
        }


    }

    @Override
    public boolean active(String code) {

        User user = userDao.findByCode(code);
        if(user != null) {
            //用户有激活码可以被激活
            //调用修改激活状态方法
            userDao.updateStatus(user);
            return true;
        }else {
            //用户没有激活码，不可被激活
            return false;
        }

    }

    @Override
    public User login(User user) {

        return userDao.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
