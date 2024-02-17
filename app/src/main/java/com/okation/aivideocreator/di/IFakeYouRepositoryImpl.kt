package com.okation.aivideocreator.di

import androidx.lifecycle.LiveData
import com.okation.aivideocreator.api.FakeYouApiService
import com.okation.aivideocreator.database.FakeYouDatabase
import com.okation.aivideocreator.model.FakeYouEntity
import com.okation.aivideocreator.model.FakeYouPostRequest
import com.okation.aivideocreator.model.FakeYouResponse
import com.okation.aivideocreator.model.StateResponse
import com.okation.aivideocreator.repository.IFakeYouRepository
import retrofit2.Call
import javax.inject.Inject

class IFakeYouRepositoryImpl @Inject constructor(
    val apiService: FakeYouApiService,
    val db: FakeYouDatabase
) : IFakeYouRepository {
    override fun getPost(postRequest: FakeYouPostRequest): Call<FakeYouResponse> {
        return apiService.getPost(postRequest)
    }

    override fun getJobToken(token: String): Call<StateResponse> {
        return apiService.getJobtoken(token)
    }

    override suspend fun addUser(fakeYouEntity: FakeYouEntity) {
        db.fakeYouDao.addUser(fakeYouEntity)
    }

    override fun getFakeYouList(): LiveData<List<FakeYouEntity>> {
        return db.fakeYouDao.getFakeYouList()
    }
}