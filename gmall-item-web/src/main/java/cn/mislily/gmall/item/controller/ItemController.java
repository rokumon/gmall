package cn.mislily.gmall.item.controller;

import cn.mislily.gmall.bean.*;
import cn.mislily.gmall.service.SkuService;
import cn.mislily.gmall.service.SpuService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ItemController {

    @Reference
    SkuService skuService;

    @Reference
    SpuService spuService;

    /**
     * 获取 sku 的信息
     * 包含 sku基础信息 图片列表 销售属性 和 对应的销售属性值[哪些属性被勾选]
     *
     * @param skuId
     * @param map
     * @return
     */
    @RequestMapping("/{skuId}.html")
    public String item(@PathVariable("skuId") String skuId, ModelMap map) {

        // 获取 spuId
        SkuInfo skuInfo = null;

        skuInfo = skuService.skuInfo(skuId);

        // 获取 sku 对应 spu 的 所有 skuInfo 列表
        List<SkuInfo> skuInfoList = skuService.skuList(skuInfo.getSpuId());

        // 获取 spu 对应的 spuSaleAttr 列表
        List<SpuSaleAttr> spuSaleAttrList = spuService.spuSaleAttrList(skuInfo.getSpuId());


        Map<String, String> skuSaleAttrMap = new HashMap<>();


        // 用于 切换的 Map
        // |红色|128G|2G|4G| -> 71
        for (SkuInfo info : skuInfoList) {

            String key = "|";

            List<SkuSaleAttrValue> skuSaleAttrValueList = info.getSkuSaleAttrValueList();

            for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                key = key + skuSaleAttrValue.getSaleAttrValueName() + "|";
            }

            skuSaleAttrMap.put(key, info.getId());
        }


        Map<String, SpuSaleAttrValue> spuSaleAttrValueMap = new HashMap<>();

        // 用于 筛选被选中的 Map
        // 渲染 可选列表
        for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {

            List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();

            for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                spuSaleAttrValueMap.put("|" + spuSaleAttr.getSpuId() + "|" + spuSaleAttrValue.getSaleAttrId() + "|" + spuSaleAttrValue.getSaleAttrValueName() + "|", spuSaleAttrValue);
            }
        }

        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();

        // 设定被选中的
        for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
            SpuSaleAttrValue spuSaleAttrValue = spuSaleAttrValueMap.get("|" + skuInfo.getSpuId() + "|" + skuSaleAttrValue.getSaleAttrId() + "|" + skuSaleAttrValue.getSaleAttrValueName() + "|");
            if (spuSaleAttrValue != null) {
                spuSaleAttrValue.setIsChecked("1");
            }
        }

        // 将 sku 基本信息放入
        map.put("skuInfo",skuInfo);

        // 将属性列表 Json 化
        String skuJson = JSON.toJSONString(skuSaleAttrMap);
        map.put("skuJson",skuJson);

        map.put("spuSaleAttrListCheckBySku",spuSaleAttrList);

        System.out.println("DEBUG");
        /*
        // 获取 spuId
        SkuInfo skuInfo = null;

        // 查询 skuInfo 对象
        skuInfo = skuService.skuInfo(skuId);

        // 获取单个 sku 的 所有 销售属性值
        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();

        // 获取 spuId
        String spuId = skuInfo.getSpuId();

        // 查询 spuId 对应的 SaleAttrList[填充完整的]
        List<SpuSaleAttr> spuSaleAttrList = spuService.spuSaleAttrList(spuId);

        Map<String,SpuSaleAttrValue> spuSaleAttrValueMap = new HashMap<>();
        // Map<String,String> skuSaleAttrValueMap = new HashMap<>();

        // Map 化
        for (SpuSaleAttr spuSaleAttr : spuSaleAttrList) {

            List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();

            // String key = "|";

            for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                spuSaleAttrValueMap.put(""+spuId+"|"+spuSaleAttrValue.getSaleAttrId()+"|"+spuSaleAttrValue.getSaleAttrValueName(),spuSaleAttrValue);
                // key = key + spuSaleAttrValue.getSaleAttrValueName() + "|";
            }

            // skuSaleAttrValueMap.put(key,);

        }

        // 设定被选中的
        for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
            SpuSaleAttrValue spuSaleAttrValue = spuSaleAttrValueMap.get(""+spuId+"|"+skuSaleAttrValue.getSaleAttrId()+"|"+skuSaleAttrValue.getSaleAttrValueName());
            if(spuSaleAttrValue != null){
                spuSaleAttrValue.setIsChecked("1");
            }
        }

        // 将 sku 基本信息放入
        map.put("skuInfo",skuInfo);

        // 将属性列表 Json 化
        //map.put("skuJson",skuJson);

        map.put("spuSaleAttrListCheckBySku",spuSaleAttrValueMap);

        return "item";

        */
        return "item";
    }

    /*
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
    */
}
