package com.nunop.doggobreed.di

import android.content.Context
import androidx.room.Room
import com.nunop.doggobreed.core.data.local.DoggoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): DoggoDatabase = Room.databaseBuilder(
        context.applicationContext,
        DoggoDatabase::class.java,
        "doggo_db"
    ).build()
}