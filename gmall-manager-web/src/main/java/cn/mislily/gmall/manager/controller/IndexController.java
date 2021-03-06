package cn.mislily.gmall.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping(value="index")
    public String index(){
        return "index";
    }

    @RequestMapping("attrListPage")
    public String attributePage(){
        return "attrListPage";
    }

    @RequestMapping("spuListPage")
    public String spuListPage(){
        return "spuListPage";
    }

    @RequestMapping("skuListPage")
    public String skuListPage(){
        return "skuListPage";
    }

}
