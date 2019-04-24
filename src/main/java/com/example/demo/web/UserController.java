package com.example.demo.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.demo.entity.Param;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.MyException.ExceptionHandle;
import com.example.demo.util.MyException.Result;
import com.example.demo.util.MyException.ResultUtil;
import com.github.pagehelper.Page;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lwx
 * @since 2019-03-21
 */
@CrossOrigin
@Controller
public class UserController {

    @Autowired
    private ExceptionHandle exceptionHandle;

    @Autowired
    private UserService userService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/api/userList.do",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result getUserList(@RequestBody Param param) {
        logger.info(param.getParameter()+","+param.getPageNum()+","+param.getPageSize());
        HashMap<String, Object> studentMap = new HashMap<String, Object>();
        Page<User> page = userService.userList(param.getParameter(), Integer.parseInt(param.getPageNum()), Integer.parseInt(param.getPageSize()));
        studentMap.put("userData", page);
        studentMap.put("number", page.getTotal());
        Result result = ResultUtil.success();
        result.setData(studentMap);
        return result;
    }

    @RequestMapping(value = "/api/User.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.PUT})
    @ResponseBody
    public Result updateUser(@RequestBody User user) {
        logger.info(user.getGeNumber()+","+user.getUserState());
        boolean isSuccess = userService.updateUser(user);
        Result result = ResultUtil.success();
        result.setData(isSuccess);
        return result;
    }

    @RequestMapping(value = "/api/User.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.DELETE})
    @ResponseBody
    public Result deleteUser(@RequestBody User user) {
        logger.info(user.getGeNumber());
        boolean isSuccess = userService.deleteUser(user);
        Result result = ResultUtil.success();
        result.setData(isSuccess);
        return result;
    }

    @RequestMapping(value = "/api/User.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    @ResponseBody
    public Result addUser(@RequestBody User user) {
        logger.info(user.getGeNumber()+","+user.getUserState());
        boolean isSuccess = userService.addUser(user);
        Result result = ResultUtil.success();
        result.setData(isSuccess);
        return result;
    }
}
