package com.okation.aivideocreator.di

import android.content.Context
import androidx.room.Room
import com.okation.aivideocreator.api.FakeYouApiService
import com.okation.aivideocreator.database.FakeYouDatabase
import com.okation.aivideocreator.home.repository.IFakeYouRepository
import com.okation.aivideocreator.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): FakeYouApiService =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_PERSON)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FakeYouApiService::class.java)

    @Provides
    @Singleton
    fun providesFakeYouRoomDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            FakeYouDatabase::class.java,
            "fake_you_db"
        ).build()

    @Provides
    @Singleton
    fun providesRepository(
        apiService: FakeYouApiService,
        db: FakeYouDatabase
    ): IFakeYouRepository = IFakeYouRepositoryImpl(apiService, db)
}