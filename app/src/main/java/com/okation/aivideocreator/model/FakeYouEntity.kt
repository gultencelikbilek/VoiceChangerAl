package com.okation.aivideocreator.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fakeyou_entity")
data class FakeYouEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val image: Int,
    val vaw_audio: String,
    val name: String,
    val userInputText: String,

    )
