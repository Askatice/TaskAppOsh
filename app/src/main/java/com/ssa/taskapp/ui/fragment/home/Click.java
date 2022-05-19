package com.ssa.taskapp.ui.fragment.home;

import com.ssa.taskapp.data.model.TaskModel;

public interface Click {
    void click (TaskModel taskModel);
    void delete (TaskModel taskModel);
}
