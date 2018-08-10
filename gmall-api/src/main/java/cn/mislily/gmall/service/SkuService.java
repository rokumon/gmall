package cn.mislily.gmall.service;

import cn.mislily.gmall.bean.SkuInfo;

import java.util.List;

public interface SkuService {

    List<SkuInfo> skuList(String spuId);

    void saveSku(SkuInfo skuInfo);

    public SkuInfo skuInfo(String skuId);

    public void deleteSkuInfo(SkuInfo skuId);

    List<SkuInfo> getSkuListByCatalog3Id(String s);
}
