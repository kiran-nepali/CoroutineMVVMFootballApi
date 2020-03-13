package com.example.coroutine

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coroutine.network.MyNetwork
import com.example.kotlinfootballappchallenge.model.footballTeam.FootballTeam
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FootballViewModel(private val myNetwork: MyNetwork) : ViewModel() {

    val teamname = MutableLiveData<FootballTeam>()
    val error = MutableLiveData<String>()

//    suspend fun getDataFromAPI() {
//        try {
//            var response = myNetwork.getTeam(Constants.CLUB)
//            if (response.isSuccessful) {
//                teamname.value = response.body()
//            } else {
//                error.value = response.message()
//            }
//        } catch (cause: Throwable) {
//            throw FootyTeamThrowError("error loading", cause)
//        }
//    }

    fun getFromFootballAPI() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            var response = myNetwork.getTeam(Constants.CLUB)
            withContext(Dispatchers.Main){
                try {
                    if (response.isSuccessful) {
                        teamname.value = response.body()
                    } else {
                        error.value = response.message()
                    }
                } catch (cause: Throwable) {
                    throw FootyTeamThrowError("error loading", cause)
                }
            }
        }
    }
}


class FootyTeamThrowError(message: String, cause: Throwable) : Throwable()








