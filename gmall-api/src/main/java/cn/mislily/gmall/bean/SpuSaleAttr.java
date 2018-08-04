package cn.mislily.gmall.bean;

import cn.mislily.gmall.bean.interfaces.DataBaseUpdateEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @param
 * @return
 */
public class SpuSaleAttr implements Serializable, DataBaseUpdateEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    @Column
    String spuId;

    @Column
    String saleAttrId;

    @Column
    String saleAttrName;

    @Transient
    List<SpuSaleAttrValue> spuSaleAttrValueList;

    @Transient
    Map spuSaleAttrValueJson;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = (id == "" ? null : id);
    }

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = (spuId == "" ? null : spuId);
    }

    public String getSaleAttrId() {
        return saleAttrId;
    }

    public void setSaleAttrId(String saleAttrId) {
        this.saleAttrId = (saleAttrId == "" ? null : saleAttrId);
    }

    public String getSaleAttrName() {
        return saleAttrName;
    }

    public void setSaleAttrName(String saleAttrName) {
        this.saleAttrName = (saleAttrName == "" ? null : saleAttrName);
    }

    public List<SpuSaleAttrValue> getSpuSaleAttrValueList() {
        return spuSaleAttrValueList;
    }

    public void setSpuSaleAttrValueList(List<SpuSaleAttrValue> spuSaleAttrValueList) {
        this.spuSaleAttrValueList = spuSaleAttrValueList;
    }

    public Map getSpuSaleAttrValueJson() {
        return spuSaleAttrValueJson;
    }

    public void setSpuSaleAttrValueJson(Map spuSaleAttrValueJson) {
        this.spuSaleAttrValueJson = spuSaleAttrValueJson;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpuSaleAttr that = (SpuSaleAttr) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (spuId != null ? !spuId.equals(that.spuId) : that.spuId != null) return false;
        if (saleAttrId != null ? !saleAttrId.equals(that.saleAttrId) : that.saleAttrId != null) return false;
        if (saleAttrName != null ? !saleAttrName.equals(that.saleAttrName) : that.saleAttrName != null) return false;
        if (spuSaleAttrValueList != null ? !spuSaleAttrValueList.equals(that.spuSaleAttrValueList) : that.spuSaleAttrValueList != null)
            return false;
        return spuSaleAttrValueJson != null ? spuSaleAttrValueJson.equals(that.spuSaleAttrValueJson) : that.spuSaleAttrValueJson == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (spuId != null ? spuId.hashCode() : 0);
        result = 31 * result + (saleAttrId != null ? saleAttrId.hashCode() : 0);
        result = 31 * result + (saleAttrName != null ? saleAttrName.hashCode() : 0);
        result = 31 * result + (spuSaleAttrValueList != null ? spuSaleAttrValueList.hashCode() : 0);
        result = 31 * result + (spuSaleAttrValueJson != null ? spuSaleAttrValueJson.hashCode() : 0);
        return result;
    }
}