package com.example.demo.service;

import com.example.demo.entity.Record;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lwx
 * @since 2019-03-26
 */
public interface UtilService  {

    Object getMenuJson();

    Object getErtusJson();

    Record getTimeAndIp(String geNumber);

    int getTotalNumber();

    int getTotalUserNumber();

    Map getRecordNumberList();

    boolean getAllData();

   boolean AutoGetAllData();

    void AutoGetAllDataById(byte[] tm, int i, int i1, String host);

    Map getTotalPower();


}
