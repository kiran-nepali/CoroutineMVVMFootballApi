package com.example.coroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.coroutine.network.MyNetwork

class FootballViewModelFactory(private var myNetwork: MyNetwork):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FootballViewModel(myNetwork) as T
    }
}