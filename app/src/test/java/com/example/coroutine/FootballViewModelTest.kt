package com.example.coroutine

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.coroutine.network.MyNetwork
import com.example.kotlinfootballappchallenge.model.footballTeam.FootballTeam
import com.example.kotlinfootballappchallenge.model.footballTeam.Teams
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.internal.EMPTY_RESPONSE
import okhttp3.internal.http2.ErrorCode
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.lang.RuntimeException

@RunWith(MockitoJUnitRunner::class)
class FootballViewModelTest {

    @get:Rule
    val instantTaskExecutor = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()


    private var myNetwork = mock<MyNetwork>()
    private lateinit var viewmodel: FootballViewModel

    private var teamnameobserver: Observer<FootballTeam> = mock()
    private var errorobserver: Observer<String> = mock()

    private var listOfTeams: List<Teams> = listOf(
        Teams(0, 2, 234, "chelsea"),
        Teams(1, 1, 300, "madrid")
    )

    private var emptylistOfTeams:List<Teams> = emptyList()

    private var errormsg = "No data found"

    @Before
    fun setUp() {
        this.viewmodel = FootballViewModel(myNetwork)
        viewmodel.teamname.observeForever(teamnameobserver)
        viewmodel.error.observeForever(errorobserver)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when api call is made, it returns empty list of teams`()=coroutinesTestRule.testDispatcher.runBlockingTest {
        `when`(myNetwork.getTeam(Constants.CLUB)).thenReturn(Response.success(FootballTeam(emptylistOfTeams)))
        viewmodel.getFromFootballAPI()
        verify(teamnameobserver).onChanged(FootballTeam(emptylistOfTeams))
    }


    @ExperimentalCoroutinesApi
    @Test
    fun `when api call is successful, it returns response with data`() =
        coroutinesTestRule.testDispatcher.runBlockingTest {
            `when`(myNetwork.getTeam(Constants.CLUB)).thenReturn(
                Response.success(
                    FootballTeam(
                        listOfTeams
                    )
                )
            )
            viewmodel.getFromFootballAPI()
            verify(myNetwork).getTeam(Constants.CLUB)
            verify(teamnameobserver).onChanged(FootballTeam(listOfTeams))
            verify(errorobserver, never()).onChanged(errormsg)
        }

    @ExperimentalCoroutinesApi
    @Test
    fun `when api call is a failure, it returns error`()= coroutinesTestRule.testDispatcher.runBlockingTest {
        `when`(myNetwork.getTeam(Constants.CLUB)).thenReturn(Response.error<FootballTeam>(422,
            errormsg.toResponseBody("text/plain".toMediaTypeOrNull())
        ))
        viewmodel.getFromFootballAPI()
        verify(teamnameobserver, never()).onChanged(FootballTeam(listOfTeams))
    }

}