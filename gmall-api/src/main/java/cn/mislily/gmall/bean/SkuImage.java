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
public class SkuImage implements Serializable, DataBaseUpdateEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;
    @Column
    String skuId;
    @Column
    String imgName;
    @Column
    String imgUrl;
    @Column
    String spuImgId;
    @Column
    String isDefault;

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

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = (imgName == "" ? null : imgName);
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = (imgUrl == "" ? null : imgUrl);
    }

    public String getSpuImgId() {
        return spuImgId;
    }

    public void setSpuImgId(String spuImgId) {
        this.spuImgId = (spuImgId == "" ? null : spuImgId);
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = (isDefault == "" ? null : isDefault);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkuImage skuImage = (SkuImage) o;

        if (id != null ? !id.equals(skuImage.id) : skuImage.id != null) return false;
        if (skuId != null ? !skuId.equals(skuImage.skuId) : skuImage.skuId != null) return false;
        if (imgName != null ? !imgName.equals(skuImage.imgName) : skuImage.imgName != null) return false;
        if (imgUrl != null ? !imgUrl.equals(skuImage.imgUrl) : skuImage.imgUrl != null) return false;
        if (spuImgId != null ? !spuImgId.equals(skuImage.spuImgId) : skuImage.spuImgId != null) return false;
        return isDefault != null ? isDefault.equals(skuImage.isDefault) : skuImage.isDefault == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (imgName != null ? imgName.hashCode() : 0);
        result = 31 * result + (imgUrl != null ? imgUrl.hashCode() : 0);
        result = 31 * result + (spuImgId != null ? spuImgId.hashCode() : 0);
        result = 31 * result + (isDefault != null ? isDefault.hashCode() : 0);
        return result;
    }
}