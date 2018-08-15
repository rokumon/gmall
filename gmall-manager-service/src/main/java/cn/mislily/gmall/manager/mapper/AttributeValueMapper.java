package cn.mislily.gmall.manager.mapper;

import cn.mislily.gmall.bean.BaseAttrInfo;
import cn.mislily.gmall.bean.BaseAttrValue;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Set;

public interface AttributeValueMapper extends Mapper<BaseAttrValue> {

    public List<BaseAttrInfo> selectAttrListByValueIds(Set<String> valueIds);
}
