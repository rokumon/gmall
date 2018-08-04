package cn.mislily.gmall.bean;

import cn.mislily.gmall.bean.interfaces.DataBaseUpdateEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @param
 * @return
 */
public class SkuSaleAttrValue implements Serializable, DataBaseUpdateEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    @Column
    String skuId;

    @Column
    String saleAttrId;

    @Column
    String saleAttrValueId;

    @Column
    String saleAttrName;

    @Column
    String saleAttrValueName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = (id == "" ? null : id);
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = (skuId == "" ? null : skuId);
    }

    public String getSaleAttrId() {
        return saleAttrId;
    }

    public void setSaleAttrId(String saleAttrId) {
        this.saleAttrId = (saleAttrId == "" ? null : saleAttrId);
    }

    public String getSaleAttrValueId() {
        return saleAttrValueId;
    }

    public void setSaleAttrValueId(String saleAttrValueId) {
        this.saleAttrValueId = (saleAttrValueId == "" ? null : saleAttrValueId);
    }

    public String getSaleAttrName() {
        return saleAttrName;
    }

    public void setSaleAttrName(String saleAttrName) {
        this.saleAttrName = (saleAttrName == "" ? null : saleAttrName);
    }

    public String getSaleAttrValueName() {
        return saleAttrValueName;
    }

    public void setSaleAttrValueName(String saleAttrValueName) {
        this.saleAttrValueName = (saleAttrValueName == "" ? null : saleAttrValueName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkuSaleAttrValue that = (SkuSaleAttrValue) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (skuId != null ? !skuId.equals(that.skuId) : that.skuId != null) return false;
        if (saleAttrId != null ? !saleAttrId.equals(that.saleAttrId) : that.saleAttrId != null) return false;
        if (saleAttrValueId != null ? !saleAttrValueId.equals(that.saleAttrValueId) : that.saleAttrValueId != null)
            return false;
        if (saleAttrName != null ? !saleAttrName.equals(that.saleAttrName) : that.saleAttrName != null) return false;
        return saleAttrValueName != null ? saleAttrValueName.equals(that.saleAttrValueName) : that.saleAttrValueName == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (saleAttrId != null ? saleAttrId.hashCode() : 0);
        result = 31 * result + (saleAttrValueId != null ? saleAttrValueId.hashCode() : 0);
        result = 31 * result + (saleAttrName != null ? saleAttrName.hashCode() : 0);
        result = 31 * result + (saleAttrValueName != null ? saleAttrValueName.hashCode() : 0);
        return result;
    }
}
