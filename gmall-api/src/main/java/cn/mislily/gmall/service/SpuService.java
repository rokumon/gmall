package cn.mislily.gmall.service;

import cn.mislily.gmall.bean.*;

import java.util.List;

public interface SpuService {

    //SpuInfo
    public List<SpuInfo> spuList(String catalog3Id) ;

    public void saveSpuInfo(SpuInfo spuInfo) ;

    // SpuSaleAttr
    public void saveSaleAttrsByList(List<SpuSaleAttr> spuSaleAttrList, String spuId);

    // SpuSaleAttrValue
    public void saveSpuSaleAttrValueByList(List<SpuSaleAttrValue> spuSaleAttrValueList, String spuId);

    // BaseSaleAttr
    public List<BaseSaleAttr> baseSaleAttrList() ;

    // SpuImage
    public void saveSpuImageByList(List<SpuImage> spuImageList, String spuId);
}
