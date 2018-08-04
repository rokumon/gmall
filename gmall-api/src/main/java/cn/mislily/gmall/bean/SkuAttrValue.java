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
public class SkuAttrValue implements Serializable, DataBaseUpdateEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    @Column
    String attrId;

    @Column
    String valueId;

    @Column
    String skuId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = (id == "" ? null : id);
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = (attrId == "" ? null : attrId);
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = (valueId == "" ? null : valueId);
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = (skuId == "" ? null : skuId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkuAttrValue that = (SkuAttrValue) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (attrId != null ? !attrId.equals(that.attrId) : that.attrId != null) return false;
        if (valueId != null ? !valueId.equals(that.valueId) : that.valueId != null) return false;
        return skuId != null ? skuId.equals(that.skuId) : that.skuId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (attrId != null ? attrId.hashCode() : 0);
        result = 31 * result + (valueId != null ? valueId.hashCode() : 0);
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        return result;
    }
}
