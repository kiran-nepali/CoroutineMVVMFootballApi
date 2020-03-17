package com.example.coroutine

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.coroutine.di.AppModule
import com.example.coroutine.di.DaggerAppComponent
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: FootballViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DaggerAppComponent.builder()
            .appModule(AppModule())
            .build()
            .inject(this)
        val viewModel = ViewModelProvider(this, viewModelFactory)
            .get(FootballViewModel::class.java)
        viewModel.getFromFootballAPI()
        click_btn.setOnClickListener {
            viewModel.progressState.observe(this, Observer {
                when (it) {
                    FootballViewModel.ProgressState.SUCCESS -> viewModel.teamname.observe(
                        this,
                        Observer {
                            tvdisplay.text = it.teams[0].strTeam
                        })

                    FootballViewModel.ProgressState.FAILURE -> viewModel.error.observe(
                        this,
                        Observer {
                            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
                        })
                }
            })
        }
    }
}
