package cn.mislily.gmall.manager.service.impl;

import cn.mislily.gmall.bean.BaseAttrInfo;
import cn.mislily.gmall.bean.BaseCatalog3;
import cn.mislily.gmall.manager.mapper.AttributeInfoMapper;
import cn.mislily.gmall.service.AttributeService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeInfoMapper attributeInfoMapper;

    @Override
    public List<BaseAttrInfo> attributeInfoList(String catalog3Id) {

        BaseAttrInfo baseAttrInfo = new BaseAttrInfo();
        baseAttrInfo.setCatalog3Id(catalog3Id);

        List<BaseAttrInfo> attributeInfoList = attributeInfoMapper.select(baseAttrInfo);

        return attributeInfoList;
    }
}
