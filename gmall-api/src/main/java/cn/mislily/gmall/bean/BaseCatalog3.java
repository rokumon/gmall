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
public class BaseCatalog3 implements Serializable, DataBaseUpdateEntity {
    @Id
    @Column
    private String id;
    @Column
    private String name;
    @Column
    private String catalog2Id;

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

    public String getCatalog2Id() {
        return catalog2Id;
    }

    public void setCatalog2Id(String catalog2Id) {
        this.catalog2Id = catalog2Id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseCatalog3 that = (BaseCatalog3) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(catalog2Id, that.catalog2Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, catalog2Id);
    }
}
