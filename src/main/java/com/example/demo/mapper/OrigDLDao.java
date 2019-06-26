package com.example.demo.mapper;

import com.example.demo.entity.Charge;
import com.example.demo.entity.OrigDL;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.example.demo.entity.Params;
import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lwx
 * @since 2019-05-08
 */
public interface OrigDLDao extends BaseMapper<OrigDL> {

    void insertOrigDL(OrigDL origDL);

    List<Map> getPowerData(Params param);

    List<Map> getPowerAnalyzeByHour(Params params);

    List<Map> getPowerAnalyzeByDay(Params params);

    List<Map> getPowerByMonthAndID(@Param("id") int id,@Param("month") String month,@Param("hours") Set<Integer> set);

    List<Map> getEMeterNum();

    List<Map> getEMeterNumByErtuID(@Param("ErtuID") int ErtuID);

    List<Map> exportPowerTotal(Params param);

    List<Map> exportAllPowerTotal(Params param);

}
