package com.example.demo.service.impl;

import com.example.demo.entity.ExportPeak;
import com.example.demo.entity.Params;
import com.example.demo.entity.PowerAnalyze;
import com.example.demo.mapper.PowerAnalyzeDao;
import com.example.demo.service.PowerAnalyzeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.util.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lwx
 * @since 2019-05-30
 */
@Service
public class PowerAnalyzeServiceImpl extends ServiceImpl<PowerAnalyzeDao, PowerAnalyze> implements PowerAnalyzeService {

    @Autowired
    private PowerAnalyzeDao powerAnalyzeDao;

    @Override
    public Map getPowerAnalyze(Params params) {
        PageInfo<Map> page = null;
        PageHelper.startPage(Integer.parseInt(params.getPageNum()), Integer.parseInt(params.getPageSize()));
        Map<String,Object> map = new HashMap<>();
        //获取数据库的map
        List<Map> maps = null;
        if(params.getDateType().equals("hour")){
            maps = powerAnalyzeDao.getPowerAnalyzeByHour(params);
        }else if(params.getDateType().equals("day")){
            maps = powerAnalyzeDao.getPowerAnalyzeByDay(params);
        }
        page = new PageInfo(maps);
        map.put("page",page);
        return map;
    }

    @Override
    public List<ExportPeak> exportAllPowerAnalyze(Params params) {
        return this.powerAnalyzeDao.exportAllPowerAnalyze(params);
    }

    @Override
    public List<ExportPeak> exportPowerByErtuID(int ErtuID, String month) {
        return this.powerAnalyzeDao.exportPowerByErtuID(ErtuID,month);
    }

    @Override
    public List<ExportPeak> exportPower(Params params) {
        List<ExportPeak> list = null;
        if (params.getDateType().equals("hour")){
            list = powerAnalyzeDao.exportPowerByHour(params);
        }else if(params.getDateType().equals("day")){
            list = powerAnalyzeDao.exportPowerByDay(params);
        }
        return list;
    }
}
