package com.ssa.taskapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ssa.taskapp.data.model.TaskModel;

import java.util.List;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM taskmodel")
    List<TaskModel> getAllTasks();

    @Query("SELECT * FROM taskmodel Order BY title ASC")
    List<TaskModel> sortWithAlphabetAllTasks();

    @Query("SELECT * FROM taskmodel Order BY created ASC")
    List<TaskModel> sortWithDateAllTasks();

    @Insert
    void addTask(TaskModel taskModel);

    @Delete
    void delete(TaskModel taskModel);

    @Update
    void update(TaskModel taskModel);
}
