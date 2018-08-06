package cn.mislily.gmall.manager.service.impl;

import cn.mislily.gmall.bean.SkuInfo;
import cn.mislily.gmall.manager.mapper.SkuInfoMapper;
import cn.mislily.gmall.service.SkuService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SkuServiceImpl implements SkuService {

    @Autowired
    private SkuInfoMapper skuInfoMapper;

    /**
     * 通过 spuId 获取 SkuInfo 的列表
     * @param spuId
     * @return
     */
    @Override
    public List<SkuInfo> skuList(String spuId) {
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setSpuId(spuId);
        List<SkuInfo> select = skuInfoMapper.select(skuInfo);
        return select;
    }
}
