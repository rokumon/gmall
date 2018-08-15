package cn.mislily.gmall.service;

import cn.mislily.gmall.bean.SkuAttrValue;
import cn.mislily.gmall.bean.SkuImage;
import cn.mislily.gmall.bean.SkuInfo;
import cn.mislily.gmall.bean.SkuSaleAttrValue;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface SkuService {

    //==== skuInfo ====

    /**
     * 根据 三级分类 id 获取 商品信息 列表
     *
     * @param catalog3Id
     * @return
     */
    public List<SkuInfo> getSkuListByCatalog3Id(String catalog3Id);

    /**
     * 通过 spuId 获取 完整 商品信息 的列表
     *
     * @param spuId
     * @return
     */
    public List<SkuInfo> getSkuInfoListBySpuId(String spuId);

    /**
     * 保存 skuInfo 信息
     *
     * @param skuInfo
     */
    public void saveSkuInfo(SkuInfo skuInfo);

    /**
     * @param skuInfoId
     * @return
     */
    public SkuInfo getSkuInfoById(String skuInfoId);

    /**
     * @param skuInfoId
     */
    public void deleteSkuInfo(String skuInfoId);


    /**
     * 更新 skuInfo
     *
     * @param skuInfo
     */
    public void updateSkuInfo(SkuInfo skuInfo);

    //==== skuImage ====

    /**
     * 获取
     *
     * @param skInfoId
     * @return
     */
    public List<SkuImage> getSkuImageListBySkuInfoId(String skInfoId);

    /**
     * 获取 skuInfo 对应的所有 商品图片信息
     *
     * @param skuImageList
     * @param skuInfoId
     * @return
     */
    public Integer saveSkuImagesByList(List<SkuImage> skuImageList, String skuInfoId);

    /**
     * 更新 skuInfo 对应的所有 商品图片信息
     *
     * @param skuImageList
     * @param skuInfoId
     * @return
     */
    public Integer updateSkuImagesByList(List<SkuImage> skuImageList, String skuInfoId);

    /**
     * 删除 skuInfo 对应的所有 商品图片信息
     *
     * @param skuImageList
     * @return
     */
    public Integer deleteSkuImagesByList(List<SkuImage> skuImageList);


    //==== skuSaleAttr ====

    /**
     * 获取 skuInfo 对应的所有 销售属性
     *
     * @param skuInfoId
     * @return
     */
    public List<SkuAttrValue> getSkuAttrValueListBySkuInfoId(String skuInfoId);

    /**
     * 保存 商品销售属性 信息
     *
     * @param skuSkuAttrValueList
     * @param skuInfoId
     * @return
     */
    public Integer saveSkuAttrValuesByList(List<SkuAttrValue> skuSkuAttrValueList, String skuInfoId);

    /**
     * 更新 商品销售属性 信息
     *
     * @param skuSkuAttrValueList
     * @param skuInfoId
     * @return
     */
    public Integer updateSkuAttrValuesByList(List<SkuAttrValue> skuSkuAttrValueList, String skuInfoId);

    /**
     * 删除 商品销售属性 信息
     *
     * @param skuSkuAttrValueList
     * @return
     */
    public Integer deleteSkuAttrValuesByList(List<SkuAttrValue> skuSkuAttrValueList);

    //==== skuSaleAttrValue ====

    /**
     * 获取 skuInfo 对应的所有 销售属性值
     *
     * @param skuInfoId
     * @return
     */
    public List<SkuSaleAttrValue> getSkuSaleAttrValueListBySkuInfoId(String skuInfoId);

    /**
     * 保存 商品销售属性值 信息
     *
     * @param skuSaleAttrValueList
     * @param skuInfoId
     * @return
     */
    public Integer saveSkuSaleAttrValuesByList(List<SkuSaleAttrValue> skuSaleAttrValueList, String skuInfoId);

    /**
     * 更新 商品销售属性值 信息
     *
     * @param skuSaleAttrValueList
     * @param skuInfoId
     * @return
     */
    public Integer updateSkuSaleAttrValuesByList(List<SkuSaleAttrValue> skuSaleAttrValueList, String skuInfoId);

    /**
     * 删除 商品销售属性值 信息
     *
     * @param skuSaleAttrValueList
     * @return
     */
    public Integer deleteSkuSaleAttrValuesByList(List<SkuSaleAttrValue> skuSaleAttrValueList);

}
