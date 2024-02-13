package com.todo.database

import androidx.room.TypeConverter
import java.sql.Timestamp
import java.util.Date

class DateTypeConverter {
    @TypeConverter
    fun convertDateToLong(date: Date):Long{


        return date.time
    }
    @TypeConverter
    fun convertLongToDate(timestamp:Long):Date{

        return Date(timestamp)
    }
}