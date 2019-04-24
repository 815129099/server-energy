package com.example.demo.web;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.util.Excel.ExcelParam;
import com.example.demo.util.Excel.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

@RestController
public class TestController {

    @Autowired
    private UserService userService;

    @RequestMapping("/index")
    public ModelAndView index(){
        return  new ModelAndView("/index");
    }

    @RequestMapping("/introduce")
    public ModelAndView introduce(){
        return  new ModelAndView("/introduce");
    }

    @RequestMapping({"exportUser.do"})
    public void exportAllege(HttpServletResponse response) throws Exception {
       // String u = new String(userState.getBytes("ISO-8859-1"), "UTF-8");
     //   System.out.println(begin + "," + end + "," + u);
     //   List<Record> list = this.userService.getRecordByTime(begin, end, u);
        List<User> list = this.userService.selectList(new EntityWrapper<User>().eq("id",1));
        String[] heads = new String[]{"序号", "id", "工号", "书名", "状态"};
        List<String[]> data = new LinkedList();
        for(int i = 0; i < list.size(); ++i) {
            User entity = (User) list.get(i);
            String[] temp = new String[]{String.valueOf(i + 1), entity.getId().toString(), entity.getGeName(), entity.getGeNumber(), entity.getEmail()};
            data.add(temp);
        }
        ExcelParam param = (new ExcelParam.Builder("借阅记录表")).headers(heads).data(data).build();
        ExcelUtil.export(param, response);
    }
}
