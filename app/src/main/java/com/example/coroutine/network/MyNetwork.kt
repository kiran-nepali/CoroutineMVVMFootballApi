package com.example.coroutine.network

import com.example.kotlinfootballappchallenge.model.footballTeam.FootballTeam
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MyNetwork {
    @GET("search_all_teams.php")
    suspend fun getTeam(@Query("l") footballTeam: String): Response<FootballTeam>

    @GET("lookupteam.php")
    suspend fun getTeamInfo(@Query("id") teamId: Int): String
}