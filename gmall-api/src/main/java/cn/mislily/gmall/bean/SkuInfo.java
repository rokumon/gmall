package cn.mislily.gmall.bean;

import cn.mislily.gmall.bean.interfaces.DataBaseUpdateEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @param
 * @return
 */
public class SkuInfo implements Serializable, DataBaseUpdateEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    String id;

    @Column
    String spuId;

    @Column
    BigDecimal price;

    @Column
    String skuName;

    @Column
    BigDecimal weight;

    @Column
    String skuDesc;

    @Column
    String catalog3Id;

    @Column
    String skuDefaultImg;

    @Transient
    List<SkuImage> skuImageList;

    @Transient
    List<SkuAttrValue> skuAttrValueList;

    @Transient
    List<SkuSaleAttrValue> skuSaleAttrValueList;


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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = (skuName == "" ? null : skuName);
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getSkuDesc() {
        return skuDesc;
    }

    public void setSkuDesc(String skuDesc) {
        this.skuDesc = (skuDesc == "" ? null : skuDesc);
    }

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = (catalog3Id == "" ? null : catalog3Id);
    }

    public String getSkuDefaultImg() {
        return skuDefaultImg;
    }

    public void setSkuDefaultImg(String skuDefaultImg) {
        this.skuDefaultImg = (skuDefaultImg == "" ? null : skuDefaultImg);
    }

    public List<SkuImage> getSkuImageList() {
        return skuImageList;
    }

    public void setSkuImageList(List<SkuImage> skuImageList) {
        this.skuImageList = skuImageList;
    }

    public List<SkuAttrValue> getSkuAttrValueList() {
        return skuAttrValueList;
    }

    public void setSkuAttrValueList(List<SkuAttrValue> skuAttrValueList) {
        this.skuAttrValueList = skuAttrValueList;
    }

    public List<SkuSaleAttrValue> getSkuSaleAttrValueList() {
        return skuSaleAttrValueList;
    }

    public void setSkuSaleAttrValueList(List<SkuSaleAttrValue> skuSaleAttrValueList) {
        this.skuSaleAttrValueList = skuSaleAttrValueList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkuInfo skuInfo = (SkuInfo) o;

        if (id != null ? !id.equals(skuInfo.id) : skuInfo.id != null) return false;
        if (spuId != null ? !spuId.equals(skuInfo.spuId) : skuInfo.spuId != null) return false;
        if (price != null ? !price.equals(skuInfo.price) : skuInfo.price != null) return false;
        if (skuName != null ? !skuName.equals(skuInfo.skuName) : skuInfo.skuName != null) return false;
        if (weight != null ? !weight.equals(skuInfo.weight) : skuInfo.weight != null) return false;
        if (skuDesc != null ? !skuDesc.equals(skuInfo.skuDesc) : skuInfo.skuDesc != null) return false;
        if (catalog3Id != null ? !catalog3Id.equals(skuInfo.catalog3Id) : skuInfo.catalog3Id != null) return false;
        if (skuDefaultImg != null ? !skuDefaultImg.equals(skuInfo.skuDefaultImg) : skuInfo.skuDefaultImg != null)
            return false;
        if (skuImageList != null ? !skuImageList.equals(skuInfo.skuImageList) : skuInfo.skuImageList != null)
            return false;
        if (skuAttrValueList != null ? !skuAttrValueList.equals(skuInfo.skuAttrValueList) : skuInfo.skuAttrValueList != null)
            return false;
        return skuSaleAttrValueList != null ? skuSaleAttrValueList.equals(skuInfo.skuSaleAttrValueList) : skuInfo.skuSaleAttrValueList == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (spuId != null ? spuId.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (skuName != null ? skuName.hashCode() : 0);
        result = 31 * result + (weight != null ? weight.hashCode() : 0);
        result = 31 * result + (skuDesc != null ? skuDesc.hashCode() : 0);
        result = 31 * result + (catalog3Id != null ? catalog3Id.hashCode() : 0);
        result = 31 * result + (skuDefaultImg != null ? skuDefaultImg.hashCode() : 0);
        result = 31 * result + (skuImageList != null ? skuImageList.hashCode() : 0);
        result = 31 * result + (skuAttrValueList != null ? skuAttrValueList.hashCode() : 0);
        result = 31 * result + (skuSaleAttrValueList != null ? skuSaleAttrValueList.hashCode() : 0);
        return result;
    }
}
