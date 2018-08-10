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
import cn.mislily.gmall.util.RedisUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;

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


    @Autowired
    RedisUtil redisUtil;

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

        Jedis jedis = redisUtil.getJedis();
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);

        // 查询redis缓存
        String key = "sku:" + skuId + ":info";
        String val = jedis.get(key);
        skuInfo = JSON.parseObject(val, SkuInfo.class);

        if (skuInfo == null) {
            // 查询db
            skuInfo = getSkuByIdFormDb(skuId);

            // 同步缓存
            jedis.set(key, JSON.toJSONString(skuInfo));
        }


        return skuInfo;
    }

    @Override
    public void deleteSkuInfo(SkuInfo skuId) {

    }

    @Override
    public List<SkuInfo> getSkuListByCatalog3Id(String catalog3Id) {

        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setCatalog3Id(catalog3Id);
        List<SkuInfo> select = skuInfoMapper.select(skuInfo);

        for (SkuInfo info : select) {
            String id = info.getId();

            SkuAttrValue skuAttrValue = new SkuAttrValue();
            skuAttrValue.setSkuId(id);
            List<SkuAttrValue> select1 = skuAttrValueMapper.select(skuAttrValue);

            info.setSkuAttrValueList(select1);
        }

        return select;

    }


    private SkuInfo getSkuByIdFormDb(String skuId) {

        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);
        SkuInfo skuInfo1 = skuInfoMapper.selectOne(skuInfo);

        SkuImage skuImage = new SkuImage();
        skuImage.setSkuId(skuId);
        List<SkuImage> select = skuImageMapper.select(skuImage);

        skuInfo1.setSkuImageList(select);

        SkuSaleAttrValue skuSaleAttrValue = new SkuSaleAttrValue();
        skuSaleAttrValue.setSkuId(skuId);
        List<SkuSaleAttrValue> select1 = skuSaleAttrValueMapper.select(skuSaleAttrValue);
        skuInfo1.setSkuSaleAttrValueList(select1);

        return skuInfo1;
    }

    // skuImage

}
