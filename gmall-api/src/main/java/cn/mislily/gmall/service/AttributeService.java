package cn.mislily.gmall.service;

import cn.mislily.gmall.bean.BaseAttrInfo;
import cn.mislily.gmall.bean.BaseAttrValue;

import java.util.List;

public interface AttributeService {

    // attributeInfo
    public List<BaseAttrInfo> attributeInfoList(String catalog3Id);

    // attributeInfo && attributeValue
    public Integer deleteAttributeInfo(String attributeInfoId);

    // attributeValue
    public void saveAttribute(BaseAttrInfo attributeInfo);

}
