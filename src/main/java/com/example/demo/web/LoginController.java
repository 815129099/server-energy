package com.example.demo.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;


import com.example.demo.entity.Record;
import com.example.demo.entity.User;
import com.example.demo.service.RecordService;
import com.example.demo.service.UserService;
import com.example.demo.util.MyException.ExceptionHandle;
import com.example.demo.util.MyException.Result;
import com.example.demo.util.MyException.ResultUtil;
import com.example.demo.util.shiro.NetworkUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author lwx
 * @since 2019-03-21
 */
@CrossOrigin
@Controller
public class LoginController {

    @Autowired
    private ExceptionHandle exceptionHandle;

    @Autowired
    private UserService userService;

    @Autowired
    private RecordService recordService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = "/api/login", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result loginByNumber(@RequestBody User user, HttpServletRequest request) {
        Result result = ResultUtil.success();
        logger.info("登录:" + user.getGeNumber() + "pa:" + user.getPassword());
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(true);
        Serializable sessionId = session.getId();
        logger.info(sessionId.toString());
        UsernamePasswordToken token = new UsernamePasswordToken(user.getGeNumber(), user.getPassword());
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            result.setMsg("账号不存在");
            return result;
        } catch (DisabledAccountException e) {
            result.setMsg("账号未启用");
            return result;
        } catch (IncorrectCredentialsException e) {
            result.setMsg("密码错误");
            return result;
        } catch (Exception e) {
            result.setMsg("未知错误");
            return result;
        }
        Record record = new Record();
        record.setIP(NetworkUtil.getIpAddr(request));
        record.setUserName(user.getGeNumber());
        record.setSessionId(sessionId.toString());
        recordService.insert(record);
        result.setMsg("success");
        result.setRole(new ArrayList(userService.findRoles(user.getGeNumber())).get(0).toString());
        token.setHost(NetworkUtil.getIpAddr(request));
        result.setData(token);
        return result;
    }

    @RequestMapping("/api/unauth")
    @ResponseBody
    public Result unauth() {
        Result result = ResultUtil.success();
        result.setMsg("未登录");
        result.setStatus(100);
        return result;
    }

    @RequestMapping({"/loginByPhone.do"})
    @ResponseBody
    public Result loginByPhone(String phone, String code, HttpServletRequest request) {
        Result result = ResultUtil.success();
        Map<String, Object> map = new HashMap();
        logger.info("登录:" + phone + code);
        EntityWrapper<User> userWrapper = new EntityWrapper<User>();
        userWrapper.eq("phone", phone).eq("code", code);
        User user = userService.selectOne(userWrapper);
        if (StringUtils.isEmpty(user)) {
            map.put("tip", "验证码错误");
            map.put("code", 2);
            result.setData(map);
            return result;
        } else {
            Subject subject = SecurityUtils.getSubject();
            //Session session = subject.getSession(true);
            // Serializable sessionId = session.getId();
            // System.out.println(sessionId.toString());
            UsernamePasswordToken token = new UsernamePasswordToken(user.getGeNumber(), user.getPassword());
            try {
                subject.login(token);
            } catch (UnknownAccountException e) {
                map.put("tip", "账号不存在");
                map.put("code", 1);
                result.setData(map);
                return result;
            } catch (DisabledAccountException e) {
                map.put("tip", "账号未启用");//
                map.put("code", 1);
                result.setData(map);
                return result;
            } catch (IncorrectCredentialsException e) {
                map.put("tip", "密码错误");
                map.put("code", 1);
                result.setData(map);
                return result;
            } catch (Exception e) {
                map.put("tip", "未知错误");
                map.put("code", 1);
                result.setData(map);
                return result;
            }

            //AccessRecord accessRecord = new AccessRecord();
            //accessRecord.setIpNumber(NetworkUtil.getIpAddr(request));
            //accessRecord.setGeNumber(geNumber);
            // accessRecord.setSessionId(sessionId.toString());
            //this.utilService.addAccess(accessRecord);
            map.put("tip", "success");
            result.setData(map);
            return result;
        }
    }

    /**
     * 退出
     *
     * @return
     */
    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "testLogin";
    }

    @RequestMapping("/getCode.do")
    @ResponseBody
    @Transactional
    public Result getCode(String phone) {
        Result result = ResultUtil.success();
        try {
            logger.info("phone:" + phone);
            Map<String, Object> map = new HashMap();
            boolean isSuccess = userService.getCode(phone);
            if (isSuccess) {
                map.put("tip", "success");
                result.setData(map);
            } else {
                map.put("tip", "error");
                result.setData(map);
            }
        } catch (Exception e) {
            result = exceptionHandle.exceptionGet(e);
            logger.error("出现错误");
        }
        return result;
    }

    @RequestMapping("/checkPhone.do")
    @ResponseBody
    public Result checkPhone(String phone) {
        Result result = ResultUtil.success();
        try {
            logger.info("phone:" + phone);
            Map<String, Object> map = new HashMap();
            EntityWrapper<User> userWrapper = new EntityWrapper<User>();
            userWrapper.eq("phone", phone);
            User user = userService.selectOne(userWrapper);
            if (StringUtils.isEmpty(user)) {
                map.put("tip", "false");
            } else {
                map.put("tip", "true");
            }
            result.setData(map);
        } catch (Exception e) {
            result = exceptionHandle.exceptionGet(e);
            logger.error("出现错误");
        }
        return result;
    }

    @RequestMapping("/resetPassword.do")
    @ResponseBody
    @Transactional
    public Result resetPassword(String phone) {
        Result result = ResultUtil.success();
        try {
            logger.info("phone:" + phone);
            Map<String, Object> map = new HashMap();
            boolean isSuccess = userService.resetPassword(phone);
            if (isSuccess) {
                map.put("tip", "success");
                result.setData(map);
            } else {
                map.put("tip", "error");
                result.setData(map);
            }
        } catch (Exception e) {
            result = exceptionHandle.exceptionGet(e);
            logger.error("出现错误");
        }
        return result;
    }

    /*
    @Autowired
    private UserService userService;

    @GetMapping("/show")
    @ResponseBody
    public JSONObject testUser() {
        Page<User> users = userService.selectPage(new Page<>());
        JSONObject object = new JSONObject();
        object.put("users",users);
        return object;
    }

    @Autowired
    private ExceptionHandle exceptionHandle;
*/
    /**
     * 返回体测试
     * @param name
     * @param pwd
     * @return

     @RequestMapping(value = "/getResult",method = RequestMethod.POST)
     @ResponseBody public Result getResult(@RequestParam("name") String name, @RequestParam("pwd") String pwd){
     Result result = ResultUtil.success();
     try {
     if (name.equals("zzp")){
     result =  ResultUtil.success(new Netapi32Util.UserInfo());
     logger.info("用户名正确");
     }else if (name.equals("pzz")){
     result =  ResultUtil.error(ExceptionEnum.USER_NOT_FIND);
     logger.info("用户名错误");
     }else{
     int i = 1/0;
     }
     }catch (Exception e){
     result =  exceptionHandle.exceptionGet(e);
     logger.error("出现错误");
     }
     return result;
     }
     */


}
