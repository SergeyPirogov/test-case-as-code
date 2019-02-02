package com.qaguild.plugin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * Created by alpa on 2018-12-06
 */
@Data
public class Section {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("suite_id")
    @Expose
    private Integer suiteId;
    @SerializedName("parent_id")
    @Expose
    private Integer parentId;
    @SerializedName("depth")
    @Expose
    private int depth;
    @SerializedName("display_oder")
    @Expose
    private int displayOrder;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getSuiteId() {
        return suiteId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public int getDepth() {
        return depth;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSuiteId(Integer suiteId) {
        this.suiteId = suiteId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }
}
