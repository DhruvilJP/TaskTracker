package com.example.tasktracker;

public class Task {
    int id;
    String title;
    String description;
    String addTime;
    String addDate;
    String dueTime;
    String dueDate;
    String priority;
    String completion;

    public Task() {}
    public Task(int id, String title, String addTime, String addDate, String dueTime, String dueDate, String priority, String completion, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.addTime = addTime;
        this.addDate = addTime;
        this.dueTime = dueTime;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completion = completion;
    }

    public Task(String title, String addTime, String addDate, String dueTime, String dueDate, String priority, String completion, String description) {
        this.title = title;
        this.description = description;
        this.addTime = addTime;
        this.addDate = addTime;
        this.dueTime = dueTime;
        this.dueDate = dueDate;
        this.priority = priority;
        this.completion = completion;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddTime() {
        return this.addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getAddDate() {
        return this.addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getDueTime() {
        return this.dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public String getDueDate() {
        return this.dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCompletion() {
        return this.completion;
    }

    public void setCompletion(String completion) {
        this.completion = completion;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
