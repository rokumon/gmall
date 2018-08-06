package cn.mislily.gmall.manager.service.impl;

import cn.mislily.gmall.bean.*;
import cn.mislily.gmall.manager.mapper.*;
import cn.mislily.gmall.service.SpuService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

}
