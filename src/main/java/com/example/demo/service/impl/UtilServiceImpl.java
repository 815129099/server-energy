package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Record;
import com.example.demo.entity.TreeNode;
import com.example.demo.mapper.UtilDao;
import com.example.demo.service.UtilService;
import com.example.demo.util.date.DateUtil;
import com.example.demo.util.jna.tcp.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lwx
 * @since 2019-03-26
 */
@Service
public class UtilServiceImpl implements UtilService {

    @Autowired
    private UtilDao utilDao;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private Client client;

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object getMenuJson() {
        //   if(redisTemplate.hasKey("menuList")){
        //       redisTemplate.delete("menuList");
        //     }
        Object o;
        //   if(redisTemplate.opsForValue().get("menuList")==null) {
        List<LinkedHashMap> menuList = utilDao.getMenuList();
        //用于判断是否已存在当前节点
        List<Object> strList = new ArrayList<Object>();
        List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
        //遍历menuList
        for (Map<String, Object> m : menuList) {
            //遍历map，并创建treeNode
            for (String k : m.keySet()) {
                //该节点不在节点树
                if (!strList.contains(m.get(k))) {
                    if (k.equals("FactoryName")) {
                        //创建节点
                        TreeNode treeNode = new TreeNode();
                        treeNode.setIcon("el-icon-menu");
                        treeNode.setPath("/" + m.get(k).toString());
                        // treeNode.setPath("/"+(treeNodeList.size()+1));
                        treeNode.setName(m.get(k).toString());
                        //把节点拼到节点树上
                        treeNodeList.add(treeNode);
                        //放到已存在节点表里
                        strList.add(m.get(k));
                    } else if (k.equals("EStationName")) {
                        //创建节点
                        TreeNode treeNode = new TreeNode();
                        treeNode.setIcon("el-icon-menu");
                        treeNode.setName(m.get(k).toString());
                        treeNode.setPath("/" + m.get(k).toString());
                        //把节点拼到节点树上
                        for (TreeNode t : treeNodeList) {
                            if (t.getName().equals(m.get("FactoryName"))) {
                                if (StringUtils.isEmpty(t.getChildren())) {
                                    // treeNode.setPath("/"+(treeNodeList.size()+1)+"/"+1);
                                    List<TreeNode> list = new ArrayList<TreeNode>();
                                    list.add(treeNode);
                                    t.setChildren(list);
                                } else {
                                    // treeNode.setPath("/"+(treeNodeList.size()+1)+"/"+(t.getChildren().size()+1));
                                    t.getChildren().add(treeNode);
                                }
                            }
                        }
                        //放到已存在节点表里
                        strList.add(m.get(k));
                    } else if (k.equals("EMeterName")) {
                        //创建节点
                        TreeNode treeNode = new TreeNode();
                        treeNode.setIcon("el-icon-menu");
                        treeNode.setPath("/" + m.get(k).toString());
                        //  treeNode.setPath("/"+treeNodeList.size()+1);
                        treeNode.setName(m.get(k).toString());
                        //把节点拼到节点树上
                        for (TreeNode t : treeNodeList) {
                            if (t.getName().equals(m.get("FactoryName"))) {
                                for (TreeNode t1 : t.getChildren()) {
                                    if (t1.getName().equals(m.get("EStationName"))) {
                                        if (StringUtils.isEmpty(t1.getChildren())) {
                                            // treeNode.setPath("/"+(treeNodeList.size()+1)+"/"+(t.getChildren().size()+1)+"/"+1);
                                            List<TreeNode> list = new ArrayList<TreeNode>();
                                            list.add(treeNode);
                                            t1.setChildren(list);
                                        } else {
                                            //treeNode.setPath("/"+(treeNodeList.size()+1)+"/"+(t.getChildren().size()+1)+"/"+(t.getChildren().size()+1));
                                            t1.getChildren().add(treeNode);
                                        }
                                    }
                                }
                            }
                        }
                        //放到已存在节点表里
                        strList.add(m.get(k).toString() + m.get("EStationName").toString());
                    }
                }
            }
        }
        Object jsonObject = JSONObject.toJSON(treeNodeList);
        o = jsonObject;
        //   redisTemplate.opsForValue().set("menuList",jsonObject);
        //  }else {
        //      o = redisTemplate.opsForValue().get("menuList");
        //  }
        return o;
    }


