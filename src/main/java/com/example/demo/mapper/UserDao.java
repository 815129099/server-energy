package com.example.demo.mapper;

import com.example.demo.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lwx
 * @since 2019-03-26
 */
public interface UserDao extends BaseMapper<User> {

    Set<String> findRoles(String geNumber);

    Set<String> findPermissions(String geNumber);

    User getUserByNumber(String geNumber);

    List<User> userList(@Param("parameter") String parameter);

}
