package com.example.demo.service;

import com.example.demo.entity.User;
import com.baomidou.mybatisplus.service.IService;
import com.github.pagehelper.Page;

import java.util.Set;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lwx
 * @since 2019-03-26
 */
public interface UserService extends IService<User> {
    Set<String> findPermissions(String geNumber);

    Set<String> findRoles(String geNumber);

    User getUserByNumber(String geNumber);

    boolean getCode(String phone);

    boolean resetPassword(String phone);

    Page<User> userList(String parameter, int pageNum, int pageSize);

    boolean updateUser(User user);

    boolean deleteUser(User user);

    boolean addUser(User user);





}
