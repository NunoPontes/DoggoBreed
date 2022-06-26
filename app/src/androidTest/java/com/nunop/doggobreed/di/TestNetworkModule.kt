package com.nunop.doggobreed.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.nunop.doggobreed.breedDetails.data.remote.RemoteDataSourceBreedDetailsImpl
import com.nunop.doggobreed.breeds.data.remote.RemoteDataSourceBreedsImpl
import com.nunop.doggobreed.core.data.remote.DogBreedApi
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
object TestNetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(StethoInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://localhost:9090/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideRickAndMortyAPI(retrofit: Retrofit): DogBreedApi {
        return retrofit
            .create(DogBreedApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSourceBreeds(api: DogBreedApi) = RemoteDataSourceBreedsImpl(api)

    @Provides
    @Singleton
    fun provideRemoteDataSourceBreedDetails(api: DogBreedApi) =
        RemoteDataSourceBreedDetailsImpl(api)
}