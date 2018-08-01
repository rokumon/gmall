package cn.mislily.gmall.manager.controller;

import cn.mislily.gmall.bean.BaseCatalog1;
import cn.mislily.gmall.bean.BaseCatalog2;
import cn.mislily.gmall.bean.BaseCatalog3;
import cn.mislily.gmall.service.CatalogService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CatalogController {

    @Reference
    private CatalogService catalogService;

    @RequestMapping("catalog1List")
    @ResponseBody
    public List<BaseCatalog1> catalog1List(){
        List<BaseCatalog1> baseCatalog1List = catalogService.baseCatalog1List();
        return baseCatalog1List;
    }

    @RequestMapping("catalog2List/{catalog1Id}")
    @ResponseBody
    public List<BaseCatalog2> catalog2List(@PathVariable("catalog1Id") String catalog1Id){
        List<BaseCatalog2> baseCatalog2List = catalogService.baseCatalog2List(catalog1Id);
        return baseCatalog2List;
    }

    @RequestMapping("catalog3List/{catalog2Id}")
    @ResponseBody
    public List<BaseCatalog3> catalog3List(@PathVariable("catalog2Id") String catalog2Id){
        List<BaseCatalog3> baseCatalog3List = catalogService.baseCatalog3List(catalog2Id);
        return baseCatalog3List;
    }
}
