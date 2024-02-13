package com.todo.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.todo.models.Task

@Database(entities = [Task::class], version = 1)
@TypeConverters(value = [DateTypeConverter::class])
abstract class TasksDatabase:RoomDatabase() {
    abstract fun getTasksDao():TasksDao
    companion object{
        private var INSTANCE:TasksDatabase?=null
        private val DATABASE_NAME="Tasks Database"
        fun getInstance(context: Context):TasksDatabase{
            if (INSTANCE==null){
                INSTANCE= Room.databaseBuilder(context,TasksDatabase::class.java, DATABASE_NAME)
                    .fallbackToDestructiveMigration().
                    allowMainThreadQueries()
                    .build()

            }
return INSTANCE!!
        }
    }
}