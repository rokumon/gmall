package cn.mislily.gmall.manager.service.impl;

import cn.mislily.gmall.FastFDSFileUtils;
import cn.mislily.gmall.bean.*;
import cn.mislily.gmall.manager.mapper.*;
import cn.mislily.gmall.service.SpuService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    private SpuInfoMapper spuInfoMapper;

    @Autowired
    private SpuImageMapper spuImageMapper;

    @Autowired
    private SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    private BaseSaleAttrMapper baseSaleAttrMapper;

    @Autowired
    private SpuSaleAttrValueMapper spuSaleAttrValueMapper;


    //SpuInfo

    /**
     * 根据 三级菜单的 id 查询出 spuList
     * @param catalog3Id
     * @return
     */
    @Override
    public List<SpuInfo> spuList(String catalog3Id) {
        SpuInfo spuInfo = new SpuInfo();
        spuInfo.setCatalog3Id(catalog3Id);
        List<SpuInfo> spuInfoList = spuInfoMapper.select(spuInfo);
        return spuInfoList;
    }

    /**
     * 保存 spuInfo 的信息
     * @param spuInfo
     */
    @Override
    public void saveSpuInfo(SpuInfo spuInfo) {

        // 保存 SpuInfo 插入后返回 spuInfo 的信息
        spuInfoMapper.insertSelective(spuInfo);

        // 获取 SpuInfo 的 id
        String spuId = spuInfo.getId();


        // 获取图片信息
        List<SpuImage> spuImageList = spuInfo.getSpuImageList();

        // 保存图片信息
        saveSpuImageByList(spuImageList, spuId);


        // 获取 销售属性 列表
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();

        // 保存 销售属性 列表
        saveSaleAttrsByList(spuSaleAttrList, spuId);
    }

    @Override
    public void deleteSpuInfo(String spuId) {

    }

    @Override
    public void updateSpuInfo(SpuInfo spuInfo) {

    }

    /**
     * 根据 spuId 获取 spuInfo 的所有属性信息
     * @param spuId
     * @return
     */
    @Override
    public SpuInfo spuInfo(String spuId) {

        SpuInfo spuInfo = new SpuInfo();
        spuInfo.setId(spuId);

        // 获取 spuInfo 基础信息
        spuInfo = spuInfoMapper.selectOne(spuInfo);

        // 获取 spuInfo 的 图片列表
        List<SpuImage> spuImageList = spuImageList(spuId);

        // 获取 spuInfo 的 销售属性 列表
        List<SpuSaleAttr> spuSaleAttrList = spuSaleAttrList(spuId);

        /*
        *
        * {"total":2,"rows":[{"id":"","saleAttrValueName":"123"},{"id":"","saleAttrValueName":"234"}]}
        *
        * */
        for (SpuSaleAttr spuSaleAttr:spuSaleAttrList) {

            if(spuSaleAttr != null){

                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();

                if (spuSaleAttrValueList != null){

                    Map<String, Object> spuSaleAttrValueJson = MapRizeList(spuSaleAttrValueList);

                    spuSaleAttr.setSpuSaleAttrValueJson(spuSaleAttrValueJson);
                }
            }
        }

        spuInfo.setSpuImageList(spuImageList);
        spuInfo.setSpuSaleAttrList(spuSaleAttrList);

        return spuInfo;
    }

    private Map<String, Object> MapRizeList(List<SpuSaleAttrValue> spuSaleAttrValueList){

        Map<String, Object> spuSaleAttrValueJson = new HashMap<String, Object>();

        List<Map<String,Object>> rows = new ArrayList<Map<String,Object>>();

        for (SpuSaleAttrValue spuSaleAttrValue:spuSaleAttrValueList) {
            Map<String,Object> row = new HashMap<>();

            row.put("id",spuSaleAttrValue.getId());
            row.put("saleAttrValueName",spuSaleAttrValue.getSaleAttrValueName());

            rows.add(row);
        }

        spuSaleAttrValueJson.put("total",spuSaleAttrValueList.size());
        spuSaleAttrValueJson.put("rows",rows);

        return spuSaleAttrValueJson;
    }


    // SpuSaleAttr

    /**
     * 保存 销售属性 列表
     * @param spuSaleAttrList
     * @param spuId
     */
    public void saveSaleAttrsByList(List<SpuSaleAttr> spuSaleAttrList, String spuId){
        if(spuSaleAttrList != null){
            for (SpuSaleAttr spuSaleAttr:spuSaleAttrList){

                spuSaleAttr.setSpuId(spuId);
                spuSaleAttrMapper.insert(spuSaleAttr);

                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttr.getSpuSaleAttrValueList();
                saveSpuSaleAttrValueByList(spuSaleAttrValueList, spuId);
            }
        }
    }

    /**
     * 获取 销售属性 列表
     * @param spuId
     * @return
     */
    public List<SpuSaleAttr> spuSaleAttrList(String spuId){

        SpuSaleAttr spuSaleAttr = new SpuSaleAttr();
        spuSaleAttr.setSpuId(spuId);

        // 获取 销售属性 列表
        List<SpuSaleAttr> spuSaleAttrList = spuSaleAttrMapper.select(spuSaleAttr);

        // 填充 销售属性值
        if (spuSaleAttrList != null) {

            for (SpuSaleAttr spuSaleAttrObj:spuSaleAttrList){
                // 获取的是 saleAttrId
                List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttrValues(spuSaleAttrObj.getSaleAttrId(),spuId);
                spuSaleAttrObj.setSpuSaleAttrValueList(spuSaleAttrValueList);
            }
        }

        return spuSaleAttrList;
    }


    // SpuSaleAttrValue

    /**
     * 保存 销售属性值 列表
     * @param spuSaleAttrValueList
     * @param spuId
     */
    public void saveSpuSaleAttrValueByList(List<SpuSaleAttrValue> spuSaleAttrValueList, String spuId){

        // 保存 销售属性值
        if (spuSaleAttrValueList != null) {
            for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
                spuSaleAttrValue.setSpuId(spuId);
                spuSaleAttrValueMapper.insert(spuSaleAttrValue);
            }
        }
    }

    /**
     * 获取 销售属性值 列表
     * @param saleAttrId
     * @param spuId
     * @return
     */
    public List<SpuSaleAttrValue> spuSaleAttrValues(String saleAttrId,String spuId) {

        SpuSaleAttrValue spuSaleAttrValue = new SpuSaleAttrValue();

        spuSaleAttrValue.setSpuId(spuId);
        spuSaleAttrValue.setSaleAttrId(saleAttrId);

        List<SpuSaleAttrValue> spuSaleAttrValueList = spuSaleAttrValueMapper.select(spuSaleAttrValue);

        return spuSaleAttrValueList;
    }


    // BaseSaleAttr

    /**
     * 查询所有的 基础销售属性
     * @return
     */
    @Override
    public List<BaseSaleAttr> baseSaleAttrList() {
        return baseSaleAttrMapper.selectAll();
    }


    // SpuImage

    /**
     * 根据 spuId 获取 spuImage
     * @param spuId
     * @return
     */
    public List<SpuImage> spuImageList(String spuId){
        SpuImage spuImage = new SpuImage();
        spuImage.setSpuId(spuId);

        List<SpuImage> spuImageList = spuImageMapper.select(spuImage);
        return spuImageList;
    }

    /**
     * 保存 图片信息
     * @param spuImageList
     * @param spuId
     */
    @Override
    public void saveSpuImageByList(List<SpuImage> spuImageList, String spuId){
        for (SpuImage spuImage : spuImageList) {
            spuImage.setSpuId(spuId);
            spuImageMapper.insert(spuImage);
        }
    }

    /**
     * 删除 图片信息
     * @param imageUrl
     */
    public void deleteSpuImage(String imageUrl){
        if(imageUrl != null) {
            SpuImage spuImage = new SpuImage();
            spuImage.setImgUrl(imageUrl);

            spuImageMapper.delete(spuImage);

            FastFDSFileUtils.removeImage(imageUrl);
        }
    }

    /**
     * 根据 sku 决定哪些 spu 对应的 销售属性值 被勾选
     * @param stringStringHashMap
     * @return
     */
    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Map<String, String> stringStringHashMap) {

        List<SpuSaleAttr> spuSaleAttrList = spuSaleAttrValueMapper.selectSpuSaleAttrListCheckBySku(stringStringHashMap);

        System.out.println("DEBUG");

        return spuSaleAttrList;
    }

    /**
     * 通过 spuId 获取对应的 skuSaleAttrValue
     * @param spuId
     * @return
     */
    @Override
    public List<SkuInfo> getSkuSaleAttrValueListBySpu(String spuId) {

        List<SkuInfo> skuInfoList = spuSaleAttrValueMapper.selectSkuSaleAttrValueListBySpu(spuId);

        System.out.println("DEBUG");

        return skuInfoList;
    }

    @Override
    public List<SpuImage> getSpuImageListBySpuId(String spuId) {
        return spuImageList(spuId);
    }

    @Override
    public List<SpuSaleAttr> getSaleAttrListBySpuId(String spuId) {
        return spuSaleAttrList(spuId);
    }
}
