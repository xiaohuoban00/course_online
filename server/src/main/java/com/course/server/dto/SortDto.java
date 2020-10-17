package com.course.server.dto;

/**
 * @author zmq
 * @date 2020/10/18 1:28 上午
 */
public class SortDto {
    /**
     * ID
     */
    private String id;
    /**
     * 当前排序
     */
    private Integer oldSort;
    /**
     * 新排序
     */
    private Integer newSort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getOldSort() {
        return oldSort;
    }

    public void setOldSort(Integer oldSort) {
        this.oldSort = oldSort;
    }

    public Integer getNewSort() {
        return newSort;
    }

    public void setNewSort(Integer newSort) {
        this.newSort = newSort;
    }
}
