package com.ydhnwb.simplecaching.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ydhnwb.simplecaching.data.todo.local.TodoDao
import com.ydhnwb.simplecaching.domain.todo.entity.TodoEntity

@Database(
    entities = [
        TodoEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun todoDao() : TodoDao
}