package com.okation.aivideocreator.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.okation.aivideocreator.model.FakeYouEntity

@Dao
interface FakeYouDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(fakeYouEntity: FakeYouEntity)

    @Query("SELECT * FROM fakeyou_entity ORDER BY id ASC")
    fun getFakeYouList(): LiveData<List<FakeYouEntity>>

}