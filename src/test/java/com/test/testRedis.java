package com.test;

import com.example.demo.DemoApplication;
import com.example.demo.entity.Params;
import com.example.demo.mapper.OrigDLDao;
import com.example.demo.service.UtilService;
import com.example.demo.util.ChargeUtil;
import com.example.demo.util.date.DateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DemoApplication.class)
@SpringBootTest
public class testRedis {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ChargeUtil chargeUtil;

    @Autowired
    private UtilService utilService;
    @Autowired
    private OrigDLDao origDLDao;

    @Test
    public void testTemplate(){
        stringRedisTemplate.opsForValue().set("aaa","2112");
        System.out.println(stringRedisTemplate.opsForValue().get("aaa"));
    }

    @Test
    public void testCharge() throws ParseException {
        List<Map> maps = this.origDLDao.getEMeterNum();
        int i = 1;
        while (i<32){
            for (Map<String,Object> m:maps) {
                Params params = new Params();
                params.setEMeterName(m.get("EMeterName").toString());
                params.setEStationName(m.get("EStationName").toString());
                params.setEMeterID(Integer.parseInt(m.get("EMeterID").toString()));
                params.setBeginTime(DateUtil.StringToDate("2019-04-" + i + " 00:00:00"));
                chargeUtil.insertPowerList(params, "2019-4-" + i);
            }
                i++;
                try {
                    Thread.sleep(1000*10);
                    System.out.println("-------------------------------------------------------------睡眠20秒");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }

    }

    //计算峰平谷
    @Test
    public void testCharge1() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        String Time = "2019-04-01 00:00:00";
        calendar.setTime(DateUtil.StringToDate(Time));
        int i = 1;
        while (i<31){
            Params params = new Params();
            params.setEMeterName("2#快冷炉");
            params.setEStationName("同安13B");
            params.setEMeterID(303);
            params.setBeginTime(calendar.getTime());
            Time = DateUtil.DateToStringByPattern(calendar.getTime(),"yyyy-MM-dd");
            chargeUtil.insertPowerList(params,Time);
            calendar.add(Calendar.DATE,1);
            i++;
        }

        // chargeUtil.insertPowerList(params,"2019-04-01");
        //  this.chargeUtil.getPowerByTime("2019-04-01 14:30:00","2019-04-01 17:30:00",params);
    }



    @Test
    public void testPower(){
        byte[] tm = {19, 5, 1, 0, 0, 0, 23, 59, 59};
        //   byte i = 1;
        //   while (i<31){
        //    System.out.println("正在跑第"+i+"天的数据");
        String host = "10.30.33.240";
        this.utilService.AutoGetAllDataById(tm,11,700,host);
        //    i++;
        //    tm[2] = i;
        //   }
    }
}
