package com.okation.aivideocreator.model

data class FakeYouPostRequest(
    val uuid_idempotency_token: String,
    val tts_model_token: String,
    val inference_text: String
)