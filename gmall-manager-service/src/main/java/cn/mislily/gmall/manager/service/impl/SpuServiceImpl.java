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
        List<SpuInfo> select = spuInfoMapper.select(spuInfo);
        return select;
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

    @Override
    public SpuInfo spuInfo(String spuId) {

        SpuInfo spuInfo = new SpuInfo();
        spuInfo.setId(spuId);

        spuInfo = spuInfoMapper.selectOne(spuInfo);

        List<SpuImage> spuImageList = spuImageList(spuId);

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

                    spuSaleAttr.setSpuSaleAttrValueJson(spuSaleAttrValueJson);
                }
            }
        }

        spuInfo.setSpuImageList(spuImageList);
        spuInfo.setSpuSaleAttrList(spuSaleAttrList);

        return spuInfo;
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

    public List<SpuSaleAttr> spuSaleAttrList(String spuId){

        SpuSaleAttr spuSaleAttr = new SpuSaleAttr();
        spuSaleAttr.setSpuId(spuId);

        List<SpuSaleAttr> spuSaleAttrList = spuSaleAttrMapper.select(spuSaleAttr);

        if (spuSaleAttrList != null) {

            for (SpuSaleAttr spuSaleAttrObj:spuSaleAttrList){
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
        for (SpuSaleAttrValue spuSaleAttrValue : spuSaleAttrValueList) {
            spuSaleAttrValue.setSpuId(spuId);
            spuSaleAttrValueMapper.insert(spuSaleAttrValue);
        }
    }

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

    public void deleteSpuImage(String imageUrl){
        if(imageUrl != null) {
            SpuImage spuImage = new SpuImage();
            spuImage.setImgUrl(imageUrl);

            spuImageMapper.delete(spuImage);

            FastFDSFileUtils.removeImage(imageUrl);
        }
    }

    @Override
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Map<String, String> stringStringHashMap) {

        return spuSaleAttrValueMapper.selectSpuSaleAttrListCheckBySku(stringStringHashMap);
    }

    @Override
    public List<SkuInfo> getSkuSaleAttrValueListBySpu(String spuId) {

        return spuSaleAttrValueMapper.selectSkuSaleAttrValueListBySpu(spuId);
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
