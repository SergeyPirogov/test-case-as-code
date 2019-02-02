package com.qaguild.plugin.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class TestCase {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("section_id")
    @Expose
    private Integer sectionId;
    @SerializedName("template_id")
    @Expose
    private Integer templateId;
    @SerializedName("type_id")
    @Expose
    private Integer typeId;
    @SerializedName("priority_id")
    @Expose
    private Integer priorityId;
    @SerializedName("milestone_id")
    @Expose
    private Object milestoneId;
    @SerializedName("refs")
    @Expose
    private Object refs;
    @SerializedName("created_by")
    @Expose
    private Integer createdBy;
    @SerializedName("created_on")
    @Expose
    private Integer createdOn;
    @SerializedName("updated_by")
    @Expose
    private Integer updatedBy;
    @SerializedName("updated_on")
    @Expose
    private Integer updatedOn;
    @SerializedName("estimate")
    @Expose
    private Object estimate;
    @SerializedName("estimate_forecast")
    @Expose
    private Object estimateForecast;
    @SerializedName("suite_id")
    @Expose
    private Integer suiteId;
    @SerializedName("custom_state")
    @Expose
    private Integer customState;
    @SerializedName("custom_automation_type")
    @Expose
    private Integer customAutomationType;
    @SerializedName("custom_ac")
    @Expose
    private String customAcceptanceCriteria;
    @SerializedName("custom_preconds")
    @Expose
    private Object customPreconds;
    @SerializedName("custom_steps")
    @Expose
    private Object customSteps;
    @SerializedName("custom_fields")
    @Expose
    private Object customFields;
    @SerializedName("custom_expected")
    @Expose
    private Object customExpected;
    @SerializedName("custom_steps_separated")
    @Expose
    private List<CustomStepsSeparated> customStepsSeparated = null;
    @SerializedName("custom_mission")
    @Expose
    private Object customMission;
    @SerializedName("custom_goals")
    @Expose
    private Object customGoals;

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getSectionId() {
        return sectionId;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public Integer getPriorityId() {
        return priorityId;
    }

    public Object getMilestoneId() {
        return milestoneId;
    }

    public Object getRefs() {
        return refs;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public Integer getCreatedOn() {
        return createdOn;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public Integer getUpdatedOn() {
        return updatedOn;
    }

    public Object getEstimate() {
        return estimate;
    }

    public Object getEstimateForecast() {
        return estimateForecast;
    }

    public Integer getSuiteId() {
        return suiteId;
    }

    public Integer getCustomState() {
        return customState;
    }

    public Integer getCustomAutomationType() {
        return customAutomationType;
    }

    public String getCustomAcceptanceCriteria() {
        return customAcceptanceCriteria;
    }

    public Object getCustomPreconds() {
        return customPreconds;
    }

    public Object getCustomSteps() {
        return customSteps;
    }

    public Object getCustomFields() {
        return customFields;
    }

    public Object getCustomExpected() {
        return customExpected;
    }

    public List<CustomStepsSeparated> getCustomStepsSeparated() {
        return customStepsSeparated;
    }

    public Object getCustomMission() {
        return customMission;
    }

    public Object getCustomGoals() {
        return customGoals;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public void setPriorityId(Integer priorityId) {
        this.priorityId = priorityId;
    }

    public void setMilestoneId(Object milestoneId) {
        this.milestoneId = milestoneId;
    }

    public void setRefs(Object refs) {
        this.refs = refs;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public void setCreatedOn(Integer createdOn) {
        this.createdOn = createdOn;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public void setUpdatedOn(Integer updatedOn) {
        this.updatedOn = updatedOn;
    }

    public void setEstimate(Object estimate) {
        this.estimate = estimate;
    }

    public void setEstimateForecast(Object estimateForecast) {
        this.estimateForecast = estimateForecast;
    }

    public void setSuiteId(Integer suiteId) {
        this.suiteId = suiteId;
    }

    public void setCustomState(Integer customState) {
        this.customState = customState;
    }

    public void setCustomAutomationType(Integer customAutomationType) {
        this.customAutomationType = customAutomationType;
    }

    public void setCustomAcceptanceCriteria(String customAcceptanceCriteria) {
        this.customAcceptanceCriteria = customAcceptanceCriteria;
    }

    public void setCustomPreconds(Object customPreconds) {
        this.customPreconds = customPreconds;
    }

    public void setCustomSteps(Object customSteps) {
        this.customSteps = customSteps;
    }

    public void setCustomFields(Object customFields) {
        this.customFields = customFields;
    }

    public void setCustomExpected(Object customExpected) {
        this.customExpected = customExpected;
    }

    public void setCustomStepsSeparated(List<CustomStepsSeparated> customStepsSeparated) {
        this.customStepsSeparated = customStepsSeparated;
    }

    public void setCustomMission(Object customMission) {
        this.customMission = customMission;
    }

    public void setCustomGoals(Object customGoals) {
        this.customGoals = customGoals;
    }
}
