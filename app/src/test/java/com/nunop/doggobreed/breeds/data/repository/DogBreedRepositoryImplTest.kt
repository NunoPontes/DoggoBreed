package com.nunop.doggobreed.breeds.data.repository

import com.google.common.truth.Truth.assertThat
import com.nunop.doggobreed.breeds.data.local.LocalDataSourceBreeds
import com.nunop.doggobreed.breeds.data.local.entities.AllDogBreedsDB
import com.nunop.doggobreed.breeds.data.remote.RemoteDataSourceBreeds
import com.nunop.doggobreed.breeds.data.remote.dto.AllDogBreeds
import com.nunop.doggobreed.breeds.domain.repository.DogBreedRepository
import com.nunop.doggobreed.core.utils.Error
import com.nunop.doggobreed.core.utils.Resource
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import retrofit2.Response


class DogBreedRepositoryImplTest {
    //TODO: use mockwebserver, success and error response

    private lateinit var repository: DogBreedRepository
    private lateinit var localDataSource: LocalDataSourceBreeds
    private lateinit var remoteDataSource: RemoteDataSourceBreeds

    @Before
    fun setUp() {
        localDataSource = mock(LocalDataSourceBreeds::class.java)
        remoteDataSource = mock(RemoteDataSourceBreeds::class.java)
        repository = DogBreedRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `getDogBreeds from local data source success`() = runBlocking {
        val map: MutableMap<String, List<String>> = mutableMapOf()
        map["Breed"] = listOf("SubBreed1", "SubBreed2")
        val allDogBreedsExpected = AllDogBreedsDB(
            status = "Success",
            message = map
        )
        val allDogBreedsResult = AllDogBreeds(
            status = "Success",
            message = map
        )

        doReturn(allDogBreedsExpected).`when`(localDataSource).getAllBreeds()

        val result = repository.getDogBreeds().toList().first()
        val expected = Resource.Success(allDogBreedsResult)

        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat(result.data).isEqualTo(expected.data)

        verify(localDataSource).getAllBreeds()

        verifyNoMoreInteractions(localDataSource)
        verifyNoMoreInteractions(remoteDataSource)
    }

    @Test
    fun `getDogBreeds from local data source no data, use remote data source success`() =
        runBlocking {
            val map: MutableMap<String, List<String>> = mutableMapOf()
            map["Breed"] = listOf("SubBreed1", "SubBreed2")
            val allDogBreedsDB = AllDogBreedsDB(
                status = "Success",
                message = map
            )
            val allDogBreedsExpected = AllDogBreeds(
                status = "Success",
                message = map
            )

            doReturn(null).`when`(localDataSource).getAllBreeds()
            doReturn(Response.success(allDogBreedsExpected)).`when`(remoteDataSource).getDogBreeds()

            val result = repository.getDogBreeds().toList().first()
            val expected = Resource.Success(allDogBreedsExpected)

            assertThat(result).isInstanceOf(Resource.Success::class.java)
            assertThat(result.data).isEqualTo(expected.data)

            verify(localDataSource).getAllBreeds()
            verify(remoteDataSource).getDogBreeds()
            verify(localDataSource).insertBreeds(allDogBreedsDB)

            verifyNoMoreInteractions(localDataSource)
            verifyNoMoreInteractions(remoteDataSource)
        }


    @Test
    fun `getDogBreeds from local data source no data, use remote data source error`() =
        runBlocking {
            val errorResponse = Response.error<String>(
                400,
                "".toResponseBody("application/json".toMediaTypeOrNull())
            )

            doReturn(null).`when`(localDataSource).getAllBreeds()
            doReturn(errorResponse).`when`(remoteDataSource).getDogBreeds()


            val result = repository.getDogBreeds().toList().first()

            assertThat(result).isInstanceOf(Resource.Error::class.java)
            assertThat(result.message).isEqualTo(Error.GENERIC.error)

            verify(localDataSource).getAllBreeds()
            verify(remoteDataSource).getDogBreeds()

            verifyNoMoreInteractions(localDataSource)
            verifyNoMoreInteractions(remoteDataSource)
        }
}