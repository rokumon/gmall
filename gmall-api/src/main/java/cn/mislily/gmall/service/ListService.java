package cn.mislily.gmall.service;

import cn.mislily.gmall.bean.SkuLsInfo;
import cn.mislily.gmall.bean.SkuLsParam;

import java.util.List;

public interface ListService {

    public List<SkuLsInfo> search(SkuLsParam skuLsParam) ;

    public  String getMyDsl(SkuLsParam skuLsParam) ;

}
