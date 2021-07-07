package com.ydhnwb.simplecaching.data.todo.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ydhnwb.simplecaching.domain.todo.entity.TodoEntity

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(todo: TodoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(todos: List<TodoEntity>)

    @Query("SELECT * FROM todos")
    fun findAll() : List<TodoEntity>

    @Query("DELETE FROM todos")
    fun deleteAll()
}