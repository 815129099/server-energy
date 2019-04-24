package com.example.demo.web;


import com.example.demo.service.UtilService;
import com.example.demo.util.MyException.ExceptionHandle;
import com.example.demo.util.MyException.Result;
import com.example.demo.util.MyException.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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
public class UtilController {

    @Autowired
    private ExceptionHandle exceptionHandle;

    @Autowired
    private UtilService utilService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/api/menu.do",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result getMenuJson() {
        Object menu = utilService.getMenuJson();
        Result result = ResultUtil.success();
        result.setData(menu);
        return result;
    }


}
