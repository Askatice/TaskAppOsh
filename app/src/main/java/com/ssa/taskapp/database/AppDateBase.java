package com.ssa.taskapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ssa.taskapp.data.model.TaskModel;

@Database(entities = {TaskModel.class}, version = 1)
public abstract class AppDateBase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
