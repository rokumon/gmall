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
public class SpuImage implements Serializable, DataBaseUpdateEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String spuId;
    @Column
    private String imgName;
    @Column
    private String imgUrl;

    public SpuImage() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SpuImage spuImage = (SpuImage) o;

        if (id != null ? !id.equals(spuImage.id) : spuImage.id != null) return false;
        if (spuId != null ? !spuId.equals(spuImage.spuId) : spuImage.spuId != null) return false;
        if (imgName != null ? !imgName.equals(spuImage.imgName) : spuImage.imgName != null) return false;
        return imgUrl != null ? imgUrl.equals(spuImage.imgUrl) : spuImage.imgUrl == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (spuId != null ? spuId.hashCode() : 0);
        result = 31 * result + (imgName != null ? imgName.hashCode() : 0);
        result = 31 * result + (imgUrl != null ? imgUrl.hashCode() : 0);
        return result;
    }


}