package com.todo.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date
@Parcelize
@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,
    val title:String?=null,
    val description:String?=null,
    val date: Date?=null,
    val isDone:Boolean?=null,

):Parcelable
