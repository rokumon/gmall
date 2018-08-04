package cn.mislily.gmall.service;

import cn.mislily.gmall.bean.BaseSaleAttr;
import cn.mislily.gmall.bean.SpuInfo;

import java.util.List;

public interface SpuService {
    List<BaseSaleAttr> baseSaleAttrList();

    List<SpuInfo> spuList(String catalog3Id);

    void saveSpuInfo(SpuInfo spuInfo);
}