    @Override
    public Object getErtusJson() {
        /*
        if(redisTemplate.hasKey("ertusList")){
            redisTemplate.delete("ertusList");
        }*/
        Object o;
        // if(redisTemplate.opsForValue().get("ertusList")==null) {
        List<LinkedHashMap> menuList = utilDao.getErtusList();
        //用于判断是否已存在当前节点
        List<Object> strList = new ArrayList<Object>();
        List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
        //遍历menuList
        for (Map<String, Object> m : menuList) {
            //遍历map，并创建treeNode
            for (String k : m.keySet()) {
                //该节点不在节点树
                if (!strList.contains(m.get(k))) {
                    if (k.equals("ErtuLocation")) {
                        //创建节点
                        TreeNode treeNode = new TreeNode();
                        treeNode.setIcon("el-icon-menu");
                        treeNode.setPath("/" + m.get(k).toString());
                        // treeNode.setPath("/"+(treeNodeList.size()+1));
                        treeNode.setName(m.get(k).toString());
                        //把节点拼到节点树上
                        treeNodeList.add(treeNode);
                        //放到已存在节点表里
                        strList.add(m.get(k));
                    } else if (k.equals("ErtuName")) {
                        //创建节点
                        TreeNode treeNode = new TreeNode();
                        treeNode.setIcon("el-icon-menu");
                        treeNode.setName(m.get(k).toString());
                        treeNode.setPath("/" + m.get(k).toString());
                        //把节点拼到节点树上
                        for (TreeNode t : treeNodeList) {
                            if (t.getName().equals(m.get("ErtuLocation"))) {
                                if (StringUtils.isEmpty(t.getChildren())) {
                                    // treeNode.setPath("/"+(treeNodeList.size()+1)+"/"+1);
                                    List<TreeNode> list = new ArrayList<TreeNode>();
                                    list.add(treeNode);
                                    t.setChildren(list);
                                } else {
                                    // treeNode.setPath("/"+(treeNodeList.size()+1)+"/"+(t.getChildren().size()+1));
                                    t.getChildren().add(treeNode);
                                }
                            }
                        }
                        //放到已存在节点表里
                        strList.add(m.get(k));
                    } else if (k.equals("EMeterName")) {
                        //创建节点
                        TreeNode treeNode = new TreeNode();
                        treeNode.setIcon("el-icon-menu");
                        treeNode.setPath("/" + m.get(k).toString());
                        //  treeNode.setPath("/"+treeNodeList.size()+1);
                        treeNode.setName(m.get(k).toString());
                        //把节点拼到节点树上
                        for (TreeNode t : treeNodeList) {
                            if (t.getName().equals(m.get("ErtuLocation"))) {
                                for (TreeNode t1 : t.getChildren()) {
                                    if (t1.getName().equals(m.get("ErtuName"))) {
                                        if (StringUtils.isEmpty(t1.getChildren())) {
                                            // treeNode.setPath("/"+(treeNodeList.size()+1)+"/"+(t.getChildren().size()+1)+"/"+1);
                                            List<TreeNode> list = new ArrayList<TreeNode>();
                                            list.add(treeNode);
                                            t1.setChildren(list);
                                        } else {
                                            //treeNode.setPath("/"+(treeNodeList.size()+1)+"/"+(t.getChildren().size()+1)+"/"+(t.getChildren().size()+1));
                                            t1.getChildren().add(treeNode);
                                        }
                                    }
                                }
                            }
                        }
                        //放到已存在节点表里
                        strList.add(m.get(k));
                    }
                }
            }
        }
        Object jsonObject = JSONObject.toJSON(treeNodeList);
        o = jsonObject;
        //  redisTemplate.opsForValue().set("ertusList",jsonObject);
        // }else {
        //       o = redisTemplate.opsForValue().get("ertusList");
        //  }
        return o;
    }

    @Override
    public Record getTimeAndIp(String geNumber) {
        return this.utilDao.getTimeAndIp(geNumber);
    }

    @Override
    public int getTotalNumber() {
        return this.utilDao.getTotalNumber();
    }

