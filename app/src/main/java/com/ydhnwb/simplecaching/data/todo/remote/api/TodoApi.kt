package com.ydhnwb.simplecaching.data.todo.remote.api

import com.ydhnwb.simplecaching.data.todo.remote.dto.TodoResponse
import retrofit2.Response
import retrofit2.http.GET

interface TodoApi {
    @GET("todos")
    suspend fun todos() : Response<List<TodoResponse>>
}