package com.ydhnwb.simplecaching.data.common.module

import android.content.Context
import com.ydhnwb.simplecaching.BuildConfig
import com.ydhnwb.simplecaching.data.common.interceptor.NetworkInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient) : Retrofit {
        return Retrofit.Builder().apply {
            baseUrl(BuildConfig.API_BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(client)
        }.build()
    }

    @Provides
    @Singleton
    fun provideHttpClient(networkInterceptor: NetworkInterceptor) : OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(networkInterceptor)
            readTimeout(60, TimeUnit.SECONDS)
            writeTimeout(60, TimeUnit.SECONDS)
            connectTimeout(60, TimeUnit.SECONDS)
        }.build()
    }

    @Provides
    fun provideInterceptor(@ApplicationContext context: Context) : NetworkInterceptor {
        return NetworkInterceptor(context)
    }
}