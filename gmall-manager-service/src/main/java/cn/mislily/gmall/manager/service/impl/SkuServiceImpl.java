package cn.mislily.gmall.manager.service.impl;

import cn.mislily.gmall.UpdadeEntityOperator;
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
import org.apache.commons.lang3.StringUtils;
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


    //==== skuInfo ====

    /**
     * 根据 三级分类 id 获取 商品信息 列表
     *
     * @param catalog3Id
     * @return
     */
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

    /**
     * 通过 spuId 获取 完整 商品信息 的列表
     *
     * @param spuId
     * @return
     */
    @Override
    public List<SkuInfo> getSkuInfoListBySpuId(String spuId) {

        // 创建查找对象
        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setSpuId(spuId);

        // 获取基本信息
        List<SkuInfo> skuInfoList = skuInfoMapper.select(skuInfo);

        // 填充信息
        if (skuInfoList != null && skuInfoList.size() > 0) {
            for (SkuInfo info : skuInfoList) {

                // 商品 图片信息
                List<SkuImage> skuImageList = getSkuImageListBySkuInfoId(info.getId());

                // 商品 属性值信息
                List<SkuSaleAttrValue> skuSaleAttrValueList = getSkuSaleAttrValueListBySkuInfoId(info.getId());

                info.setSkuImageList(skuImageList);
                info.setSkuSaleAttrValueList(skuSaleAttrValueList);
            }
        }
        return skuInfoList;
    }

    /**
     * 保存 skuInfo 信息
     *
     * @param skuInfo
     */
    @Override
    public void saveSkuInfo(SkuInfo skuInfo) {

        // 保存 基础信息
        skuInfoMapper.insertSelective(skuInfo);
        String skuInfoId = skuInfo.getId();

        // 保存 商品图片
        saveSkuImagesByList(skuInfo.getSkuImageList(), skuInfoId);

        // 保存 销售属性
        saveSkuAttrValuesByList(skuInfo.getSkuAttrValueList(), skuInfoId);

        // 保存 销售属性值
        saveSkuSaleAttrValuesByList(skuInfo.getSkuSaleAttrValueList(), skuInfoId);
    }

    /**
     *
     *
     * @param skuId
     * @return
     */
    @Override
    public SkuInfo getSkuInfoById(String skuInfoId) {

        Jedis jedis = null;
        SkuInfo skuInfo = null;

        try {
            jedis = redisUtil.getJedis();
        } catch (Exception e) {
            return null;
        }

        // 查询redis缓存
        String val = getSkuInfoByIdFormRedis(jedis, skuInfoId);

        if ("empty".equals(val)) {
            System.out.println(Thread.currentThread().getName() + "发现数据库中暂时没有改商品，直接返回空对象");
            return skuInfo;
        }

        if (StringUtils.isBlank(val)) {
            // 缓存未命中
            System.out.println(Thread.currentThread().getName() + "发现缓存中没有数据，申请分布式锁");
            // 申请缓存锁
            String OK = jedis.set("sku:" + skuInfoId + ":lock", "1", "nx", "px", 5000);

            if ("OK".equals(OK)) {// 拿到缓存锁
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "获得分布式锁，开始访问数据");
                // 查询db
                skuInfo = getSkuInfoByIdFormDb(skuInfoId);

                if (skuInfo != null) {
                    System.out.println(Thread.currentThread().getName() + "通过分布式锁，查询到数据，同步缓存");
                    String key = "sku:" + skuInfoId + ":info";
                    // 同步缓存
                    jedis.set(key, JSON.toJSONString(skuInfo));

                } else {
                    // 通知同伴
                    System.out.println(Thread.currentThread().getName() + "通过分布式锁，没有查询到数据，通知同伴在10秒之内不要访问该sku");
                    jedis.setex("sku:" + skuInfoId + ":info", 10, "empty");
                }

                // 归还缓存锁
                System.out.println(Thread.currentThread().getName() + "归还分布式锁");
                jedis.del("sku:" + skuInfoId + ":lock");

            } else {
                // 没有拿到缓存锁 自旋
                System.out.println(Thread.currentThread().getName() + "发现分布式锁被占用，开始自旋");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getSkuInfoById(skuInfoId);
            }

        } else {
            // 缓存 命中
            System.out.println(Thread.currentThread().getName() + "正常从缓存中取得数据，返回结果");
            skuInfo = JSON.parseObject(val, SkuInfo.class);
        }
        return skuInfo;
    }

    /**
     *
     *
     * @param skuInfoId
     */
    @Override
    public void deleteSkuInfo(String skuInfoId) {

        SkuInfo skuInfo = getSkuInfoByIdFormDb(skuInfoId);

        if (skuInfo != null) {

            // 获取 图片列表
            List<SkuImage> skuImageList = skuInfo.getSkuImageList();
            deleteSkuImagesByList(skuImageList);

            // 获取 属性列表
            List<SkuAttrValue> skuAttrValueList = skuInfo.getSkuAttrValueList();
            deleteSkuAttrValuesByList(skuAttrValueList);

            // 获取 销售属性列表
            List<SkuSaleAttrValue> skuSaleAttrValueList = skuInfo.getSkuSaleAttrValueList();
            deleteSkuSaleAttrValuesByList(skuSaleAttrValueList);

            // 删除 基础信息
            skuInfoMapper.deleteByPrimaryKey(skuInfoId);
        }
    }

    /**
     * 从 Redis 获取 skuInfo 数据
     *
     * @param skuInfoId
     * @return
     */
    private String getSkuInfoByIdFormRedis(Jedis jedis, String skuInfoId) {

        // 查询redis缓存
        String key = "sku:" + skuInfoId + ":info";
        String val = jedis.get(key);

        return val;
    }

    /**
     * 从数据库中获取 SkuInfo
     *
     * @param skuId
     * @return
     */
    private SkuInfo getSkuInfoByIdFormDb(String skuInfoId) {

        SkuInfo skuInfo = new SkuInfo();
        skuInfo.setId(skuInfoId);

        // sku 基础信息
        skuInfo = skuInfoMapper.selectOne(skuInfo);

        // sku 图片列表
        List<SkuImage> skuImageList = getSkuImageListBySkuInfoId(skuInfoId);
        skuInfo.setSkuImageList(skuImageList);

        // sku 销售属性值
        List<SkuSaleAttrValue> skuSaleAttrValuesList = getSkuSaleAttrValueListBySkuInfoId(skuInfoId);
        skuInfo.setSkuSaleAttrValueList(skuSaleAttrValuesList);

        return skuInfo;
    }

    /**
     * 更新 skuInfo
     *
     * @param skuInfo
     */
    @Override
    public void updateSkuInfo(SkuInfo skuInfo) {

        String skuInfoId = skuInfo.getId();

        SkuInfo dbSkuInfo = null;

        dbSkuInfo = getSkuInfoByIdFormDb(skuInfoId);

        if (dbSkuInfo != null) {

            // 更新基本信息
            skuInfoMapper.updateByPrimaryKey(skuInfo);

            // 更新图片信息
            List<SkuImage> dbSkuImageList = dbSkuInfo.getSkuImageList();
            List<SkuImage> inputImageList = skuInfo.getSkuImageList();
            UpdadeEntityOperator<SkuImage> skuImageUpdadeEntityOperator = new UpdadeEntityOperator(dbSkuImageList, inputImageList);

            saveSkuImagesByList(skuImageUpdadeEntityOperator.getAdded(), skuInfoId);
            updateSkuImagesByList(skuImageUpdadeEntityOperator.getUpdated(), skuInfoId);
            deleteSkuImagesByList(skuImageUpdadeEntityOperator.getDeleted());

            // 更新属性信息
            List<SkuAttrValue> dbSkuAttrValueList = dbSkuInfo.getSkuAttrValueList();
            List<SkuAttrValue> inputSkuAttrValueList = skuInfo.getSkuAttrValueList();
            UpdadeEntityOperator<SkuAttrValue> skuAttrValueUpdadeEntityOperator = new UpdadeEntityOperator(dbSkuAttrValueList, inputSkuAttrValueList);

            saveSkuAttrValuesByList(skuAttrValueUpdadeEntityOperator.getAdded(), skuInfoId);
            updateSkuAttrValuesByList(skuAttrValueUpdadeEntityOperator.getUpdated(), skuInfoId);
            deleteSkuAttrValuesByList(skuAttrValueUpdadeEntityOperator.getDeleted());

            // 更新属性值信息
            List<SkuSaleAttrValue> dbSkuSaleAttrValueLsit = dbSkuInfo.getSkuSaleAttrValueList();
            List<SkuSaleAttrValue> inputSkuSaleAttrValueLsit = skuInfo.getSkuSaleAttrValueList();
            UpdadeEntityOperator<SkuSaleAttrValue> skuSaleAttrValueUpdadeEntityOperator = new UpdadeEntityOperator(dbSkuSaleAttrValueLsit, inputSkuSaleAttrValueLsit);

            saveSkuSaleAttrValuesByList(skuSaleAttrValueUpdadeEntityOperator.getAdded(), skuInfoId);
            updateSkuSaleAttrValuesByList(skuSaleAttrValueUpdadeEntityOperator.getUpdated(), skuInfoId);
            deleteSkuSaleAttrValuesByList(skuSaleAttrValueUpdadeEntityOperator.getDeleted());

        } else {
            // 抛出异常
        }
    }

    //==== skuImage ====

    /**
     * 获取
     *
     * @param skInfoId
     * @return
     */
    @Override
    public List<SkuImage> getSkuImageListBySkuInfoId(String skInfoId) {

        SkuImage skuImage = new SkuImage();
        skuImage.setSkuId(skInfoId);

        // sku 图片列表
        List<SkuImage> skuImageList = skuImageMapper.select(skuImage);

        return skuImageList;
    }

    /**
     * 获取 skuInfo 对应的所有 商品图片信息
     *
     * @param skuImageList
     * @param skuInfoId
     * @return
     */
    @Override
    public Integer saveSkuImagesByList(List<SkuImage> skuImageList, String skuInfoId) {

        if (skuImageList != null && skuImageList.size() > 0) {

            Integer changedRow = 0;

            for (SkuImage skuImage : skuImageList) {

                skuImage.setSkuId(skuInfoId);

                changedRow = changedRow + skuImageMapper.insert(skuImage);
            }
            return changedRow;
        }
        return 0;
    }

    /**
     * 更新 skuInfo 对应的所有 商品图片信息
     *
     * @param skuImageList
     * @param skuInfoId
     * @return
     */
    @Override
    public Integer updateSkuImagesByList(List<SkuImage> skuImageList, String skuInfoId) {

        if (skuImageList != null && skuImageList.size() > 0) {

            Integer changedRow = 0;

            for (SkuImage skuImage : skuImageList) {

                skuImage.setSkuId(skuInfoId);

                changedRow = changedRow + skuImageMapper.updateByPrimaryKey(skuImage);
            }
            return changedRow;
        }
        return 0;
    }

    /**
     * 删除 skuInfo 对应的所有 商品图片信息
     *
     * @param skuImageList
     * @return
     */
    @Override
    public Integer deleteSkuImagesByList(List<SkuImage> skuImageList) {

        if (skuImageList != null && skuImageList.size() > 0) {

            Integer changedRow = 0;

            for (SkuImage skuImage : skuImageList) {

                changedRow = changedRow + skuImageMapper.deleteByPrimaryKey(skuImage.getId());
            }
            return changedRow;
        }
        return 0;
    }


    //==== skuSaleAttr ====

    /**
     * 获取 skuInfo 对应的所有 销售属性
     *
     * @param skuInfoId
     * @return
     */
    @Override
    public List<SkuAttrValue> getSkuAttrValueListBySkuInfoId(String skuInfoId) {

        SkuAttrValue SkuAttrValue = new SkuAttrValue();
        SkuAttrValue.setSkuId(skuInfoId);

        // sku 销售属性值
        List<SkuAttrValue> skuSkuAttrValuesList = skuAttrValueMapper.select(SkuAttrValue);

        return skuSkuAttrValuesList;
    }

    /**
     * 保存 商品销售属性 信息
     *
     * @param skuSaleAttrValueList
     * @param skuInfoId
     * @return
     */
    @Override
    public Integer saveSkuAttrValuesByList(List<SkuAttrValue> skuSkuAttrValueList, String skuInfoId) {

        if (skuSkuAttrValueList != null && skuSkuAttrValueList.size() > 0) {

            Integer changedRow = 0;

            for (SkuAttrValue skuAttrValue : skuSkuAttrValueList) {
                skuAttrValue.setSkuId(skuInfoId);
                changedRow = changedRow + skuAttrValueMapper.insert(skuAttrValue);
            }
            return changedRow;
        }
        return 0;
    }

    /**
     * 更新 商品销售属性 信息
     *
     * @param skuSaleAttrValueList
     * @param skuInfoId
     * @return
     */
    @Override
    public Integer updateSkuAttrValuesByList(List<SkuAttrValue> skuSkuAttrValueList, String skuInfoId) {

        if (skuSkuAttrValueList != null && skuSkuAttrValueList.size() > 0) {

            Integer changedRow = 0;

            for (SkuAttrValue skuAttrValue : skuSkuAttrValueList) {
                skuAttrValue.setSkuId(skuInfoId);
                changedRow = changedRow + skuAttrValueMapper.updateByPrimaryKey(skuAttrValue);
            }
            return changedRow;
        }
        return 0;
    }

    /**
     * 删除 商品销售属性 信息
     *
     * @param skuSaleAttrValueList
     * @return
     */
    @Override
    public Integer deleteSkuAttrValuesByList(List<SkuAttrValue> skuSkuAttrValueList) {

        if (skuSkuAttrValueList != null && skuSkuAttrValueList.size() > 0) {

            Integer changedRow = 0;

            for (SkuAttrValue skuAttrValue : skuSkuAttrValueList) {
                changedRow = changedRow + skuAttrValueMapper.deleteByPrimaryKey(skuAttrValue.getId());
            }
            return changedRow;
        }
        return 0;
    }


    //==== skuSaleAttrValue ====

    /**
     * 获取 skuInfo 对应的所有 销售属性值
     *
     * @param skuInfoId
     * @return
     */
    @Override
    public List<SkuSaleAttrValue> getSkuSaleAttrValueListBySkuInfoId(String skuInfoId) {

        SkuSaleAttrValue skuSaleAttrValue = new SkuSaleAttrValue();
        skuSaleAttrValue.setSkuId(skuInfoId);

        // sku 销售属性值
        List<SkuSaleAttrValue> skuSaleAttrValuesList = skuSaleAttrValueMapper.select(skuSaleAttrValue);

        return skuSaleAttrValuesList;
    }

    /**
     * 保存 商品销售属性值 信息
     *
     * @param skuSaleAttrValueList
     * @param skuInfoId
     * @return
     */
    @Override
    public Integer saveSkuSaleAttrValuesByList(List<SkuSaleAttrValue> skuSaleAttrValueList, String skuInfoId) {

        if (skuSaleAttrValueList != null && skuSaleAttrValueList.size() > 0) {

            Integer changedRow = 0;

            for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                skuSaleAttrValue.setSkuId(skuInfoId);
                changedRow = changedRow + skuSaleAttrValueMapper.insert(skuSaleAttrValue);
            }
            return changedRow;
        }
        return 0;
    }

    /**
     * 更新 商品销售属性值 信息
     *
     * @param skuSaleAttrValueList
     * @param skuInfoId
     * @return
     */
    @Override
    public Integer updateSkuSaleAttrValuesByList(List<SkuSaleAttrValue> skuSaleAttrValueList, String skuInfoId) {

        if (skuSaleAttrValueList != null && skuSaleAttrValueList.size() > 0) {

            Integer changedRow = 0;

            for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                skuSaleAttrValue.setSkuId(skuInfoId);
                changedRow = changedRow + skuSaleAttrValueMapper.updateByPrimaryKey(skuSaleAttrValue);
            }
            return changedRow;
        }
        return 0;
    }

    /**
     * 删除 商品销售属性值 信息
     *
     * @param skuSaleAttrValueList
     * @return
     */
    @Override
    public Integer deleteSkuSaleAttrValuesByList(List<SkuSaleAttrValue> skuSaleAttrValueList) {

        if (skuSaleAttrValueList != null && skuSaleAttrValueList.size() > 0) {

            Integer changedRow = 0;

            for (SkuSaleAttrValue skuSaleAttrValue : skuSaleAttrValueList) {
                changedRow = changedRow + skuSaleAttrValueMapper.deleteByPrimaryKey(skuSaleAttrValue.getId());
            }
            return changedRow;
        }
        return 0;
    }
}
