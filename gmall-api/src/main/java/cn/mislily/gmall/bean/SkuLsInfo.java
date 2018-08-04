package cn.mislily.gmall.bean;

import cn.mislily.gmall.bean.interfaces.DataBaseUpdateEntity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @param
 * @return
 */
public class SkuLsInfo implements Serializable, DataBaseUpdateEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    BigDecimal price;

    String skuName;

    String skuDesc;

    String catalog3Id;

    String skuDefaultImg;

    Long hotScore = 0L;

    List<SkuLsAttrValue> skuAttrValueList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = (id == "" ? null : id);
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

    public Long getHotScore() {
        return hotScore;
    }

    public void setHotScore(Long hotScore) {
        this.hotScore = hotScore;
    }

    public List<SkuLsAttrValue> getSkuAttrValueList() {
        return skuAttrValueList;
    }

    public void setSkuAttrValueList(List<SkuLsAttrValue> skuAttrValueList) {
        this.skuAttrValueList = skuAttrValueList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkuLsInfo skuLsInfo = (SkuLsInfo) o;

        if (id != null ? !id.equals(skuLsInfo.id) : skuLsInfo.id != null) return false;
        if (price != null ? !price.equals(skuLsInfo.price) : skuLsInfo.price != null) return false;
        if (skuName != null ? !skuName.equals(skuLsInfo.skuName) : skuLsInfo.skuName != null) return false;
        if (skuDesc != null ? !skuDesc.equals(skuLsInfo.skuDesc) : skuLsInfo.skuDesc != null) return false;
        if (catalog3Id != null ? !catalog3Id.equals(skuLsInfo.catalog3Id) : skuLsInfo.catalog3Id != null) return false;
        if (skuDefaultImg != null ? !skuDefaultImg.equals(skuLsInfo.skuDefaultImg) : skuLsInfo.skuDefaultImg != null)
            return false;
        if (hotScore != null ? !hotScore.equals(skuLsInfo.hotScore) : skuLsInfo.hotScore != null) return false;
        return skuAttrValueList != null ? skuAttrValueList.equals(skuLsInfo.skuAttrValueList) : skuLsInfo.skuAttrValueList == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (skuName != null ? skuName.hashCode() : 0);
        result = 31 * result + (skuDesc != null ? skuDesc.hashCode() : 0);
        result = 31 * result + (catalog3Id != null ? catalog3Id.hashCode() : 0);
        result = 31 * result + (skuDefaultImg != null ? skuDefaultImg.hashCode() : 0);
        result = 31 * result + (hotScore != null ? hotScore.hashCode() : 0);
        result = 31 * result + (skuAttrValueList != null ? skuAttrValueList.hashCode() : 0);
        return result;
    }
}
