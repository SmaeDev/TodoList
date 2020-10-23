package com.smaedev.todolist.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Task(
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "taskId")
    var taskId: Long? = null

): Serializable {
    @ColumnInfo(name = "taskTitle")
    var taskTitle : String? = null

    @ColumnInfo(name = "description")
    var description : String? = null

    @ColumnInfo(name = "finish_by")
    var finishBy : String? = null

    @ColumnInfo(name = "isFinished")
    var isFinished : Boolean? = null

    constructor(taskTitle: String, description: String, finishBy: String, isFinished: Boolean) : this() {
        this.taskTitle = taskTitle
        this.description = description
        this.finishBy = finishBy
        this.isFinished = isFinished
    }
}