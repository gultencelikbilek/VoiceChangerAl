package com.okation.aivideocreator.api

import com.okation.aivideocreator.model.FakeYouPostRequest
import com.okation.aivideocreator.model.FakeYouResponse
import com.okation.aivideocreator.model.StateResponse
import com.okation.aivideocreator.utils.Constants
import retrofit2.Call
import retrofit2.http.*

interface FakeYouApiService {
    @POST(Constants.END_POINT_POST)
    @Headers("Accept: application/json", "Content-Type:application/json")
    fun getPost(@Body postRequest: FakeYouPostRequest): Call<FakeYouResponse>

    @GET(Constants.END_POINT_JOB_TOKEN)
    @Headers("Accept: application/json")
    fun getJobtoken(
        @Path("inference_job_token") token: String,
    ): Call<StateResponse>
}