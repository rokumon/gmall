package cn.mislily.gmall.manager.controller;

import cn.mislily.gmall.bean.SkuInfo;
import cn.mislily.gmall.service.SkuService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SkuController {

    @Reference
    private SkuService skuService;


    // SkuInfo

    @RequestMapping("saveSku")
    @ResponseBody
    public String saveSku(SkuInfo skuInfo){

        skuService.saveSkuInfo(skuInfo);

        return "success";
    }

    @RequestMapping("deleteSku/{skuId}")
    @ResponseBody
    public String deleteSku(@PathVariable("skuId") String skuId){

        skuService.deleteSkuInfo(skuId);

        return "success";
    }


    @RequestMapping("updateSku")
    @ResponseBody
    public String updateSku(SkuInfo skuInfo){

        skuService.updateSkuInfo(skuInfo);

        return "success";
    }


    @RequestMapping("skuInfo/{skuId}")
    @ResponseBody
    public SkuInfo skuInfo(@PathVariable("skuId") String skuId){

        SkuInfo skuInfo = skuService.getSkuInfoById(skuId);

        return skuInfo;
    }

    @RequestMapping("skuList/{spuId}")
    @ResponseBody
    public List<SkuInfo> skuList(@PathVariable("spuId") String spuId){

        List<SkuInfo> skuInfos =  skuService.getSkuInfoListBySpuId(spuId);

        return skuInfos;
    }

    //

}
