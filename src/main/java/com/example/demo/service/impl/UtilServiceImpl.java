package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.TreeNode;
import com.example.demo.mapper.UtilDao;
import com.example.demo.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
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

    @Override
    public Object getMenuJson() {
        Object o;
        if(redisTemplate.opsForValue().get("menuList")==null) {
            List<Map> menuList = utilDao.getMenuList();
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
                            strList.add(m.get(k));
                        }
                    }
                }
            }
            Object jsonObject = JSONObject.toJSON(treeNodeList);
            o = jsonObject;
            redisTemplate.opsForValue().set("menuList",jsonObject);
        }else {
            o = redisTemplate.opsForValue().get("menuList");
        }
        return o;
    }


}
