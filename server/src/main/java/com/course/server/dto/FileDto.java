package com.course.server.dto;

import java.time.LocalDateTime;

public class FileDto {
    private String id;

    private String path;

    private String name;

    private String suffix;

    private Integer size;

    private String use;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Integer shardIndex;

    private Integer shardSize;

    private Integer shardTotal;

    private String key;

    private String vod;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getShardIndex() {
        return shardIndex;
    }

    public void setShardIndex(Integer shardIndex) {
        this.shardIndex = shardIndex;
    }

    public Integer getShardSize() {
        return shardSize;
    }

    public void setShardSize(Integer shardSize) {
        this.shardSize = shardSize;
    }

    public Integer getShardTotal() {
        return shardTotal;
    }

    public void setShardTotal(Integer shardTotal) {
        this.shardTotal = shardTotal;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVod() {
        return vod;
    }

    public void setVod(String vod) {
        this.vod = vod;
    }

    @Override
    public String toString() {
        return "File{" +
                "id='" + id + '\'' +
                ", path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", suffix='" + suffix + '\'' +
                ", size=" + size +
                ", use='" + use + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", shardIndex=" + shardIndex +
                ", shardSize=" + shardSize +
                ", shardTotal=" + shardTotal +
                ", key='" + key + '\'' +
                ", vod='" + vod + '\'' +
                '}';
    }
}