package com.example.coroutine.network

import com.example.coroutine.Constants
import com.example.kotlinfootballappchallenge.model.footballTeam.FootballTeam
import com.example.kotlinfootballappchallenge.model.footballTeam.Teams
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val service: MyNetwork by lazy {
    val loggingInterceptor = HttpLoggingInterceptor()
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    retrofit.create(MyNetwork::class.java)
}

interface MyNetwork {
    @GET("search_all_teams.php")
    suspend fun getTeam(@Query("l") footballTeam:String): FootballTeam

    @GET("lookupteam.php")
    suspend fun getTeamInfo(@Query("id") teamId:Int): Teams
}