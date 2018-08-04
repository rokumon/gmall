package cn.mislily.gmall.bean;

import cn.mislily.gmall.bean.interfaces.DataBaseUpdateEntity;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @param
 * @return
 */
public class SkuLsParam implements Serializable {


    String catalog3Id;

    String[] valueId;

    String keyword;

    int pageNo = 1;

    int pageSize = 20;

    public String getCatalog3Id() {
        return catalog3Id;
    }

    public void setCatalog3Id(String catalog3Id) {
        this.catalog3Id = (catalog3Id == "" ? null : catalog3Id);
    }

    public String[] getValueId() {
        return valueId;
    }

    public void setValueId(String[] valueId) {
        this.valueId = valueId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = (keyword == "" ? null : keyword);
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkuLsParam that = (SkuLsParam) o;

        if (pageNo != that.pageNo) return false;
        if (pageSize != that.pageSize) return false;
        if (catalog3Id != null ? !catalog3Id.equals(that.catalog3Id) : that.catalog3Id != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(valueId, that.valueId)) return false;
        return keyword != null ? keyword.equals(that.keyword) : that.keyword == null;
    }

    @Override
    public int hashCode() {
        int result = catalog3Id != null ? catalog3Id.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(valueId);
        result = 31 * result + (keyword != null ? keyword.hashCode() : 0);
        result = 31 * result + pageNo;
        result = 31 * result + pageSize;
        return result;
    }
}
