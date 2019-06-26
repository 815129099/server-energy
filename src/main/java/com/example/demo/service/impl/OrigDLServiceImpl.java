package com.example.demo.service.impl;

import com.example.demo.entity.ExportPower;
import com.example.demo.entity.OrigDL;
import com.example.demo.entity.Params;
import com.example.demo.mapper.OrigDLDao;
import com.example.demo.service.OrigDLService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.example.demo.util.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lwx
 * @since 2019-05-08
 */
@Service
public class OrigDLServiceImpl extends ServiceImpl<OrigDLDao, OrigDL> implements OrigDLService {

    @Autowired
    private OrigDLDao origDLDao;

    @Override
    public List<ExportPower> exportPowerTotal(Params params) {
        List<Map> maps = origDLDao.exportPowerTotal(params);
        ExportPower exportPower = new ExportPower();
        if(maps.size()>0){
            Map beginMap = maps.get(0);
            Map endMap = maps.get(maps.size()-1);
            Double beginNumber =0.0;
            Double endNumber = 0.0;
            if(params.getPowerType().equals("ZxygZ")){
                beginNumber = Double.valueOf(beginMap.get("ZxygZ").toString());
                endNumber = Double.valueOf(endMap.get("ZxygZ").toString());
            }else if(params.getPowerType().equals("FxygZ")){
                beginNumber = Double.valueOf(beginMap.get("FxygZ").toString());
                endNumber = Double.valueOf(endMap.get("FxygZ").toString());
            }else if(params.getPowerType().equals("ZxwgZ")){
                beginNumber = Double.valueOf(beginMap.get("ZxwgZ").toString());
                endNumber = Double.valueOf(endMap.get("ZxwgZ").toString());
            }else if(params.getPowerType().equals("FxwgZ")){
                beginNumber = Double.valueOf(beginMap.get("FxwgZ").toString());
                endNumber = Double.valueOf(endMap.get("FxwgZ").toString());
            }
            exportPower.setBeginNumber(beginNumber);
            exportPower.setEndNumber(endNumber);
            int MultiplyRatio = Integer.parseInt(beginMap.get("MultiplyRatio").toString());
            Double num;
            if(MultiplyRatio==1){
                num = Double.valueOf(beginMap.get("num").toString());
            }else {
                num = 1.0;
            }
            exportPower.setNum(num);
            exportPower.setDifValue(endNumber-beginNumber);
            exportPower.setPowerTotal((endNumber-beginNumber)*num);
            exportPower.setEMeterName(beginMap.get("EMeterName").toString());
            exportPower.setBeginTime(DateUtil.DateToString(params.getBeginTime()));
            exportPower.setEndTime(DateUtil.DateToString(params.getEndTime()));
        }
        List<ExportPower> list = new ArrayList<>();
        list.add(exportPower);
        return list;
    }

    @Override
    public PageInfo<Map> getPowerData(Params param) {
        Map<String,Object> map = new HashMap();
        List<Map> maps = origDLDao.getPowerData(param);
        PageInfo<Map> page = null;
        if(maps.size()>0){
            Map beginMap = maps.get(0);
            Map endMap = maps.get(maps.size()-1);
            Double beginNumber =0.0;
            Double endNumber = 0.0;
            if(param.getPowerType().equals("ZxygZ")){
                beginNumber = Double.valueOf(beginMap.get("ZxygZ").toString());
                endNumber = Double.valueOf(endMap.get("ZxygZ").toString());
            }else if(param.getPowerType().equals("FxygZ")){
                beginNumber = Double.valueOf(beginMap.get("FxygZ").toString());
                endNumber = Double.valueOf(endMap.get("FxygZ").toString());
            }else if(param.getPowerType().equals("ZxwgZ")){
                beginNumber = Double.valueOf(beginMap.get("ZxwgZ").toString());
                endNumber = Double.valueOf(endMap.get("ZxwgZ").toString());
            }else if(param.getPowerType().equals("FxwgZ")){
                beginNumber = Double.valueOf(beginMap.get("FxwgZ").toString());
                endNumber = Double.valueOf(endMap.get("FxwgZ").toString());
            }
            //MultiplyRatio是否乘变比
            System.out.println(beginMap.get("MultiplyRatio").toString());
            int MultiplyRatio = Integer.parseInt(beginMap.get("MultiplyRatio").toString());
            Double num;
            if(MultiplyRatio==1){
                num = Double.valueOf(beginMap.get("num").toString());
            }else {
                num = 1.0;
            }
            map.put("beginNumber",beginNumber);
            map.put("endNumber",endNumber);
            map.put("difValue",String.format("%.6f",endNumber-beginNumber));
            //倍率
            map.put("num",num);
            map.put("PowerTotal",String.format("%.6f",(endNumber-beginNumber)*num));
            map.put("EMeterName",param.getEMeterName());
            map.put("beginTime", DateUtil.DateToString(param.getBeginTime()));
            map.put("endTime",DateUtil.DateToString(param.getEndTime()));
            PageHelper.startPage(Integer.parseInt(param.getPageNum()), Integer.parseInt(param.getPageSize()));
            List<Map> list = new ArrayList<>();
            list.add(map);
            page = new PageInfo(list);
        }
        return page;
    }

