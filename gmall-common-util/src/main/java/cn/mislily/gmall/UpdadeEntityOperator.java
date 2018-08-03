package cn.mislily.gmall;

import cn.mislily.gmall.bean.interfaces.DataBaseUpdateEntity;

import java.util.*;

/*
    1.有 getId 方法，返回值为 String
    2.有 hashCode 方法
*/
public class UpdadeEntityOperator<T> {

    //数据库中的数据
    private List<DataBaseUpdateEntity> dbEntityList;
    private HashMap dbEntityMap;
    private Set<DataBaseUpdateEntity> dbEntitySet;
    private Set<String> dbEntityIdSet;

    //用户传入的数据
    private List<DataBaseUpdateEntity> inputEntityList;
    private Map<String, DataBaseUpdateEntity> inputEntityMap;
    private Set<DataBaseUpdateEntity> inputEntitySet;
    private Set<String> inputEntityIdSet;

    //结果数据
    private List<T> added;
    private List<T> deleted;
    private List<T> updated;

    /**
     * 筛选出哪些值需要更新
     * 传入的参数中没有 id 的 : 直接判断 id 是否为 null
     */
    public void findAdded() {

        added = new ArrayList<T>();

        for (DataBaseUpdateEntity entity : inputEntityList) {
            if (entity.getId() != null) {
                added.add((T) entity);
            }
        }
    }

    /**
     * 筛选出哪些值需要删除
     * 数据库中有 但 传入的多个参数中没有其 id 的 : 数据库 id set - 传入值 id set
     */
    public void findDeleted() {

        deleted = new ArrayList<T>();

        dbEntityIdSet.removeAll(inputEntityIdSet);

        Iterator it = dbEntityIdSet.iterator();

        while (it.hasNext()) {
            deleted.add((T) dbEntityMap.get(it.next()));
        }
    }

    /**
     * 筛选出那些值需要更新
     * 传入的参数中有其 id，但值被更新的 : 传入值 bean set - 数据库 bean set
     */
    public void findUpdated() {

        updated = new ArrayList<T>();

        inputEntitySet.removeAll(dbEntitySet);

        Iterator it = inputEntitySet.iterator();

        while (it.hasNext()) {
            updated.add((T) it.next());
        }
    }

    //获取需要被添加的
    public List<T> getAdded() {

        if (added == null) {
            findAdded();
        }

        return added;
    }

    //2 需要被删除的
    public List<T> getDeleted() {

        if (deleted == null) {
            findDeleted();
        }

        return deleted;
    }

    //3 需要被更新的
    public List<T> getUpdated() {

        if (updated == null) {
            findUpdated();
        }

        return updated;
    }


    public UpdadeEntityOperator(List<DataBaseUpdateEntity> dbEntityList, List<DataBaseUpdateEntity> inputEntityList) {

        this.dbEntityList = dbEntityList;
        this.inputEntityList = inputEntityList;

        added = new ArrayList<T>();

        dbEntitySet = new HashSet<DataBaseUpdateEntity>();
        dbEntityIdSet = new HashSet<String>();
        dbEntityMap = new HashMap<String, DataBaseUpdateEntity>();

        inputEntitySet = new HashSet<DataBaseUpdateEntity>();
        inputEntityIdSet = new HashSet<String>();
        inputEntityMap = new HashMap<String, DataBaseUpdateEntity>();

        for (DataBaseUpdateEntity entity : inputEntityList) {

            inputEntitySet.add(entity);

            //坑爹的逻辑判断 BUG
            if (entity.getId() == null) {
                added.add((T) entity);
            } else {
                inputEntityIdSet.add(entity.getId());
                inputEntityMap.put(entity.getId(), entity);
            }
        }

        for (DataBaseUpdateEntity entity : dbEntityList) {
            dbEntitySet.add(entity);
            dbEntityIdSet.add(entity.getId());
            dbEntityMap.put(entity.getId(), entity);
        }
    }

    public void initMapStatus(){

    }

    public void initSetStatus(){
        System.out.println("inputEntitySet");
        System.out.println(inputEntitySet);
        System.out.println("dbEntitySet");
        System.out.println(dbEntitySet);
    }

    public void status() {

        System.out.println("added");
        if (added != null || added.size() != 0) {
            for (T value : added) {
                System.out.println(value);
            }
        } else {
            System.out.println("null");
        }

        System.out.println("deleted");
        if (deleted != null) {
            for (T value : deleted) {
                System.out.println(value);
            }
        } else {
            System.out.println("null");
        }

        System.out.println("updated");
        if (updated != null) {
            for (T value : updated) {
                System.out.println(value);
            }
        } else {
            System.out.println("null");
        }
    }
}
