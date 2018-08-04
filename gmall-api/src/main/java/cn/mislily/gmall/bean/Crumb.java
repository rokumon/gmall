package cn.mislily.gmall.bean;

import cn.mislily.gmall.bean.interfaces.DataBaseUpdateEntity;

import java.io.Serializable;

public class Crumb implements Serializable {

    private String valueName;

    private String urlParam;

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getUrlParam() {
        return urlParam;
    }

    public void setUrlParam(String urlParam) {
        this.urlParam = urlParam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Crumb crumb = (Crumb) o;

        if (valueName != null ? !valueName.equals(crumb.valueName) : crumb.valueName != null) return false;
        return urlParam != null ? urlParam.equals(crumb.urlParam) : crumb.urlParam == null;
    }

    @Override
    public int hashCode() {
        int result = valueName != null ? valueName.hashCode() : 0;
        result = 31 * result + (urlParam != null ? urlParam.hashCode() : 0);
        return result;
    }
}