    @Override
    public Map getPowerAnalyze(Params params) {
        PageInfo<Map> page = null;
        PageHelper.startPage(Integer.parseInt(params.getPageNum()), Integer.parseInt(params.getPageSize()));
        Map<String,Object> map = new HashMap<>();
        //获取数据库的map
        List<Map> maps = null;
        //图表数据
        List<Map> chartList = new ArrayList<Map>();
        List<LinkedHashMap> newMaps = new ArrayList<LinkedHashMap>();
        if(params.getDateType().equals("hour")){
            maps = origDLDao.getPowerAnalyzeByHour(params);
            for(int i=0;i<maps.size()-1;i++){
                //用于数据显示
                LinkedHashMap map1 = new LinkedHashMap();
                String Time = maps.get(i).get("Time").toString();
                map1.put("Time", Time);
                int MultiplyRatio = Integer.parseInt(maps.get(i).get("MultiplyRatio").toString());
                Double num;
                if(MultiplyRatio==1){
                    num = Double.valueOf(maps.get(i).get("num").toString());
                }else {
                    num = 1.0;
                }
                map1.put("num",num);
                map1.put("beginTime",DateUtil.DateToString((Date) maps.get(i).get("TimeTag")));
                map1.put("endTime",DateUtil.DateToString((Date)maps.get(i+1).get("TimeTag")));
                Double beginNumber = 0.0,endNumber = 0.0;
                if(params.getPowerType().equals("ZxygZ")){
                     beginNumber = Double.valueOf(maps.get(i).get("ZxygZ").toString());
                     endNumber = Double.valueOf(maps.get(i+1).get("ZxygZ").toString());
                }else if(params.getPowerType().equals("FxygZ")){
                     beginNumber = Double.valueOf(maps.get(i).get("FxygZ").toString());
                     endNumber = Double.valueOf(maps.get(i+1).get("FxygZ").toString());
                }else if(params.getPowerType().equals("ZxwgZ")){
                     beginNumber = Double.valueOf(maps.get(i).get("ZxwgZ").toString());
                     endNumber = Double.valueOf(maps.get(i+1).get("ZxwgZ").toString());
                }else if(params.getPowerType().equals("FxwgZ")){
                     beginNumber = Double.valueOf(maps.get(i).get("FxwgZ").toString());
                     endNumber = Double.valueOf(maps.get(i+1).get("FxwgZ").toString());
                }
                map1.put("beginNumber",beginNumber);
                map1.put("endNumber",endNumber);
                int totalNumber = (int)((endNumber-beginNumber)*num);
                map1.put("totalNumber",totalNumber);
                newMaps.add(i,map1);
                //用于图表显示
                Map<String ,Object> map2 = new LinkedHashMap<>();
                map2.put("name",Time);
                map2.put("value",totalNumber);
                chartList.add(i,map2);
            }
        }else if(params.getDateType().equals("day")){
            maps = origDLDao.getPowerAnalyzeByDay(params);
            for(int i=0;i<maps.size()-1;i++){
                //用于数据显示
                LinkedHashMap map1 = new LinkedHashMap();
                String Time = maps.get(i).get("Time").toString();
                map1.put("Time", Time);
                int MultiplyRatio = Integer.parseInt(maps.get(i).get("MultiplyRatio").toString());
                Double num;
                if(MultiplyRatio==1){
                    num = Double.valueOf(maps.get(i).get("num").toString());
                }else {
                    num = 1.0;
                }
                map1.put("num",num);
                map1.put("beginTime",DateUtil.DateToString((Date) maps.get(i).get("TimeTag")));
                map1.put("endTime",DateUtil.DateToString((Date)maps.get(i+1).get("TimeTag")));
                Double beginNumber = 0.0,endNumber = 0.0;
                if(params.getPowerType().equals("ZxygZ")){
                    beginNumber = Double.valueOf(maps.get(i).get("ZxygZ").toString());
                    endNumber = Double.valueOf(maps.get(i+1).get("ZxygZ").toString());
                }else if(params.getPowerType().equals("FxygZ")){
                    beginNumber = Double.valueOf(maps.get(i).get("FxygZ").toString());
                    endNumber = Double.valueOf(maps.get(i+1).get("FxygZ").toString());
                }else if(params.getPowerType().equals("ZxwgZ")){
                    beginNumber = Double.valueOf(maps.get(i).get("ZxwgZ").toString());
                    endNumber = Double.valueOf(maps.get(i+1).get("ZxwgZ").toString());
                }else if(params.getPowerType().equals("FxwgZ")){
                    beginNumber = Double.valueOf(maps.get(i).get("FxwgZ").toString());
                    endNumber = Double.valueOf(maps.get(i+1).get("FxwgZ").toString());
                }
                map1.put("beginNumber",beginNumber);
                map1.put("endNumber",endNumber);
                int totalNumber = (int)((endNumber-beginNumber)*num);
                map1.put("totalNumber",totalNumber);
                newMaps.add(i,map1);
                //用于图表显示
                Map<String ,Object> map2 = new LinkedHashMap<>();
                map2.put("name",Time);
                map2.put("value",totalNumber);
                chartList.add(i,map2);
            }
        }

        page = new PageInfo(newMaps);
        map.put("page",page);
        map.put("chartList",chartList);
        return map;
    }



