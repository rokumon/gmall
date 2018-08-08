package cn.mislily.gmall.manager.controller;

import cn.mislily.gmall.bean.BaseSaleAttr;
import cn.mislily.gmall.bean.SpuInfo;
import cn.mislily.gmall.FastFDSFileUtils;
import cn.mislily.gmall.service.SpuService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
public class SpuController {

    @Reference
    private SpuService spuService;



    // SpuInfo

    @RequestMapping("spuInfo/{spuId}")
    @ResponseBody
    public SpuInfo spuInfo(@PathVariable("spuId") String spuId){

        SpuInfo spuInfo = spuService.spuInfo(spuId);
        System.out.println(spuInfo.getSpuImageList());
        System.out.println(spuInfo.getSpuSaleAttrList());
        return spuInfo;
    }


    @RequestMapping("saveSpu")
    @ResponseBody
    public Boolean saveSpu(SpuInfo spuInfo){

        spuService.saveSpuInfo(spuInfo);

        return true;
    }


    @RequestMapping("deleteSpu/{spuId}")
    @ResponseBody
    public Boolean deleteSpu(@PathVariable("spuId") String spuId){

        spuService.deleteSpuInfo(spuId);

        return true;
    }


    @RequestMapping("updateSpu")
    @ResponseBody
    public Boolean updateSpu(SpuInfo spuInfo){

        spuService.updateSpuInfo(spuInfo);

        return true;
    }


    @RequestMapping("spuList/{catalog3Id}")
    @ResponseBody
    public List<SpuInfo> spuList(@PathVariable("catalog3Id")String catalog3Id){
        List<SpuInfo> spuInfos = spuService.spuList(catalog3Id);
        return spuInfos;
    }


    // saleAttr

    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    public List<BaseSaleAttr> baseSaleAttrList(){
        List<BaseSaleAttr> baseSaleAttrs = spuService.baseSaleAttrList();
        return baseSaleAttrs;
    }


    // image

    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile file){
        // fdfs的上传工具
        String imgUrl = FastFDSFileUtils.uploadImage(file);

        return imgUrl;
    }

    @RequestMapping("fileRemove")
    @ResponseBody
    public String fileRemove(String imageUrl){
        spuService.deleteSpuImage(imageUrl);
        return "success";
    }
}
