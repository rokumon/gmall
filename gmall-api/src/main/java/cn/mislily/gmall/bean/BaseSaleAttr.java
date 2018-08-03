package cn.mislily.gmall.bean;

import cn.mislily.gmall.bean.interfaces.DataBaseUpdateEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * @param
 * @return
 */
public class BaseSaleAttr implements Serializable, DataBaseUpdateEntity {

    @Id
    @Column
    String id;

    @Column
    String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseSaleAttr that = (BaseSaleAttr) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}