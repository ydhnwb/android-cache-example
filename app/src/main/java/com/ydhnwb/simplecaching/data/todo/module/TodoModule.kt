package com.ydhnwb.simplecaching.data.todo.module

import com.ydhnwb.simplecaching.data.AppDatabase
import com.ydhnwb.simplecaching.data.todo.TodoRepositoryImpl
import com.ydhnwb.simplecaching.data.todo.local.TodoDao
import com.ydhnwb.simplecaching.data.todo.remote.TodoRemoteSource
import com.ydhnwb.simplecaching.data.todo.remote.api.TodoApi
import com.ydhnwb.simplecaching.domain.todo.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoModule {

    @Provides
    @Singleton
    fun provideTodoRemoteApi(retrofit: Retrofit) : TodoApi {
        return retrofit.create(TodoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideTodoRemoteSource(todoApi: TodoApi) : TodoRemoteSource {
        return TodoRemoteSource(todoApi)
    }

    @Provides
    @Singleton
    fun provideTodoDao(appDatabase: AppDatabase) : TodoDao {
        return appDatabase.todoDao()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(todoRemoteSource: TodoRemoteSource, todoDao: TodoDao) : TodoRepository {
        return TodoRepositoryImpl(todoRemoteSource, todoDao)
    }
}