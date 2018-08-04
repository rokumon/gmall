package cn.mislily.gmall.manager.service.impl;

import cn.mislily.gmall.bean.BaseSaleAttr;
import cn.mislily.gmall.bean.SpuInfo;
import cn.mislily.gmall.bean.SpuSaleAttr;
import cn.mislily.gmall.manager.mapper.BaseSaleAttrMapper;
import cn.mislily.gmall.manager.mapper.SpuInfoMapper;
import cn.mislily.gmall.manager.mapper.SpuSaleAttrMapper;
import cn.mislily.gmall.service.SpuService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SpuServiceImpl implements SpuService {

    @Autowired
    SpuInfoMapper spuInfoMapper;

    @Autowired
    SpuSaleAttrMapper spuSaleAttrMapper;

    @Autowired
    BaseSaleAttrMapper baseSaleAttrMapper;

    //SpuInfo
    @Override
    public List<SpuInfo> spuList(String catalog3Id) {
        SpuInfo spuInfo = new SpuInfo();
        spuInfo.setCatalog3Id(catalog3Id);
        List<SpuInfo> select = spuInfoMapper.select(spuInfo);
        return select;
    }

    @Override
    public void saveSpuInfo(SpuInfo spuInfo) {

        //保存 SpuInfo 并获取 id
        spuInfoMapper.insertSelective(spuInfo);

        //保存图片信息


        //获取 属性值列表
        List<SpuSaleAttr> spuSaleAttrList = spuInfo.getSpuSaleAttrList();

        //保存 属性值列表
        saveSaleAttrsByList(spuSaleAttrList, spuInfo.getId());
    }


    //SpuSaleAttr
    public void saveSaleAttrsByList(List<SpuSaleAttr> spuSaleAttrList, String spuInfoId){
        if(spuSaleAttrList != null){
            for (SpuSaleAttr spuSaleAttr:spuSaleAttrList){
                spuSaleAttr.setSpuId(spuInfoId);
                spuSaleAttrMapper.insert(spuSaleAttr);
            }
        }
    }


    //BaseSaleAttr
    @Override
    public List<BaseSaleAttr> baseSaleAttrList() {
        return baseSaleAttrMapper.selectAll();
    }
}
