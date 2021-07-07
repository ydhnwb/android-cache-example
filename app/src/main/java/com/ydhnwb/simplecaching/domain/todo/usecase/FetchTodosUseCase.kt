package com.ydhnwb.simplecaching.domain.todo.usecase

import com.ydhnwb.simplecaching.domain.common.base.BaseResult
import com.ydhnwb.simplecaching.domain.common.base.Failure
import com.ydhnwb.simplecaching.domain.todo.entity.TodoEntity
import com.ydhnwb.simplecaching.domain.todo.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchTodosUseCase @Inject constructor(private val todoRepository: TodoRepository) {
    suspend fun invoke() : Flow<BaseResult<List<TodoEntity>, Failure>> {
        return todoRepository.fetchTodos()
    }
}