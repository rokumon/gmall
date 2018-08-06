package cn.mislily.gmall.manager.service.impl;

import cn.mislily.gmall.bean.BaseCatalog1;
import cn.mislily.gmall.bean.BaseCatalog2;
import cn.mislily.gmall.bean.BaseCatalog3;
import cn.mislily.gmall.manager.mapper.BaseCatalog1Mapper;
import cn.mislily.gmall.manager.mapper.BaseCatalog2Mapper;
import cn.mislily.gmall.manager.mapper.BaseCatalog3Mapper;
import cn.mislily.gmall.service.CatalogService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    private BaseCatalog1Mapper baseCatalog1Mapper;

    @Autowired
    private BaseCatalog2Mapper baseCatalog2Mapper;

    @Autowired
    private BaseCatalog3Mapper baseCatalog3Mapper;

    /**
     * 一级 分类信息
     * @return
     */
    @Override
    public List<BaseCatalog1> baseCatalog1List() {
        List<BaseCatalog1> baseCatalog1List = baseCatalog1Mapper.selectAll();
        return baseCatalog1List;
    }

    /**
     * 二级 分类信息
     * @param catalog1Id
     * @return
     */
    @Override
    public List<BaseCatalog2> baseCatalog2List(String catalog1Id) {
        BaseCatalog2 baseCatalog2 = new BaseCatalog2();
        baseCatalog2.setCatalog1Id(catalog1Id);

        List<BaseCatalog2> baseCatalog2List = baseCatalog2Mapper.select(baseCatalog2);
        return baseCatalog2List;
    }

    /**
     * 三级 分类信息
     * @param catalog2Id
     * @return
     */
    @Override
    public List<BaseCatalog3> baseCatalog3List(String catalog2Id) {
        BaseCatalog3 baseCatalog3 = new BaseCatalog3();
        baseCatalog3.setCatalog2Id(catalog2Id);

        List<BaseCatalog3> baseCatalog3List = baseCatalog3Mapper.select(baseCatalog3);
        return baseCatalog3List;
    }
}
