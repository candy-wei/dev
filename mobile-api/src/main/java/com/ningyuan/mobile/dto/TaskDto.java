package com.ningyuan.mobile.dto;

public class TaskDto {
    // 初级任务，中级任务
    private String title;
    // 任务内容描述
    private String content;
    // 已完成，未完成，已开启
    private String taskStatus;

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }

    public void setContent(String content) { this.content = content; }

    public String getTaskStatus() { return taskStatus; }

    public void setTaskStatus(String taskStatus) { this.taskStatus = taskStatus; }
}
