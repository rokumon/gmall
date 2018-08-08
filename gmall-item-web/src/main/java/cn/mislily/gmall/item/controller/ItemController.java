package cn.mislily.gmall.item.controller;

import com.atguigu.gmall.bean.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ItemController {

    @RequestMapping("{skuId}.html")
    public String item(ModelMap map){
        return "item";
    }

    @RequestMapping("index")
    public String index(ModelMap map){

        map.put("hello","hello thymeleaf");

        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        for (int i = 0; i <5 ; i++) {
            UserInfo userInfo = new UserInfo();
            userInfo.setNickName("小"+i);
            userInfo.setPhoneNum("12333333333");

            userInfos.add(userInfo);
        }

        map.put("userInfos",userInfos);
        return "demo";
    }
}
