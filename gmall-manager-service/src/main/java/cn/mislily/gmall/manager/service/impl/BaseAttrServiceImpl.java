package cn.mislily.gmall.manager.service.impl;

import cn.mislily.gmall.UpdadeEntityOperator;
import cn.mislily.gmall.bean.BaseAttrInfo;
import cn.mislily.gmall.bean.BaseAttrValue;
import cn.mislily.gmall.manager.mapper.AttributeInfoMapper;
import cn.mislily.gmall.manager.mapper.AttributeValueMapper;
import cn.mislily.gmall.service.BaseAttrService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class BaseAttrServiceImpl implements BaseAttrService {

    @Autowired
    private AttributeInfoMapper attributeInfoMapper;

    @Autowired
    private AttributeValueMapper attributeValueMapper;


    //==== AttributeInfo ====

    /**
     * 根据 AttributeInfo 的 Id 查询
     * @param id
     * @return
     */
    @Override
    public BaseAttrInfo getAttrInfoById(String id) {

        BaseAttrInfo baseAttrInfo = attributeInfoMapper.selectByPrimaryKey(id);

        // 查询 属性名 对应的 属性值 列表
        List<BaseAttrValue> attrValueList = getBaseAttrValueListByBaseAttrInfoId(baseAttrInfo.getId());

        // 将属性值列表添加
        baseAttrInfo.setAttrValueList(attrValueList);

        return baseAttrInfo;
    }

    /**
     * 根据 三级列表 id 获取 属性列表
     * @param catalog3Id 三级列表 id
     * @return
     */
    @Override
    public List<BaseAttrInfo> getBaseAttrInfoListByCatalog3Id(String catalog3Id) {

        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(catalog3Id);

        // 获取
        List<BaseAttrInfo> baseAttrInfoList = attributeInfoMapper.select(baseAttrInfo);

        // 填充属性值
        for (BaseAttrInfo attributeInfo : baseAttrInfoList) {
            // 查询 属性名 对应的 属性值 列表
            List<BaseAttrValue> attrValueList = getBaseAttrValueListByBaseAttrInfoId(attributeInfo.getId());
            // 将属性值列表添加
            attributeInfo.setAttrValueList(attrValueList);
        }
        return baseAttrInfoList;
    }

    /**
     * 删除 属性名 的所有信息
     * @param baseAttrInfoId 属性名 id
     * @return
     */
    @Override
    public Integer deleteAttributeInfoById(String baseAttrInfoId) {

        // 删除 属性值
        deleteBaseAttrValuesByBaseAttrInfoId(baseAttrInfoId);

        // 删除 属性名
        Integer changedRow = attributeInfoMapper.deleteByPrimaryKey(baseAttrInfoId);

        return changedRow;
    }

    /**
     * 保存 属性名 的所有信息
     * @param baseAttrInfoId
     */
    @Override
    public Integer saveBaseAttrInfo(BaseAttrInfo baseAttrInfoId) {

        Integer changedRow = attributeInfoMapper.insertSelective(baseAttrInfoId);

        saveBaseAttrValuesByList(baseAttrInfoId.getAttrValueList(), baseAttrInfoId.getId(), true);

        return changedRow;
    }

    /**
     * 更新 属性名 的所有信息
     * @param attributeInfo
     */
    @Override
    public Integer updateBaseAttrInfo(BaseAttrInfo attributeInfo) {

        Integer changedRow = attributeInfoMapper.updateByPrimaryKeySelective(attributeInfo);

        updateBaseAttrValues(attributeInfo);

        return changedRow;
    }


    //==== AttributeValue ====

    /**
     * 获取 属性值 列表
     *
     * @param baseAttrInfoId 属性名 id
     * @return 返回 属性值 列表
     */
    @Override
    public List<BaseAttrValue> getBaseAttrValueListByBaseAttrInfoId(String baseAttrInfoId) {

        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(baseAttrInfoId);

        List<BaseAttrValue> baseAttrValueList = attributeValueMapper.select(baseAttrValue);

        return baseAttrValueList;
    }

    /**
     * 根据 属性名 id 删除 属性值
     *
     * @param baseAttrInfoId 属性名 id
     */
    @Override
    public Integer deleteBaseAttrValuesByBaseAttrInfoId(String baseAttrInfoId) {

        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(baseAttrInfoId);

        Integer changedRow = attributeValueMapper.delete(baseAttrValue);

        return changedRow;
    }

    /**
     * 根据 属性信息 更新属性列表
     * 包括 增 删 改 操作
     *
     * @param baseAttrInfo
     */
    @Override
    public void updateBaseAttrValues(BaseAttrInfo baseAttrInfo) {

        List<BaseAttrValue> inputAttributeValueList = baseAttrInfo.getAttrValueList();

        if (inputAttributeValueList == null) {
            inputAttributeValueList = new ArrayList<BaseAttrValue>();
        }

        //获取
        List<BaseAttrValue> dbAttributeValueList = getBaseAttrValueList(baseAttrInfo.getId());

        //创建工具对象
        UpdadeEntityOperator<BaseAttrValue> updadeEntityOperator = new UpdadeEntityOperator(dbAttributeValueList, inputAttributeValueList);

        //获取被添加的
        List<BaseAttrValue> added = updadeEntityOperator.getAdded();
        saveBaseAttrValuesByList(added, baseAttrInfo.getId(), true);

        //获取被删除的
        List<BaseAttrValue> deleted = updadeEntityOperator.getDeleted();
        deleteBaseAttrValuesByList(deleted);

        //获取被更新的
        List<BaseAttrValue> updated = updadeEntityOperator.getUpdated();
        updateBaseAttrValuesByList(updated);
    }


    //==== Tools ====

    /**
     * 根据 属性 id 获取 属性值 列表
     *
     * @param baseAttrInfoId
     */
    @Override
    public List<BaseAttrValue> getBaseAttrValueList(String baseAttrInfoId) {
        BaseAttrValue baseAttrValue = new BaseAttrValue();
        baseAttrValue.setAttrId(baseAttrInfoId);

        List<BaseAttrValue> baseAttrValueList = attributeValueMapper.select(baseAttrValue);

        return baseAttrValueList;
    }

    /**
     * 根据 列表 保存 属性值
     *
     * @param baseAttrValueList
     * @param baseAttrInfoId
     * @param isEnabled
     */
    @Override
    public void saveBaseAttrValuesByList(List<BaseAttrValue> baseAttrValueList, String baseAttrInfoId, Boolean isEnabled) {
        if (baseAttrValueList != null) {
            //遍历插入值
            for (BaseAttrValue baseAttrValue : baseAttrValueList) {
                baseAttrValue.setAttrId(baseAttrInfoId);
                baseAttrValue.setIsEnabled(isEnabled ? "1" : "0");
                attributeValueMapper.insert(baseAttrValue);
            }
        }
    }

    /**
     * 根据 列表 更新 属性值
     *
     * @param baseAttrValueList
     */
    @Override
    public void updateBaseAttrValuesByList(List<BaseAttrValue> baseAttrValueList) {
        if (baseAttrValueList != null) {
            //遍历更新值
            for (BaseAttrValue baseAttrValue : baseAttrValueList) {
                attributeValueMapper.updateByPrimaryKey(baseAttrValue);
            }
        }
    }

    /**
     * 根据 列表 删除 属性值
     *
     * @param baseAttrValueList
     */
    @Override
    public void deleteBaseAttrValuesByList(List<BaseAttrValue> baseAttrValueList) {
        if (baseAttrValueList != null) {
            //遍历删除值
            for (BaseAttrValue baseAttrValue : baseAttrValueList) {
                attributeValueMapper.deleteByPrimaryKey(baseAttrValue);
            }
        }
    }

    /**
     * 获取 与 属性值 id 相关的信息
     * @param valueIds
     * @return
     */
    @Override
    public List<BaseAttrInfo> getBaseAttrValuesByValueIds(Set<String> valueIds) {

        //String join = StringUtils.join(valueIds, ",");

        List<BaseAttrInfo> baseAttrInfoList = attributeValueMapper.selectAttrListByValueIds(valueIds);

        return baseAttrInfoList;
    }
}
