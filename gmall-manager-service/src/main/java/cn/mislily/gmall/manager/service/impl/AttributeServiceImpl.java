package cn.mislily.gmall.manager.service.impl;

import cn.mislily.gmall.UpdadeEntityOperator;
import cn.mislily.gmall.bean.BaseAttrInfo;
import cn.mislily.gmall.bean.BaseAttrValue;
import cn.mislily.gmall.manager.mapper.AttributeInfoMapper;
import cn.mislily.gmall.manager.mapper.AttributeValueMapper;
import cn.mislily.gmall.service.AttributeService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeInfoMapper attributeInfoMapper;

    @Autowired
    private AttributeValueMapper attributeValueMapper;


    //AttributeInfo

    /**
     * 根据 三级列表 id 获取 属性列表
     *
     * @param catalog3Id 三级列表 id
     * @return
     */
    @Override
    public List<BaseAttrInfo> attributeInfoList(String catalog3Id) {

        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(catalog3Id);

        List<BaseAttrInfo> attributeInfoList = attributeInfoMapper.select(baseAttrInfo);

        for (BaseAttrInfo attributeInfo : attributeInfoList) {
            // 查询 属性名 对应的 属性值 列表
            List<BaseAttrValue> attrValueList = attributeValueList(attributeInfo.getId());
            // 将属性值列表添加
            attributeInfo.setAttrValueList(attrValueList);
        }
        return attributeInfoList;
    }

    /**
     * 删除 属性名 的所有信息
     *
     * @param attributeInfoId 属性名 id
     * @return
     */
    @Override
    public Integer deleteAttributeInfo(String attributeInfoId) {

        // 删除 属性值
        deleteAttributeValuesByAttributeInfoId(attributeInfoId);

        // 删除 属性名
        Integer changedRow = attributeInfoMapper.deleteByPrimaryKey(attributeInfoId);

        return changedRow;
    }

    @Override
    public void saveAttributeInfo(BaseAttrInfo attributeInfo) {

        attributeInfoMapper.insertSelective(attributeInfo);

        saveAttributeValuesByList(attributeInfo.getAttrValueList(), attributeInfo.getId(), true);
    }

    @Override
    public void updateAttributeInfo(BaseAttrInfo attributeInfo) {

        attributeInfoMapper.updateByPrimaryKeySelective(attributeInfo);

        updateAttributeValues(attributeInfo);
    }


    //AtributeValue

    /**
     * 获取 属性值 列表
     *
     * @param attributeInfoId 属性名 id
     * @return 返回 属性值 列表
     */
    @Override
    public List<BaseAttrValue> attributeValueList(String attributeInfoId) {

        BaseAttrValue attributeValue = new BaseAttrValue();
        attributeValue.setAttrId(attributeInfoId);

        List<BaseAttrValue> attributeValueList = attributeValueMapper.select(attributeValue);

        return attributeValueList;
    }

    /**
     * 根据 属性名 id 删除 属性值
     *
     * @param attributeInfoId 属性名 id
     */
    @Override
    public void deleteAttributeValuesByAttributeInfoId(String attributeInfoId) {

        BaseAttrValue attributeValue = new BaseAttrValue();
        attributeValue.setAttrId(attributeInfoId);

        Integer changedRow = attributeValueMapper.delete(attributeValue);
    }

    @Override
    public void saveAttributeValuesByList(List<BaseAttrValue> attributeValueList, String attrId, Boolean isEnabled) {
        if (attributeValueList != null) {
            //遍历插入值
            for (BaseAttrValue attrValue : attributeValueList) {
                attrValue.setAttrId(attrId);
                attrValue.setIsEnabled(isEnabled ? "1" : "0");
                attributeValueMapper.insert(attrValue);
            }
        }
    }

    @Override
    public void updateAttributeValuesByList(List<BaseAttrValue> attributeValueList) {
        if (attributeValueList != null) {
            //遍历更新值
            for (BaseAttrValue attrValue : attributeValueList) {
                attributeValueMapper.updateByPrimaryKey(attrValue);
            }
        }
    }

    @Override
    public void deleteAttributeValuesByList(List<BaseAttrValue> attributeValueList) {
        if (attributeValueList != null) {
            //遍历删除值
            for (BaseAttrValue attrValue : attributeValueList) {
                attributeValueMapper.deleteByPrimaryKey(attrValue);
            }
        }
    }

    /**
     * @param attributeInfo
     */
    @Override
    public void updateAttributeValues(BaseAttrInfo attributeInfo) {

        List<BaseAttrValue> inputAttributeValueList = attributeInfo.getAttrValueList();

        if (inputAttributeValueList == null) {
            inputAttributeValueList = new ArrayList<BaseAttrValue>();
        }

        //获取
        List<BaseAttrValue> dbAttributeValueList = attributeValueList(attributeInfo.getId());

        //创建工具对象
        UpdadeEntityOperator<BaseAttrValue> updadeEntityOperator = new UpdadeEntityOperator(dbAttributeValueList, inputAttributeValueList);

        //获取被添加的
        List<BaseAttrValue> added = updadeEntityOperator.getAdded();
        saveAttributeValuesByList(added, attributeInfo.getId(), true);

        //获取被删除的
        List<BaseAttrValue> deleted = updadeEntityOperator.getDeleted();
        deleteAttributeValuesByList(deleted);

        //获取被更新的
        List<BaseAttrValue> updated = updadeEntityOperator.getUpdated();
        updateAttributeValuesByList(updated);

        updadeEntityOperator.status();
    }



}
