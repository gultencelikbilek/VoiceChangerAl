package com.okation.aivideocreator.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.okation.aivideocreator.di.IFakeYouRepositoryImpl
import com.okation.aivideocreator.model.FakeYouEntity
import com.okation.aivideocreator.model.FakeYouPostRequest
import com.okation.aivideocreator.model.FakeYouResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FakeYouViewModel
@Inject constructor(private val repositoryImpl: IFakeYouRepositoryImpl) : ViewModel() {

    private val _response = MutableLiveData<FakeYouResponse>()
    val postResponse: LiveData<FakeYouResponse>
        get() = _response

    init {
        getFakeYouList()
    }

    fun getPost(postRequest: FakeYouPostRequest) = viewModelScope.launch {
        repositoryImpl.getPost(postRequest).enqueue(object : Callback<FakeYouResponse> {
            override fun onResponse(
                call: Call<FakeYouResponse>,
                response: Response<FakeYouResponse>
            ) {
                if (response.isSuccessful) {
                    _response.value = response.body()
                }
            }

            override fun onFailure(call: Call<FakeYouResponse>, t: Throwable) {
                Log.d("onFailure", t.message.toString())
            }
        })
    }

    fun addUser(fakeYouEntity: FakeYouEntity) = viewModelScope.launch {
        repositoryImpl.addUser(fakeYouEntity)
    }

    fun getFakeYouList() = repositoryImpl.getFakeYouList()
}