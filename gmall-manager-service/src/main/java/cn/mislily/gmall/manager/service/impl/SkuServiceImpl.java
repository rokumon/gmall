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

        for (SkuInfo info : skuInfoList) {

            List<SkuImage> skuImageList = skuImageList(info.getId());
            List<SkuSaleAttrValue> skuSaleAttrValueList = skuSaleAttrValue(info.getId());

            info.setSkuImageList(skuImageList);
            info.setSkuSaleAttrValueList(skuSaleAttrValueList);
        }

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

        /*
        Jedis jedis = null;
        try {
            jedis = redisUtil.getJedis();
        }catch (Exception e){
            return null;
        }
        SkuInfo skuInfo = null;

        // 查询redis缓存
        String key = "sku:" + skuId + ":info";
        String val = jedis.get(key);

        if("empty".equals(val)){
            System.out.println(Thread.currentThread().getName()+"发现数据库中暂时没有改商品，直接返回空对象");
            return skuInfo;
        }


        if (StringUtils.isBlank(val)) {
            System.out.println(Thread.currentThread().getName()+"发现缓存中没有数据，申请分布式锁");
            // 申请缓存锁
            String OK = jedis.set("sku:" + skuId + ":lock", "1", "nx", "px", 5000);

            if("OK".equals(OK)){// 拿到缓存锁
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName()+"获得分布式锁，开始访问数据");
                // 查询db
                skuInfo = getSkuByIdFormDb(skuId);

                if(skuInfo!=null){
                    System.out.println(Thread.currentThread().getName()+"通过分布式锁，查询到数据，同步缓存");
                    // 同步缓存
                    jedis.set(key, JSON.toJSONString(skuInfo));

                }else{
                    // 通知同伴
                    System.out.println(Thread.currentThread().getName()+"通过分布式锁，没有查询到数据，通知同伴在10秒之内不要访问该sku");
                    jedis.setex("sku:" + skuId + ":info", 10,"empty");
                }

                // 归还缓存锁
                System.out.println(Thread.currentThread().getName()+"归还分布式锁");
                jedis.del("sku:" + skuId + ":lock");

            }else{// 没有拿到缓存锁
                // 自旋
                System.out.println(Thread.currentThread().getName()+"发现分布式锁被占用，开始自旋");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getSkuById(skuId);
            }

        }else{
            // 正常转换缓存数据
            System.out.println(Thread.currentThread().getName()+"正常从缓存中取得数据，返回结果");
            skuInfo = JSON.parseObject(val, SkuInfo.class);
        }

        return skuInfo;
        */


        Jedis jedis = redisUtil.getJedis();
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuId);

        // key 的 形式
        // sku:70:info
        String key = "sku:" + skuId + ":info";

        // 查询 redis 缓存
        String val = jedis.get(key);

        // 转换成为 json 对象
        skuInfo = JSON.parseObject(val, SkuInfo.class);

        // 如果 Json 对象为 null,则说明缓存未命中，要去DB查询
        if (skuInfo == null) {
            // 查询db
            skuInfo = getSkuByIdFormDb(skuId);

            // 同步缓存
            jedis.set(key, JSON.toJSONString(skuInfo));
        }

        // 返回查询结果
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

        // sku 基础信息
        skuInfo = skuInfoMapper.selectOne(skuInfo);


        // sku 图片列表
        List<SkuImage> skuImageList = skuImageList(skuId);
        skuInfo.setSkuImageList(skuImageList);

        
        // sku 销售属性值
        List<SkuSaleAttrValue> skuSaleAttrValuesList = skuSaleAttrValue(skuId);
        skuInfo.setSkuSaleAttrValueList(skuSaleAttrValuesList);

        return skuInfo;
    }

    // skuImage
    public List<SkuImage> skuImageList(String skuId){

        SkuImage skuImage = new SkuImage();
        skuImage.setSkuId(skuId);

        // sku 图片列表
        List<SkuImage> skuImageList = skuImageMapper.select(skuImage);

        return skuImageList;
    }

    // skuSaleAttrValue
    public List<SkuSaleAttrValue> skuSaleAttrValue(String skuId){

        SkuSaleAttrValue skuSaleAttrValue = new SkuSaleAttrValue();
        skuSaleAttrValue.setSkuId(skuId);

        // sku 销售属性值
        List<SkuSaleAttrValue> skuSaleAttrValuesList = skuSaleAttrValueMapper.select(skuSaleAttrValue);

        return skuSaleAttrValuesList;
    }

}
