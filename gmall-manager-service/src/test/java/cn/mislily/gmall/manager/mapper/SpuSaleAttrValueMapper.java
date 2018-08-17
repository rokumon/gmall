package cn.mislily.gmall.manager.mapper;

import cn.mislily.gmall.bean.SkuInfo;
import cn.mislily.gmall.bean.SpuSaleAttr;
import cn.mislily.gmall.bean.SpuSaleAttrValue;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SpuSaleAttrValueMapper extends Mapper<SpuSaleAttrValue>{
    List<SpuSaleAttr> selectSpuSaleAttrListCheckBySku(Map<String, String> stringStringHashMap);

    List<SkuInfo> selectSkuSaleAttrValueListBySpu(String spuId);
}
