package cn.mislily.gmall.manager.service.impl;

import cn.mislily.gmall.bean.BaseAttrInfo;
import cn.mislily.gmall.bean.BaseAttrValue;
import cn.mislily.gmall.manager.mapper.AttributeInfoMapper;
import cn.mislily.gmall.manager.mapper.AttributeValueMapper;
import cn.mislily.gmall.service.AttributeService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeInfoMapper attributeInfoMapper;

    @Autowired
    private AttributeValueMapper attributeValueMapper;

    @Override
    public List<BaseAttrInfo> attributeInfoList(String catalog3Id) {

        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(catalog3Id);

        List<BaseAttrInfo> attributeInfoList = attributeInfoMapper.select(baseAttrInfo);

        for (BaseAttrInfo attributeInfo : attributeInfoList) {
            // 创建
            BaseAttrValue attributeValue = new BaseAttrValue();
            // 获取 属性名 id
            attributeValue.setAttrId(attributeInfo.getId());
            // 查询 属性名 对应的 属性值
            List<BaseAttrValue> attrValueList = attributeValueMapper.select(attributeValue);
            // 将属性值列表添加
            attributeInfo.setAttrValueList(attrValueList);
        }
        return attributeInfoList;
    }

    @Override
    public Integer deleteAttributeInfo(String attributeInfoId) {

        BaseAttrValue attributeValue = new BaseAttrValue();
        attributeValue.setAttrId(attributeInfoId);

        // 删除 属性值
        Integer changedRow = attributeValueMapper.delete(attributeValue);

        // 删除 属性名
        attributeInfoMapper.deleteByPrimaryKey(attributeInfoId);

        return changedRow;
    }


    //
    @Override
    public void saveAttribute(BaseAttrInfo attributeInfo) {

        //这一步会把数据查出来
        attributeInfoMapper.insertSelective(attributeInfo);

        //
        List<BaseAttrValue> attributeValueList = attributeInfo.getAttrValueList();

        //遍历插入值
        for (BaseAttrValue attrValue : attributeValueList){
            attrValue.setAttrId(attributeInfo.getId());
            attrValue.setIsEnabled("1");
            attributeValueMapper.insert(attrValue);
        }
    }


}
