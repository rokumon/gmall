package cn.mislily.gmall.service;

import cn.mislily.gmall.bean.BaseAttrInfo;
import cn.mislily.gmall.bean.BaseAttrValue;

import java.util.ArrayList;
import java.util.List;

public interface AttributeService {

    public List<BaseAttrInfo> attributeInfoList(String catalog3Id);

    public Integer deleteAttributeInfo(String attributeInfoId);

    public void saveAttributeInfo(BaseAttrInfo attributeInfo);

    public void updateAttributeInfo(BaseAttrInfo attributeInfo);


    //AtributeValue

    public List<BaseAttrValue> attributeValueList(String attributeInfoId);

    public void deleteAttributeValuesByAttributeInfoId(String attributeInfoId);

    public void saveAttributeValuesByList(List<BaseAttrValue> attributeValueList, String attrId, Boolean isEnabled);

    public void updateAttributeValuesByList(List<BaseAttrValue> attributeValueList);

    public void deleteAttributeValuesByList(List<BaseAttrValue> attributeValueList);

    public void updateAttributeValues(BaseAttrInfo attributeInfo);

}
