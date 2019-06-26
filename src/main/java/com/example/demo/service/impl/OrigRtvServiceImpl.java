package com.example.demo.service.impl;

import com.example.demo.entity.OrigRtv;
import com.example.demo.entity.Params;
import com.example.demo.mapper.OrigRtvDao;
import com.example.demo.service.OrigRtvService;
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
 * @since 2019-05-08
 */
@Service
public class OrigRtvServiceImpl extends ServiceImpl<OrigRtvDao, OrigRtv> implements OrigRtvService {

    @Autowired
    private OrigRtvDao origRtvDao;

    @Override
    public Map getInstantPowerAnalyze(Params params) {
        PageInfo<Map> page = null;
        PageHelper.startPage(Integer.parseInt(params.getPageNum()), Integer.parseInt(params.getPageSize()));
        Map<String,Object> map = new HashMap<>();
        //获取数据库的map
        List<Map> maps = null;
        //图表数据
        List<Map> chartList = new ArrayList<Map>();
        List<LinkedHashMap> newMaps = new ArrayList<LinkedHashMap>();
        if(params.getDateType().equals("day")){
            maps = origRtvDao.getInstantPowerAnalyzeByDay(params);
            for(int i=0;i<maps.size()-1;i++){
                //用于数据显示
                chartList.add(i,maps.get(i));
            }
        }else if(params.getDateType().equals("week")){
            maps = origRtvDao.getInstantPowerAnalyzeByWeek(params);
            for(int i=0;i<maps.size()-1;i++){
                //用于数据显示
                chartList.add(i,maps.get(i));
            }
        }

        page = new PageInfo(chartList);
        map.put("page",page);
        map.put("chartList",chartList);
        return map;
    }
}
