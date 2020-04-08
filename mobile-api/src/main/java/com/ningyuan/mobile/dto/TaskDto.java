package com.ningyuan.mobile.dto;

public class TaskDto {
    // 初级任务，中级任务
    private String title;
    // 任务内容描述
    private String content;
    // 已完成，未完成，已开启
    private String baseTask;

    private String specialTask;

    private String dailyTask;

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getBaseTask() { return baseTask; }

    public void setBaseTask(String baseTask) { this.baseTask = baseTask; }

    public String getSpecialTask() { return specialTask; }

    public void setSpecialTask(String specialTask) { this.specialTask = specialTask; }

    public String getDailyTask() { return dailyTask; }

    public void setDailyTask(String dailyTask) { this.dailyTask = dailyTask; }
}
