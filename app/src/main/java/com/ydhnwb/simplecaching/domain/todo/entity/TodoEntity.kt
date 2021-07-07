package com.ydhnwb.simplecaching.domain.todo.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "todos", indices = [Index(value = ["id"], unique = true)])
data class TodoEntity(
    @PrimaryKey(autoGenerate = false)
    var id : Int,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "is_completed")
    var isComplete : Boolean
)