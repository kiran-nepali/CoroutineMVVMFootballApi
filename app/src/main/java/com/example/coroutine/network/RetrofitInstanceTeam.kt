package com.example.coroutine.network

import com.example.coroutine.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstanceTeam {
    val retrofitInstanceTeam:Retrofit
    get() {
        val loggingInterceptor = HttpLoggingInterceptor()
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }
}