    @Override
    public Map getRecordNumberList() {
        List<LinkedHashMap> list = this.utilDao.getRecordNumberList();
        List<Map> listMap = new ArrayList<>();
        List<String> dateList = DateUtil.getWeekDay();
        //如果list为空
        if (StringUtils.isEmpty(list.size())) {
            logger.info("list is empty");
            for (int i = 0; i < dateList.size(); i++) {
                Map map = new HashMap();
                map.put("label", dateList.get(i));
                map.put("value", 0);
                listMap.add(i, map);
            }
            //如果list的长度大于0且小于7
        } else if (list.size() < 7) {
            logger.info("list is smaller than 7 and bigger than 0");
            int i = 0;
            while (i < list.size()) {
                for (int j = 0; j < dateList.size(); j++) {
                    if (!list.get(i).get("name").equals(dateList.get(j))) {
                        Map map = new HashMap();
                        map.put("label", dateList.get(j));
                        map.put("value", 0);
                        listMap.add(j, map);
                    } else {
                        Map map = new HashMap();
                        map.put("label", list.get(i).get("name").toString());
                        map.put("value", Integer.parseInt(list.get(i).get("value").toString()));
                        listMap.add(j, map);
                        i++;
                    }
                }
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("rows", listMap);
        return map;
    }

    @Override
    //@Async("asyncServiceExecutor")
    public boolean getAllData() {
        byte[] tm = {19, 6, 23, 0, 0, 0, 23, 59, 59};
        List<Map> mapList = this.utilDao.getErtuIDList();
      //  byte i = 1;
      //  while (i<23){
       // System.out.println("正在跑第"+i+"天的数据");
        for (Map<String, Object> m : mapList) {
            int port = Integer.parseInt(m.get("port").toString());
            String host = m.get("ip").toString();
            int ErtuID = Integer.parseInt(m.get("ErtuID").toString());
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    logger.warn(port + "," + host + "," + ErtuID);
                    try {
                        client.connect(port, host, ErtuID, tm);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        /*休眠1小时
            try {
                Thread.sleep(1000*60*60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i++;
           tm[2] = i;
         }*/
        return true;
    }


    //@Scheduled(cron = "0 0/15 * * * ?")
    public boolean AutoGetAllData() {
        byte[] tm ={19, 5, 25, 0, 0, 0, 23, 59, 59};
        System.out.println(Arrays.toString(tm));
        // byte[] tm =
        List<Map> mapList = this.utilDao.getErtuIDList();
        //byte i = 2;
        //while (i<20){
        //System.out.println("正在跑第"+i+"天的数据");
        for (Map<String, Object> m : mapList) {
            int port = Integer.parseInt(m.get("port").toString());
            String host = m.get("ip").toString();
            int ErtuID = Integer.parseInt(m.get("ErtuID").toString());
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    logger.warn(port + "," + host + "," + ErtuID);
                    try {
                        client.connect(port, host, ErtuID, tm);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        //    i++;
        //    tm[2] = i;
        //  }
        boolean success = true;
        if (tm[2] == 30) {
            success = false;
        }
        return false;
    }

    //@Scheduled(cron = "0 0/15 * * * ?")
    public void AutoGetAllDataById(byte[] tm, int ErtuID, int port, String host) {
        System.out.println(Arrays.toString(tm));
        new Thread() {
            @Override
            public void run() {
                super.run();
                logger.warn(port + "," + host + "," + ErtuID);
                try {
                    client.connect(port, host, ErtuID, tm);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();

        //    i++;
        //    tm[2] = i;
        //  }

    }

    @Override
    public Map getTotalPower() {
        Map map = new HashMap();
        List<Map> maps = this.utilDao.getTotalPower();
        List<Map> mapList = new ArrayList<>();
        List<String> timeList = new ArrayList<>();
        List<Double> powerNumList = new ArrayList<>();
        for (int i = 0; i < maps.size() - 1; i++) {
            int EMeterID = Integer.parseInt(maps.get(i).get("EMeterID").toString());
            if (EMeterID == 1) {
                String Time = maps.get(i).get("Time").toString();
                if (!Time.equals("4-08")) {
                    timeList.add(i, Time);
                    Double beginNum = Double.valueOf(maps.get(i).get("ZxygZ").toString());
                    Double endNum = Double.valueOf(maps.get(i + 1).get("ZxygZ").toString());
                    int MultiplyRatio = Integer.parseInt(maps.get(i).get("MultiplyRatio").toString());
                    Double num;
                    if (MultiplyRatio == 1) {
                        num = Double.valueOf(maps.get(i).get("num").toString());
                    } else {
                        num = 1.0;
                    }
                    powerNumList.add(i, (endNum - beginNum) * num);
                }
            } else {
                Double beginNum = Double.valueOf(maps.get(i).get("ZxygZ").toString());
                Double endNum = Double.valueOf(maps.get(i + 1).get("ZxygZ").toString());
                int MultiplyRatio = Integer.parseInt(maps.get(i).get("MultiplyRatio").toString());
                Double num;
                if (MultiplyRatio == 1) {
                    num = Double.valueOf(maps.get(i).get("num").toString());
                } else {
                    num = 1.0;
                }
                Double total = powerNumList.get(i - 8) + (endNum - beginNum) * num;
                powerNumList.set(i - 8, total);
            }
        }
        for (int i = 0; i < timeList.size(); i++) {
            Map map1 = new LinkedHashMap();
            map1.put("Time", timeList.get(i));
            map1.put("PowerTotal", powerNumList.get(i));
            mapList.add(map1);
        }
        map.put("list", mapList);
        map.put("timeList", timeList);
        map.put("powerList", powerNumList);
        return map;
    }

    @Override
    public int getTotalUserNumber() {
        return this.utilDao.getTotalUserNumber();
    }
}
