package com.ssa.taskapp.ui.home.task;

public class TaskModel {
    private String title, created;

    public TaskModel(String title, String created) {
        this.title = title;
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public String getCreated() {
        return created;
    }
}
