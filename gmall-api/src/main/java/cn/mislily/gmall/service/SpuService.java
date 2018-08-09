package cn.mislily.gmall.service;

import cn.mislily.gmall.bean.*;

import java.util.List;
import java.util.Map;

public interface SpuService {

    //SpuInfo
    public List<SpuInfo> spuList(String catalog3Id) ;

    public void saveSpuInfo(SpuInfo spuInfo) ;

    public void deleteSpuInfo(String spuId);

    public void updateSpuInfo(SpuInfo spuInfo);

    public SpuInfo spuInfo(String spuId);


    // SpuSaleAttr
    public void saveSaleAttrsByList(List<SpuSaleAttr> spuSaleAttrList, String spuId);


    // SpuSaleAttrValue
    public void saveSpuSaleAttrValueByList(List<SpuSaleAttrValue> spuSaleAttrValueList, String spuId);


    // BaseSaleAttr
    public List<BaseSaleAttr> baseSaleAttrList() ;


    // SpuImage
    public void saveSpuImageByList(List<SpuImage> spuImageList, String spuId);

    public void deleteSpuImage(String imageUrl);


    List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Map<String, String> stringStringHashMap);

    List<SkuInfo> getSkuSaleAttrValueListBySpu(String spuId);

    List<SpuImage> getSpuImageListBySpuId(String spuId);

    List<SpuSaleAttr> getSaleAttrListBySpuId(String spuId);
}
