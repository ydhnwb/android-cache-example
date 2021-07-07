package com.ydhnwb.simplecaching.data.todo

import com.ydhnwb.simplecaching.data.todo.local.TodoDao
import com.ydhnwb.simplecaching.data.todo.remote.TodoRemoteSource
import com.ydhnwb.simplecaching.domain.common.base.BaseResult
import com.ydhnwb.simplecaching.domain.common.base.Failure
import com.ydhnwb.simplecaching.domain.todo.entity.TodoEntity
import com.ydhnwb.simplecaching.domain.todo.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TodoRepositoryImpl constructor(
    private val todoRemoteSource: TodoRemoteSource,
    private val todoDao: TodoDao
) : TodoRepository {

    override suspend fun fetchTodos(): Flow<BaseResult<List<TodoEntity>, Failure>> {
        return flow {
            val localTodos = todoDao.findAll()

            // send the local db value to db
            emit(BaseResult.Success(localTodos))

            // is should fetch?
            if(localTodos.isNullOrEmpty()){
                val result = todoRemoteSource.fetchTodos()
                if(result is BaseResult.Success){
                    saveToLocal(result.data)
                }
                emit(result)
            }
        }
    }


    private fun saveToLocal(todos: List<TodoEntity>){
        todoDao.deleteAll()
        todoDao.insertAll(todos)
    }
}