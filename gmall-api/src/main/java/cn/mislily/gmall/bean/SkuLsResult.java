package cn.mislily.gmall.bean;

import cn.mislily.gmall.bean.interfaces.DataBaseUpdateEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @param
 * @return
 */
public class SkuLsResult implements Serializable {

    List<SkuLsInfo> skuLsInfoList;

    int Total;

    List<String> valueIdList;

    public List<SkuLsInfo> getSkuLsInfoList() {
        return skuLsInfoList;
    }

    public void setSkuLsInfoList(List<SkuLsInfo> skuLsInfoList) {
        this.skuLsInfoList = skuLsInfoList;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public List<String> getValueIdList() {
        return valueIdList;
    }

    public void setValueIdList(List<String> valueIdList) {
        this.valueIdList = valueIdList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SkuLsResult that = (SkuLsResult) o;

        if (Total != that.Total) return false;
        if (skuLsInfoList != null ? !skuLsInfoList.equals(that.skuLsInfoList) : that.skuLsInfoList != null)
            return false;
        return valueIdList != null ? valueIdList.equals(that.valueIdList) : that.valueIdList == null;
    }

    @Override
    public int hashCode() {
        int result = skuLsInfoList != null ? skuLsInfoList.hashCode() : 0;
        result = 31 * result + Total;
        result = 31 * result + (valueIdList != null ? valueIdList.hashCode() : 0);
        return result;
    }
}
