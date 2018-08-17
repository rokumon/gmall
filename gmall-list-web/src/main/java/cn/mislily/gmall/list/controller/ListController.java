package cn.mislily.gmall.list.controller;

import cn.mislily.gmall.bean.*;
import cn.mislily.gmall.service.BaseAttrService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
public class ListController {

    @Reference
    cn.mislily.gmall.service.ListService ListService;
    @Reference
    BaseAttrService baseAttrService;

    @RequestMapping("list.html")
    public String search(SkuLsParam skuLsParam, ModelMap map) {

        List<SkuLsInfo> skuLsInfo;
        skuLsInfo = ListService.search(skuLsParam);

        //
        List<BaseAttrInfo> baseAttrInfos = getAttrValueIds(skuLsInfo);

        // 填充平台属性
        if(baseAttrInfos != null && baseAttrInfos.size() > 0){

            for (int i = 0; i < baseAttrInfos.size(); i++) {
                BaseAttrInfo baseAttrInfo = baseAttrInfos.get(i);
                baseAttrInfo = baseAttrService.getAttrInfoById(baseAttrInfo.getId());
                baseAttrInfos.remove(i);
                baseAttrInfos.add(i,baseAttrInfo);
            }
        }

        // 封装平台属性的列表,排除已经选中的属性[制作面包屑]
        String[] valueId = skuLsParam.getValueId();

        List<Crumb> crumbs = new ArrayList<>();// 面包屑
        if (valueId != null && valueId.length > 0) {
            for (String s : valueId) {
                Iterator<BaseAttrInfo> iterator = baseAttrInfos.iterator();
                while (iterator.hasNext()) {
                    BaseAttrInfo baseAttrInfo = iterator.next();
                    List<BaseAttrValue> attrValueList = baseAttrInfo.getAttrValueList();
                    for (BaseAttrValue baseAttrValue : attrValueList) {
                        if (baseAttrValue.getId().equals(s)) {
                            Crumb crumb = new Crumb();
                            // 制作面包屑url
                            String urlParamForCrumb = getUrlParamForCrumb(skuLsParam, s);
                            // 制作面包屑name
                            String valueName = "";
                            valueName = baseAttrValue.getValueName();
                            //封装好面包屑
                            crumb.setValueName(valueName);
                            crumb.setUrlParam(urlParamForCrumb);
                            crumbs.add(crumb);
                            iterator.remove();//当前游标所在位置的元素删除
                        }
                    }
                }
            }
        }


        String urlParam = getUrlParam(skuLsParam);
        map.put("urlParam", urlParam);
        // 面包屑
        map.put("attrValueSelectedList", crumbs);
        // 筛选菜单
        map.put("attrList", baseAttrInfos);
        map.put("skuLsInfoList", skuLsInfo);
        System.out.println("DEBUG");
        return "list";
    }


    /***
     * 制作面包屑url
     * @param skuLsParam
     * @return
     */
    private String getUrlParamForCrumb(SkuLsParam skuLsParam,String id) {

        String urlParam = "";

        String[] valueId = skuLsParam.getValueId();
        String keyword = skuLsParam.getKeyword();
        String catalog3Id = skuLsParam.getCatalog3Id();

        if (StringUtils.isNotBlank(keyword)) {
            if (StringUtils.isBlank(urlParam)) {

                urlParam = "keyword=" + keyword;

            } else {
                urlParam = urlParam + "&keyword=" + keyword;
            }
        }

        if (StringUtils.isNotBlank(catalog3Id)) {
            if (StringUtils.isBlank(urlParam)) {

                urlParam = "catalog3Id=" + catalog3Id;

            } else {
                urlParam = urlParam + "&catalog3Id=" + catalog3Id;
            }
        }

        if (valueId != null && valueId.length > 0) {
            for (String s : valueId) {
                if(!id.equals(s)){
                    urlParam = urlParam + "&valueId=" + s;
                }
            }
        }

        return urlParam;
    }

    /***
     * 制作普通url
     * @param skuLsParam
     * @return
     */
    private String getUrlParam(SkuLsParam skuLsParam) {

        String urlParam = "";

        String[] valueId = skuLsParam.getValueId();
        String keyword = skuLsParam.getKeyword();
        String catalog3Id = skuLsParam.getCatalog3Id();

        if (StringUtils.isNotBlank(keyword)) {
            if (StringUtils.isBlank(urlParam)) {

                urlParam = "keyword=" + keyword;

            } else {
                urlParam = urlParam + "&keyword=" + keyword;
            }
        }

        if (StringUtils.isNotBlank(catalog3Id)) {
            if (StringUtils.isBlank(urlParam)) {

                urlParam = "catalog3Id=" + catalog3Id;

            } else {
                urlParam = urlParam + "&catalog3Id=" + catalog3Id;
            }
        }

        if (valueId != null && valueId.length > 0) {
            for (String s : valueId) {
                urlParam = urlParam + "&valueId=" + s;
            }
        }

        return urlParam;
    }

    /***
     * sku的平台属性列表
     * @param skuLsInfo
     * @return
     */
    private List<BaseAttrInfo> getAttrValueIds(List<SkuLsInfo> skuLsInfo) {

        Set<String> valueIds = new HashSet<>();

        for (SkuLsInfo lsInfo : skuLsInfo) {
            List<SkuLsAttrValue> skuAttrValueList = lsInfo.getSkuAttrValueList();

            for (SkuLsAttrValue skuLsAttrValue : skuAttrValueList) {
                String valueId = skuLsAttrValue.getValueId();
                valueIds.add(valueId);
            }
        }

        // 根据去重后的id集合检索，关联到的平台属性列表
        List<BaseAttrInfo> baseAttrInfos = new ArrayList<>();
        baseAttrInfos = baseAttrService.getBaseAttrValuesByValueIds(valueIds);

        return baseAttrInfos;
    }

    @RequestMapping("index")
    public String index() {
        return "index";
    }
}
