package cn.mislily.gmall.bean;

import cn.mislily.gmall.bean.interfaces.DataBaseUpdateEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @param
 * @return
 */

public class SpuInfo implements Serializable, DataBaseUpdateEntity {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    private String spuName;

    @Column
    private String description;

    @Column
    private String catalog3Id;

    @Transient
    private List<SpuSaleAttr> spuSaleAttrList;

    @Transient
    private List<SpuImage> spuImageList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = (id == "" ? null : id);
    }

    public String getSpuName() {
        return spuName;
    }

    public void setSpuName(String spuName) {
        this.spuName = (spuName == "" ? null : spuName);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = (description == "" ? null : description);
    }

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = (catalog3Id == "" ? null : catalog3Id);
    }

    public List<SpuSaleAttr> getSpuSaleAttrList() {
        return spuSaleAttrList;
    }

    public void setSpuSaleAttrList(List<SpuSaleAttr> spuSaleAttrList) {
        this.spuSaleAttrList = spuSaleAttrList;
    }

    public List<SpuImage> getSpuImageList() {
        return spuImageList;
    }

    public void setSpuImageList(List<SpuImage> spuImageList) {
        this.spuImageList = spuImageList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpuInfo spuInfo = (SpuInfo) o;

        if (id != null ? !id.equals(spuInfo.id) : spuInfo.id != null) return false;
        if (spuName != null ? !spuName.equals(spuInfo.spuName) : spuInfo.spuName != null) return false;
        if (description != null ? !description.equals(spuInfo.description) : spuInfo.description != null) return false;
        if (catalog3Id != null ? !catalog3Id.equals(spuInfo.catalog3Id) : spuInfo.catalog3Id != null) return false;
        if (spuSaleAttrList != null ? !spuSaleAttrList.equals(spuInfo.spuSaleAttrList) : spuInfo.spuSaleAttrList != null)
            return false;
        return spuImageList != null ? spuImageList.equals(spuInfo.spuImageList) : spuInfo.spuImageList == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (spuName != null ? spuName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (catalog3Id != null ? catalog3Id.hashCode() : 0);
        result = 31 * result + (spuSaleAttrList != null ? spuSaleAttrList.hashCode() : 0);
        result = 31 * result + (spuImageList != null ? spuImageList.hashCode() : 0);
        return result;
    }
}


