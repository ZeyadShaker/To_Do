package com.todo.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.todo.models.Task
import java.util.Date

@Dao
interface TasksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)


    @Delete
    fun deleteTask(task: Task)

//    @Update
//    fun updateTask(task: Task)

    @Query("SELECT * FROM Task")
    fun getAllTasks():List<Task>

    @Query("SELECT * FROM Task WHERE date=:dateTime")
    fun getTasksInDate(dateTime: Date):List<Task>
}