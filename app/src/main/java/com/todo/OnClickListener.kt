package com.todo

import com.todo.models.Task

interface OnClickListener {
    fun onClick(item:Task, position:Int)
}