package cn.mislily.gmall.manager.service.impl;

import cn.mislily.gmall.bean.SkuAttrValue;
import cn.mislily.gmall.bean.SkuImage;
import cn.mislily.gmall.bean.SkuInfo;
import cn.mislily.gmall.bean.SkuSaleAttrValue;
import cn.mislily.gmall.manager.mapper.SkuAttrValueMapper;
import cn.mislily.gmall.manager.mapper.SkuImageMapper;
import cn.mislily.gmall.manager.mapper.SkuInfoMapper;
import cn.mislily.gmall.manager.mapper.SkuSaleAttrValueMapper;
import cn.mislily.gmall.service.SkuService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    @Autowired
    private SkuAttrValueMapper skuAttrValueMapper;

    @Autowired
    private SkuSaleAttrValueMapper skuSaleAttrValueMapper;

    @Autowired
    private SkuImageMapper skuImageMapper;


    // sku

    /**
     * 通过 spuId 获取 SkuInfo 的列表
     * @param spuId
     * @return
     */
    @Override
    public List<SkuInfo> skuList(String spuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setSpuId(spuId);
        List<SkuInfo> skuInfoList = skuInfoMapper.select(skuInfo);
        return skuInfoList;
    }

    public void saveSku(SkuInfo skuInfo) {

        skuInfoMapper.insertSelective(skuInfo);

        String skuId = skuInfo.getId();

        List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();

        for (SkuAttrValue skuAttrValue : skuAttrValueList) {
            skuAttrValue.setSkuId(skuId);
            skuAttrValueMapper.insert(skuAttrValue);
        }

        List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();

        for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
            skuSaleAttrValue.setSkuId(skuId);
            skuSaleAttrValueMapper.insert(skuSaleAttrValue);
        }


        List<SkuImage> skuImageList = skuInfo.getSkuImageList();
        for (SkuImage skuImage : skuImageList) {
            skuImage.setSkuId(skuId);
            skuImageMapper.insert(skuImage);
        }
    }

    @Override
    public SkuInfo skuInfo(String skuId) {
        return null;
    }

    @Override
    public void deleteSkuInfo(SkuInfo skuId) {

    }

    // skuImage

}
