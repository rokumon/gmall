package cn.mislily.gmall.service;

import cn.mislily.gmall.bean.BaseAttrInfo;
import cn.mislily.gmall.bean.BaseAttrValue;

import java.util.List;
import java.util.Set;

public interface BaseAttrService {

    //==== AttributeInfo ====

    /**
     * 根据 AttributeInfo 的 Id 查询
     * @param id
     * @return
     */
    public BaseAttrInfo getAttrInfoById(String id);

    /**
     * 根据 三级列表 id 获取 属性列表
     * @param catalog3Id 三级列表 id
     * @return
     */
    public List<BaseAttrInfo> getBaseAttrInfoListByCatalog3Id(String catalog3Id);

    /**
     * 删除 属性名 的所有信息
     * @param baseAttrInfoId 属性名 id
     * @return
     */
    public Integer deleteAttributeInfoById(String baseAttrInfoId) ;

    /**
     * 保存 属性名 的所有信息
     * @param baseAttrInfoId
     */
    public Integer saveBaseAttrInfo(BaseAttrInfo baseAttrInfoId);

    /**
     * 更新 属性名 的所有信息
     * @param attributeInfo
     */
    public Integer updateBaseAttrInfo(BaseAttrInfo attributeInfo);


    //==== AttributeValue ====

    /**
     * 获取 属性值 列表
     *
     * @param baseAttrInfoId 属性名 id
     * @return 返回 属性值 列表
     */
    public List<BaseAttrValue> getBaseAttrValueListByBaseAttrInfoId(String baseAttrInfoId) ;

    /**
     * 根据 属性名 id 删除 属性值
     *
     * @param baseAttrInfoId 属性名 id
     */
    public Integer deleteBaseAttrValuesByBaseAttrInfoId(String baseAttrInfoId) ;

    /**
     * 根据 属性信息 更新属性列表
     * 包括 增 删 改 操作
     *
     * @param baseAttrInfo
     */
    public void updateBaseAttrValues(BaseAttrInfo baseAttrInfo);


    //==== Tools ====
    /**
     * 根据  属性值
     *
     * @param baseAttrInfoId
     */
    public List<BaseAttrValue> getBaseAttrValueList(String baseAttrInfoId);

    /**
     * 根据 列表 保存 属性值
     *
     * @param baseAttrValueList
     * @param baseAttrInfoId
     * @param isEnabled
     */
    public void saveBaseAttrValuesByList(List<BaseAttrValue> baseAttrValueList, String baseAttrInfoId, Boolean isEnabled) ;

    /**
     * 根据 列表 更新 属性值
     *
     * @param baseAttrValueList
     */
    public void updateBaseAttrValuesByList(List<BaseAttrValue> baseAttrValueList);

    /**
     * 根据 列表 删除 属性值
     *
     * @param baseAttrValueList
     */
    public void deleteBaseAttrValuesByList(List<BaseAttrValue> baseAttrValueList);


    /**
     *
     * @param valueIds
     * @return
     */
    public List<BaseAttrInfo> getBaseAttrValuesByValueIds(Set<String> valueIds);
}
