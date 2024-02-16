package com.okation.aivideocreator.model

data class FakeYouResponse(
    val success: Boolean,
    val inference_job_token: String,
    val inference_job_token_type: String
)