    @Override
    public List<ExportPower> exportAllPowerTotal(Params params) {
        List<Map> maps = origDLDao.exportAllPowerTotal(params);
        List<ExportPower> list = new ArrayList<>();
        for (int i=0;i<maps.size();i=i+2){
            if(maps.get(i).get("EMeterID").equals(maps.get(i+1).get("EMeterID"))){
                ExportPower exportPower = new ExportPower();
                exportPower.setBeginTime(maps.get(i).get("TimeTag").toString());
                exportPower.setEndTime(maps.get(i+1).get("TimeTag").toString());

                Double beginNumber = 0.0,endNumber = 0.0;
                if(params.getPowerType().equals("ZxygZ")){
                    beginNumber = Double.valueOf(maps.get(i).get("ZxygZ").toString());
                    endNumber = Double.valueOf(maps.get(i+1).get("ZxygZ").toString());
                }else if(params.getPowerType().equals("FxygZ")){
                    beginNumber = Double.valueOf(maps.get(i).get("FxygZ").toString());
                    endNumber = Double.valueOf(maps.get(i+1).get("FxygZ").toString());
                }else if(params.getPowerType().equals("ZxwgZ")){
                    beginNumber = Double.valueOf(maps.get(i).get("ZxwgZ").toString());
                    endNumber = Double.valueOf(maps.get(i+1).get("ZxwgZ").toString());
                }else if(params.getPowerType().equals("FxwgZ")){
                    beginNumber = Double.valueOf(maps.get(i).get("FxwgZ").toString());
                    endNumber = Double.valueOf(maps.get(i+1).get("FxwgZ").toString());
                }
                exportPower.setBeginNumber(beginNumber);
                exportPower.setEndNumber(endNumber);
                exportPower.setDifValue(endNumber-beginNumber);
                Double num;
                int MultiplyRatio = Integer.parseInt(maps.get(i).get("MultiplyRatio").toString());
                if(MultiplyRatio==1){
                    num = Double.valueOf(maps.get(i).get("num").toString());
                }else {
                    num = 1.0;
                }
                exportPower.setNum(num);
                exportPower.setPowerTotal((endNumber-beginNumber)*num);
                exportPower.setEMeterName(maps.get(i).get("EMeterName").toString());
                list.add(exportPower);
            }
        }
        return list;
    }
}
