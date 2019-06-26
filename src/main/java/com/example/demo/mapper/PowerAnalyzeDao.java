package com.example.demo.mapper;

import com.example.demo.entity.ExportPeak;
import com.example.demo.entity.Params;
import com.example.demo.entity.PowerAnalyze;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lwx
 * @since 2019-05-30
 */
public interface PowerAnalyzeDao extends BaseMapper<PowerAnalyze> {

    void insertPowerAnalyze(PowerAnalyze powerAnalyze);

    List<Integer> getEMeterIDList();

    List<Map> getPowerAnalyzeByHour(Params params);

    List<Map> getPowerAnalyzeByDay(Params params);

    List<ExportPeak> exportAllPowerAnalyze(Params params);

    List<ExportPeak> exportPowerByErtuID(@Param("ErtuID") int ErtuID, @Param("month") String month);

    List<ExportPeak> exportPowerByHour(Params params);

    List<ExportPeak> exportPowerByDay(Params params);

}
