package com.nunop.doggobreed.di

import android.content.Context
import androidx.room.Room
import com.nunop.doggobreed.core.data.local.DoggoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestPersistenceModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, DoggoDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}