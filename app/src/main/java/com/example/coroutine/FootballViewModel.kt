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
    val progressState = MutableLiveData<ProgressState>()

    fun getFromFootballAPI() {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val response = myNetwork.getTeam(Constants.CLUB)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        if (response.body()?.teams == null) {
                            progressState.value = ProgressState.FAILURE
                            error.value = "it is null"
                        } else {
                            teamname.value = response.body()
                            progressState.value = ProgressState.SUCCESS
                        }
                    } else {
                        progressState.value = ProgressState.FAILURE
                        error.value = response.errorBody().toString()
                    }
                } catch (cause: Throwable) {
                    throw FootyTeamThrowError("error loading", cause)
                }
            }
        }
    }

    sealed class ProgressState {
        object SUCCESS : ProgressState()
        object FAILURE : ProgressState()
    }
}


class FootyTeamThrowError(message: String, cause: Throwable) : Throwable()


//                        val errorResponse = Gson().fromJson<ErrorResponse>(
//                            response.errorBody()?.string(),
//                            ErrorResponse::class.java
//                        )
//                        if (errorResponse != null) {
//                           throw RuntimeException(errorResponse.message)

//                        } else {
//                            throw RuntimeException("Unknown Error")
//                        }





