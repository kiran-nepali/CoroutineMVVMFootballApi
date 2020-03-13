package com.example.coroutine.di

import com.example.coroutine.Constants
import com.example.coroutine.FootballViewModelFactory
import com.example.coroutine.network.MyNetwork
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideWebServices(retrofit: Retrofit): MyNetwork {
        return retrofit.create(MyNetwork::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpCient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideFootballViewModelFactory(mynetwork: MyNetwork): FootballViewModelFactory {
        return FootballViewModelFactory(mynetwork)
    }
}