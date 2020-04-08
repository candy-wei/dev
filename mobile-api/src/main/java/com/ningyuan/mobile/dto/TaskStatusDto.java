package com.ningyuan.mobile.dto;

public class TaskStatusDto {
    private Boolean baseTask;
    private Boolean specialTask;
    private Boolean dailyTask;

    public Boolean getBaseTask() {
        return baseTask;
    }

    public void setBaseTask(Boolean baseTask) {
        this.baseTask = baseTask;
    }

    public Boolean getSpecialTask() {
        return specialTask;
    }

    public void setSpecialTask(Boolean specialTask) {
        this.specialTask = specialTask;
    }

    public Boolean getDailyTask() {
        return dailyTask;
    }

    public void setDailyTask(Boolean dailyTask) {
        this.dailyTask = dailyTask;
    }
}
