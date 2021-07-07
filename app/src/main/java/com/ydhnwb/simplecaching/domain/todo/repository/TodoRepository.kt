package com.ydhnwb.simplecaching.domain.todo.repository

import com.ydhnwb.simplecaching.domain.common.base.BaseResult
import com.ydhnwb.simplecaching.domain.common.base.Failure
import com.ydhnwb.simplecaching.domain.todo.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun fetchTodos() : Flow<BaseResult<List<TodoEntity>, Failure>>
}