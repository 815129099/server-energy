package com.example.demo.web;


import com.example.demo.entity.Params;
import com.example.demo.entity.Record;
import com.example.demo.entity.User;
import com.example.demo.service.RecordService;
import com.example.demo.service.UtilService;
import com.example.demo.util.MyException.ExceptionHandle;
import com.example.demo.util.MyException.Result;
import com.example.demo.util.MyException.ResultUtil;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
public class UtilController {

    @Autowired
    private ExceptionHandle exceptionHandle;

    @Autowired
    private UtilService utilService;

    @Autowired
    private RecordService recordService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/api/menu.do",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result getMenuJson() {
        Object menu = utilService.getMenuJson();
        Result result = ResultUtil.success();
        result.setData(menu);
        return result;
    }

    @RequestMapping(value = "/api/ertus.do",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Result getErtusJson() {
        Object menu = utilService.getErtusJson();
        Result result = ResultUtil.success();
        result.setData(menu);
        return result;
    }

    @RequestMapping(value = "/api/RecordList.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    @ResponseBody
    public Result getRecordList(@RequestBody Params param) {
        logger.info(param.getParameter()+","+param.getPageNum()+","+param.getPageSize());
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        Page<Record> page = recordService.recordList(param.getParameter(), Integer.parseInt(param.getPageNum()), Integer.parseInt(param.getPageSize()));
        resultMap.put("recordData", page);
        resultMap.put("number", page.getTotal());
        Result result = ResultUtil.success();
        result.setData(resultMap);
        return result;
    }


    @RequestMapping(value = "/api/getTimeAndIp.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    @ResponseBody
    public Result getTimeAndIp(@RequestBody User user) {
        logger.info(user.getGeNumber());
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        //近七日用户访问记录
        resultMap.put("list",this.utilService.getRecordNumberList());
        Record record = this.utilService.getTimeAndIp(user.getGeNumber());
        resultMap.put("recordTotal",this.utilService.getTotalNumber());
        resultMap.put("userTotal",this.utilService.getTotalUserNumber());
        resultMap.put("ip",record.getIP());
        resultMap.put("time",record.getCreatedDateTime());
        Result result = ResultUtil.success();
        result.setData(resultMap);
        return result;
    }

    @RequestMapping(value = "/api/getAllData.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    @ResponseBody
    public Result getAllData() {
       this.utilService.getAllData();
        Result result = ResultUtil.success();
        return result;
    }

    @RequestMapping(value = "/api/getTotalPower.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    @ResponseBody
    public Result getTotalPower() {
        Map map = this.utilService.getTotalPower();
        Result result = ResultUtil.success();
        result.setData(map);
        return result;
    }

}
