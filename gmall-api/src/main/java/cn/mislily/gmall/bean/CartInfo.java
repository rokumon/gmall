package cn.mislily.gmall.bean;

import cn.mislily.gmall.bean.interfaces.DataBaseUpdateEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @param
 * @return
 */
public class CartInfo implements Serializable, DataBaseUpdateEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column
    String id;
    @Column
    String userId;
    @Column
    String skuId;
    @Column
    BigDecimal cartPrice;
    @Column
    Integer skuNum;
    @Column
    String imgUrl;
    @Column
    String skuName;

    @Column
    BigDecimal skuPrice;

    @Column
    String isChecked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = (id == "" ? null : id);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = (userId == "" ? null : userId);
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = (skuId == "" ? null : skuId);
    }

    public BigDecimal getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(BigDecimal cartPrice) {
        this.cartPrice = cartPrice;
    }

    public Integer getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(Integer skuNum) {
        this.skuNum = skuNum;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = (imgUrl == "" ? null : imgUrl);
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = (skuName == "" ? null : skuName);
    }

    public BigDecimal getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(BigDecimal skuPrice) {
        this.skuPrice = skuPrice;
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

        CartInfo cartInfo = (CartInfo) o;

        if (id != null ? !id.equals(cartInfo.id) : cartInfo.id != null) return false;
        if (userId != null ? !userId.equals(cartInfo.userId) : cartInfo.userId != null) return false;
        if (skuId != null ? !skuId.equals(cartInfo.skuId) : cartInfo.skuId != null) return false;
        if (cartPrice != null ? !cartPrice.equals(cartInfo.cartPrice) : cartInfo.cartPrice != null) return false;
        if (skuNum != null ? !skuNum.equals(cartInfo.skuNum) : cartInfo.skuNum != null) return false;
        if (imgUrl != null ? !imgUrl.equals(cartInfo.imgUrl) : cartInfo.imgUrl != null) return false;
        if (skuName != null ? !skuName.equals(cartInfo.skuName) : cartInfo.skuName != null) return false;
        if (skuPrice != null ? !skuPrice.equals(cartInfo.skuPrice) : cartInfo.skuPrice != null) return false;
        return isChecked != null ? isChecked.equals(cartInfo.isChecked) : cartInfo.isChecked == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (skuId != null ? skuId.hashCode() : 0);
        result = 31 * result + (cartPrice != null ? cartPrice.hashCode() : 0);
        result = 31 * result + (skuNum != null ? skuNum.hashCode() : 0);
        result = 31 * result + (imgUrl != null ? imgUrl.hashCode() : 0);
        result = 31 * result + (skuName != null ? skuName.hashCode() : 0);
        result = 31 * result + (skuPrice != null ? skuPrice.hashCode() : 0);
        result = 31 * result + (isChecked != null ? isChecked.hashCode() : 0);
        return result;
    }
}

