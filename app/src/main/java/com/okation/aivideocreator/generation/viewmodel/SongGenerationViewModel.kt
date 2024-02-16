package com.okation.aivideocreator.generation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okation.aivideocreator.di.IFakeYouRepositoryImpl
import com.okation.aivideocreator.model.State
import com.okation.aivideocreator.model.StateResponse
import com.okation.aivideocreator.utils.StringConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

@HiltViewModel
class SongGenerationViewModel @Inject constructor(private val repositoryImpl: IFakeYouRepositoryImpl) :
    ViewModel() {

    private val _responseJobToken = MutableLiveData<State>()
    val responseJobToken: LiveData<State>
        get() = _responseJobToken

    fun getJobToken(token: String) = viewModelScope.launch {
        var counter = 0
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                repositoryImpl.getJobToken(token).enqueue(object : Callback<StateResponse> {
                    override fun onResponse(
                        call: Call<StateResponse>,
                        response: Response<StateResponse>
                    ) {
                        if (response.isSuccessful) {
                            _responseJobToken.value = response.body()?.state
                        }
                        if (response.body()?.state?.job_token == StringConstants.completeSucces) {
                            timer.cancel()
                        }
                    }

                    override fun onFailure(call: Call<StateResponse>, t: Throwable) {
                        Log.d("SongGenerationViewModel onFailure", t.message.toString())
                    }
                })
                counter++
            }
        }, 0, 300L)
    }
}