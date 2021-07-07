package com.ydhnwb.simplecaching.data.todo.remote

import com.ydhnwb.simplecaching.data.exception.NoInternetConnectionException
import com.ydhnwb.simplecaching.data.todo.remote.api.TodoApi
import com.ydhnwb.simplecaching.domain.common.base.BaseResult
import com.ydhnwb.simplecaching.domain.common.base.Failure
import com.ydhnwb.simplecaching.domain.todo.entity.TodoEntity
import okio.IOException
import java.lang.Exception
import java.net.UnknownHostException

class TodoRemoteSource constructor(private val todoApi: TodoApi) {
    suspend fun fetchTodos() : BaseResult<List<TodoEntity>, Failure>{
        try{
            val response = todoApi.todos()
            return if(response.isSuccessful){
                val todos = mutableListOf<TodoEntity>()
                response.body()?.forEach { todo ->
                    todos.add(TodoEntity(todo.id, todo.title, todo.isCompleted))
                }
                BaseResult.Success(todos)
            }else{
                //since we don't know the error response of jsonplacheholder.com,
                //i will create a basic error here
                BaseResult.Error(Failure(response.code(), response.message()))
            }
        }catch (e: NoInternetConnectionException){
            return BaseResult.Error(Failure(0, e.message))
        }catch (e: Exception){
            return BaseResult.Error(Failure(-1, e.message.toString()))
        }
    }
}