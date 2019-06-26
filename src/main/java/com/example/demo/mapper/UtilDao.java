package com.example.demo.mapper;



import com.example.demo.entity.Record;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lwx
 * @since 2019-03-26
 */
public interface UtilDao  {
    List<LinkedHashMap> getMenuList();

    List<LinkedHashMap> getErtusList();

    List<Record> recordList(@Param("parameter") String parameter);

    Record getTimeAndIp(@Param("geNumber") String geNumber);

    int getTotalNumber();

    int getTotalUserNumber();

    List<LinkedHashMap> getRecordNumberList();

    List<Map> getErtuIDList();

    List<Map> getEMeterNumByErtuID(int ErtuID);

    List<Map> getTotalPower();
}
