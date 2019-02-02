package com.qaguild.plugin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by alpa on 2018-12-06
 */
@Data
public class Project {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("announcement")
    @Expose
    private Object announcement;
    @SerializedName("show_announcement")
    @Expose
    private Boolean showAnnouncement;
    @SerializedName("is_completed")
    @Expose
    private Boolean isCompleted;
    @SerializedName("completed_on")
    @Expose
    private Object completedOn;
    @SerializedName("suite_mode")
    @Expose
    private Integer suiteMode;
    @SerializedName("url")
    @Expose
    private String url;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Object getAnnouncement() {
        return announcement;
    }

    public Boolean getShowAnnouncement() {
        return showAnnouncement;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public Object getCompletedOn() {
        return completedOn;
    }

    public Integer getSuiteMode() {
        return suiteMode;
    }

    public String getUrl() {
        return url;
    }
}
