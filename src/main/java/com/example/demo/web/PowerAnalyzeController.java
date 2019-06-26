package com.example.demo.web;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.ExportPeak;
import com.example.demo.entity.Params;
import com.example.demo.entity.User;
import com.example.demo.service.PowerAnalyzeService;
import com.example.demo.util.MyException.Result;
import com.example.demo.util.MyException.ResultUtil;
import com.example.demo.util.date.DateUtil;
import com.github.pagehelper.PageInfo;
import com.wuwenze.poi.ExcelKit;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lwx
 * @since 2019-05-30
 */
@CrossOrigin
@Controller
public class PowerAnalyzeController {
    @Autowired
    private PowerAnalyzeService powerAnalyzeService;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/api/PowerPeak.do",produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    @ResponseBody
    public Result getPowerData(@RequestBody Params param) {
        logger.info(param.getEMeterName()+","+param.getBeginTime()+","+param.getEStationName()+","+param.getDateType());
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        Map page = powerAnalyzeService.getPowerAnalyze(param);
        resultMap.put("data", page);
        Result result = ResultUtil.success();
        result.setData(resultMap);
        return result;
    }

    // 生成导入模板（含3条示例数据）
    @RequestMapping(value = "/api/exportAllPowerAnalyze.do", method = RequestMethod.GET)
    public void downTemplate(String beginTime, HttpServletResponse response) {
        System.out.println(beginTime);
        Params params = new Params();
        params.setBeginTime(DateUtil.GMTStringtoDateByPattern(beginTime,"yyyy-MM-dd"));
        List<ExportPeak> list = powerAnalyzeService.exportAllPowerAnalyze(params);
        ExcelKit.$Export(ExportPeak.class, response).downXlsx(list, false);
    }

    // 生成导入模板（含3条示例数据）
    @RequestMapping(value = "/api/exportPower.do", method = RequestMethod.GET)
    public void exportPower(HttpServletRequest request, HttpServletResponse response) {
        Params params = new Params();
        String beginTime = request.getParameter("beginTime");
        params.setEMeterName(request.getParameter("emeterName"));
        params.setEStationName(request.getParameter("estationName"));
        params.setDateType(request.getParameter("dateType"));
        params.setBeginTime(DateUtil.parseGMT(beginTime));
        List<ExportPeak> list = powerAnalyzeService.exportPower(params);
        ExcelKit.$Export(ExportPeak.class, response).downXlsx(list, false);
    }


    // 生成导入模板（含3条示例数据）
    @RequestMapping(value = "/api/exportPowerByErtuID.do", method = RequestMethod.GET)
    public void exportPowerByErtuID(int ErtuID,String month, HttpServletResponse response) {
        System.out.println(ErtuID+","+month);
       // Params params = new Params();
     //   params.setBeginTime(DateUtil.GMTStringtoDateByPattern(beginTime,"yyyy-MM-dd"));
        List<ExportPeak> list = powerAnalyzeService.exportPowerByErtuID(ErtuID,month);
        ExcelKit.$Export(ExportPeak.class, response).downXlsx(list, false);
    }
}
