package cn.mislily.gmall.manager.controller;

import cn.mislily.gmall.bean.BaseSaleAttr;
import cn.mislily.gmall.bean.SpuInfo;
import cn.mislily.gmall.manager.utils.UploadUtils;
import cn.mislily.gmall.service.SpuService;
import com.alibaba.dubbo.config.annotation.Reference;
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

    @RequestMapping("saveSpu")
    @ResponseBody
    public Boolean saveSpu(SpuInfo spuInfo){

        spuService.saveSpuInfo(spuInfo);

        return true;
    }

    @RequestMapping("baseSaleAttrList")
    @ResponseBody
    public List<BaseSaleAttr> baseSaleAttrList(){
        List<BaseSaleAttr> baseSaleAttrs = spuService.baseSaleAttrList();
        return baseSaleAttrs;
    }

    @RequestMapping("spuList/{catalog3Id}")
    @ResponseBody
    public List<SpuInfo> spuList(@PathVariable("catalog3Id")String catalog3Id){
        List<SpuInfo> spuInfos = spuService.spuList(catalog3Id);
        return spuInfos;
    }

    //image
    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload(@RequestParam("file") MultipartFile file){
        // fdfs的上传工具
        String imgUrl = UploadUtils.uploadImage(file);

        return imgUrl;
    }

}
