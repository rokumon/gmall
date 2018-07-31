package cn.mislily.gmall.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping(value="index")
    public String index(){
        return "index";
    }

    @RequestMapping("attributePage")
    public String attributePage(){
        return "attributePage";
    }
}