package cn.mislily.gmall.bean;

import cn.mislily.gmall.bean.interfaces.DataBaseUpdateEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @param
 * @return
 */
public class SpuSaleAttrValue implements Serializable, DataBaseUpdateEntity {


    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    @Column
    String spuId;

    @Column
    String saleAttrId;

    @Column
    String saleAttrValueName;

    @Transient
    String isChecked;

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

    public String getSaleAttrValueName() {
        return saleAttrValueName;
    }

    public void setSaleAttrValueName(String saleAttrValueName) {
        this.saleAttrValueName = (saleAttrValueName == "" ? null : saleAttrValueName);
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = (isChecked == "" ? null : isChecked);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpuSaleAttrValue that = (SpuSaleAttrValue) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (spuId != null ? !spuId.equals(that.spuId) : that.spuId != null) return false;
        if (saleAttrId != null ? !saleAttrId.equals(that.saleAttrId) : that.saleAttrId != null) return false;
        if (saleAttrValueName != null ? !saleAttrValueName.equals(that.saleAttrValueName) : that.saleAttrValueName != null)
            return false;
        return isChecked != null ? isChecked.equals(that.isChecked) : that.isChecked == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (spuId != null ? spuId.hashCode() : 0);
        result = 31 * result + (saleAttrId != null ? saleAttrId.hashCode() : 0);
        result = 31 * result + (saleAttrValueName != null ? saleAttrValueName.hashCode() : 0);
        result = 31 * result + (isChecked != null ? isChecked.hashCode() : 0);
        return result;
    }
}
