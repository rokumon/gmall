package cn.mislily.gmall.bean;

import cn.mislily.gmall.bean.interfaces.DataBaseUpdateEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * @param
 * @return
 */
public class BaseAttrValue implements Serializable, DataBaseUpdateEntity {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column
    private String valueName;
    @Column
    private String attrId;
    @Column
    private String isEnabled;

    @Transient
    private String urlParam;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = (id == "" ? null : id);
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = (valueName == "" ? null : valueName);
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = (attrId == "" ? null : attrId);
    }

    public String getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(String isEnabled) {
        this.isEnabled = (isEnabled == "" ? null : isEnabled);
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = (urlParam == "" ? null : urlParam);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseAttrValue attrValue = (BaseAttrValue) o;

        if (id != null ? !id.equals(attrValue.id) : attrValue.id != null) return false;
        if (valueName != null ? !valueName.equals(attrValue.valueName) : attrValue.valueName != null) return false;
        if (attrId != null ? !attrId.equals(attrValue.attrId) : attrValue.attrId != null) return false;
        if (isEnabled != null ? !isEnabled.equals(attrValue.isEnabled) : attrValue.isEnabled != null) return false;
        return urlParam != null ? urlParam.equals(attrValue.urlParam) : attrValue.urlParam == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (valueName != null ? valueName.hashCode() : 0);
        result = 31 * result + (attrId != null ? attrId.hashCode() : 0);
        result = 31 * result + (isEnabled != null ? isEnabled.hashCode() : 0);
        result = 31 * result + (urlParam != null ? urlParam.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BaseAttrValue{" +
                "id='" + id + '\'' +
                ", valueName='" + valueName + '\'' +
                ", attrId='" + attrId + '\'' +
                ", isEnabled='" + isEnabled + '\'' +
                ", urlParam='" + urlParam + '\'' +
                '}';
    }
}
