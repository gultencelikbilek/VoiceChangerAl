package com.okation.aivideocreator.home.repository

import androidx.lifecycle.LiveData
import com.okation.aivideocreator.model.FakeYouEntity
import com.okation.aivideocreator.model.FakeYouPostRequest
import com.okation.aivideocreator.model.FakeYouResponse
import com.okation.aivideocreator.model.StateResponse
import retrofit2.Call


interface IFakeYouRepository {

    fun getPost(postRequest: FakeYouPostRequest): Call<FakeYouResponse>
    fun getJobToken(token: String): Call<StateResponse>
    suspend fun addUser(fakeYouEntity: FakeYouEntity)
    fun getFakeYouList(): LiveData<List<FakeYouEntity>>

}