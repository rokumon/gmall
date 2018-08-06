package cn.mislily.gmall.service;

import cn.mislily.gmall.bean.SkuInfo;

import java.util.List;

public interface SkuService {
    List<SkuInfo> skuList(String spuId);
}
