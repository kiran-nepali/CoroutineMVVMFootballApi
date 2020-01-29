package com.example.coroutine

import androidx.lifecycle.MutableLiveData
import com.example.coroutine.network.MyNetwork
import com.example.kotlinfootballappchallenge.model.footballTeam.FootballTeam

class FootballRepository(val myNetwork: MyNetwork) {

    var team=MutableLiveData<FootballTeam>()

    private suspend fun getDataFromAPI(){
        try {
            val callbackresponse = myNetwork.getTeam(Constants.CLUB)
            team = callbackresponse
        }
        catch (cause:Throwable){
            throw FootyTeamThrowError("error loading",cause)
        }
    }
}


class FootyTeamThrowError(message:String,cause:Throwable):Throwable(){

}

