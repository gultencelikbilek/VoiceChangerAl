package com.okation.aivideocreator.model

data class State(
    val attempt_count: Int?,
    val created_at: String?,
    val job_token: String?,
    val maybe_extra_status_description: Any?,
    val maybe_public_bucket_wav_audio_path: String?,
    val maybe_result_token: String?,
    val model_token: String?,
    val raw_inference_text: String?,
    val status: String?,
    val title: String?,
    val tts_model_type: String?,
    val updated_at: String?
)