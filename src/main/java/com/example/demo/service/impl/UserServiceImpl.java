package com.example.demo.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserDao;
import com.example.demo.service.UserService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.util.Message.SendMessageUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lwx
 * @since 2019-03-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Set<String> findPermissions(String geNumber) {
        return this.userDao.findPermissions(geNumber);
    }

    @Override
    public Set<String> findRoles(String geNumber) {
        return this.userDao.findRoles(geNumber);
    }

    @Override
    public User getUserByNumber(String geNumber) {
        return this.userDao.getUserByNumber(geNumber);
    }

    @Override
    @Transactional
    public boolean getCode(String phone) {
        boolean isSuccess = false;
        User user = new User();
        String code = SendMessageUtil.getRandomCode(6);
        user.setCode(code);
        //SendMessageUtil.send(phone,code);
        userDao.update(user,new EntityWrapper<User>().eq("phone",phone));
        isSuccess = true;
        return isSuccess;
    }

    @Override
    @Transactional
    public boolean resetPassword(String phone) {
        boolean isSuccess = false;
        User user = new User();
        String password = SendMessageUtil.getRandomCode(8);
        user.setPassword(password);
        //SendMessageUtil.send(phone,code);
        userDao.update(user,new EntityWrapper<User>().eq("phone",phone));
        isSuccess = true;
        return isSuccess;
    }

    @Override
    public Page<User> userList(String parameter, int pageNum, int pageSize) {
        Page<User> page = PageHelper.startPage(pageNum, pageSize);
        userDao.userList(parameter);
        return page;
    }

    @Override
    @Transactional
    public boolean updateUser(User user) {
        boolean isSuccess = false;
        userDao.update(user,new EntityWrapper<User>().eq("geNumber",user.getGeNumber()));
        isSuccess = true;
        return isSuccess;
    }

    @Override
    @Transactional
    public boolean deleteUser(User user) {
        boolean isSuccess = false;
        user.setUserState("删除");
        userDao.update(user,new EntityWrapper<User>().eq("geNumber",user.getGeNumber()));
        isSuccess = true;
        return isSuccess;
    }

    @Override
    @Transactional
    public boolean addUser(User user) {
        boolean isSuccess = false;
        userDao.insert(user);
        isSuccess = true;
        return isSuccess;
    }
}
