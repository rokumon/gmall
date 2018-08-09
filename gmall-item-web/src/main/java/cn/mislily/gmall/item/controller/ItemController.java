package cn.mislily.gmall.item.controller;

import cn.mislily.gmall.bean.SkuInfo;
import cn.mislily.gmall.bean.SkuSaleAttrValue;
import cn.mislily.gmall.bean.SpuSaleAttr;
import cn.mislily.gmall.bean.UserInfo;
import cn.mislily.gmall.service.SkuService;
import cn.mislily.gmall.service.SpuService;
import com.alibaba.fastjson.JSON;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

    @Reference
    SkuService skuService;

    @Reference
    SpuService spuService;

    @RequestMapping("/{skuId}.html")
    public String item(@PathVariable("skuId") String skuId, ModelMap map){

        System.out.println("skuId = " + skuId);

        SkuInfo skuInfo = skuService.skuInfo(skuId);

        map.put("skuInfo",skuInfo);

        String spuId = skuInfo.getSpuId();

//        // 当前sku所包含的销售属性
//        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
//
//        // spu的销售属性列表
//        List<SpuSaleAttr> saleAttrListBySpuId = spuService.getSaleAttrListBySpuId(spuId);

        // spu的sku和销售属性对应关系的hash表
        List<SkuInfo> infos = spuService.getSkuSaleAttrValueListBySpu(spuId);
        HashMap<String, String> stringStringHashMap1 = new HashMap<>();
        for (SkuInfo info : infos) {
            String v = info.getId();

            List<SkuSaleAttrValue> skuSaleAttrValueList = info.getSkuSaleAttrValueList();
            String k = "";
            for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                k = k + "|" + skuSaleAttrValue.getSaleAttrValueId();
            }
            stringStringHashMap1.put(k,v);
        }
        String skuJson = JSON.toJSONString(stringStringHashMap1);
        map.put("skuJson",skuJson);

        // 销售属性列表
        Map<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("spuId",spuId);
        stringStringHashMap.put("skuId",skuId);
        List<SpuSaleAttr> saleAttrListBySpuId = spuService.getSpuSaleAttrListCheckBySku(stringStringHashMap);
        map.put("spuSaleAttrListCheckBySku",saleAttrListBySpuId);

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
