package com.example.demo.util.jna.po;

import com.sun.jna.Structure;

import java.util.Arrays;
import java.util.List;

public class S_CLOU102_UNPACK extends Structure {
    public byte address;// 子站地址（报文第6字节）
    //unsigned char* time;// 时间
    public String time;// 时间
    public byte infoObjNum;// 信息体数量
    public byte powerTypeNum;// 功率类型数量
    public byte powerTariffNum;// 费率数量
    public long meternum;// 表号
    public String infoType;// 信息体类型
    public long framenum;// 帧号
    /*char* power1[64][30];// 返回总电量数据，长度最长的应为时间，电量数字长度小于时间*/
    public POWER power;// 返回总电量数据
    public INSTANTANEOUS_VALUE instantaneous_value;

    public static class ByReference extends S_CLOU102_UNPACK implements Structure.ByReference { }
    public static class ByValue  extends S_CLOU102_UNPACK  implements Structure.ByValue { }

    @Override
    protected List getFieldOrder() {
        return Arrays.asList(new String[]{"address", "time", "infoObjNum", "powerTypeNum","powerTariffNum",
                "meternum","infoType","framenum","power","instantaneous_value"});
    }
}
