package cn.mislily.gmall.service;

import cn.mislily.gmall.bean.BaseCatalog1;
import cn.mislily.gmall.bean.BaseCatalog2;
import cn.mislily.gmall.bean.BaseCatalog3;

import java.util.List;

public interface CatalogService {

    //BaseCatalog1
    List<BaseCatalog1> baseCatalog1List();

    //BaseCatalog2
    List<BaseCatalog2> baseCatalog2List(String catalog1Id);

    //BaseCatalog3
    List<BaseCatalog3> baseCatalog3List(String catalog2Id);

}
