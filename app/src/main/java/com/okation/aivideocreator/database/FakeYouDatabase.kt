package com.okation.aivideocreator.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.okation.aivideocreator.model.FakeYouEntity

@Database(entities = [FakeYouEntity::class], version = 1)
abstract class FakeYouDatabase : RoomDatabase() {
    abstract val fakeYouDao: FakeYouDao
}