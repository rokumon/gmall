package cn.mislily.gmall.service;


import cn.mislily.gmall.bean.*;

import java.util.List;
import java.util.Map;

public interface SpuService {

    //==== SpuInfo ====

    public SpuInfo getSpuInfoById(String spuInfoId);
    public List<SpuInfo> getSpuInfoListByCatalog3Id(String catalog3Id);
    public void saveSpuInfo(SpuInfo spuInfo) ;
    public void deleteSpuInfo(String spuInfoId);
    public void updateSpuInfo(SpuInfo spuInfo);

    //==== SpuSaleAttr ====

    public void saveSpuSaleAttrsByList(List<SpuSaleAttr> spuSaleAttrList, String spuId);
    public List<SpuSaleAttr> getSpuSaleAttrsList(String spuId) ;

    //==== SpuSaleAttrValue ====

    public void saveSpuSaleAttrValuesByList(List<SpuSaleAttrValue> spuSaleAttrValueList, String spuId);
    public List<SpuSaleAttrValue> getSpuSaleAttrValues(String saleAttrId, String spuId) ;

    //==== SpuImage ====

    public List<SpuImage> getSpuImagesList(String spuInfoId) ;
    public void saveSpuImagesByList(List<SpuImage> spuImageList, String spuInfoId) ;
    public void deleteSpuImage(String imageUrl);


    //==== BaseSaleAttr ====

    public List<BaseSaleAttr> baseSaleAttrList() ;
    public List<SpuSaleAttr> getSpuSaleAttrListCheckBySku(Map<String, String> stringStringHashMap);
    public List<SkuInfo> getSkuSaleAttrValueListBySpuInfoId(String spuInfoId) ;
    public List<SpuImage> getSpuImageListBySpuId(String spuId);
    public List<SpuSaleAttr> getSaleAttrListBySpuId(String spuId) ;

}
