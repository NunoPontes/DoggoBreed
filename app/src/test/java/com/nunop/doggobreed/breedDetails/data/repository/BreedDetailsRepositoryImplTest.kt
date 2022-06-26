package com.nunop.doggobreed.breedDetails.data.repository

import com.google.common.truth.Truth.assertThat
import com.nunop.doggobreed.breedDetails.data.local.LocalDataSourceBreedDetails
import com.nunop.doggobreed.breedDetails.data.local.entities.BreedDetailsDB
import com.nunop.doggobreed.breedDetails.data.remote.RemoteDataSourceBreedDetails
import com.nunop.doggobreed.breedDetails.data.remote.dto.BreedDetails
import com.nunop.doggobreed.breedDetails.domain.repository.BreedDetailsRepository
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

class BreedDetailsRepositoryImplTest {

    private lateinit var repository: BreedDetailsRepository
    private lateinit var remoteDataSource: RemoteDataSourceBreedDetails
    private lateinit var localDataSource: LocalDataSourceBreedDetails

    @Before
    fun setUp() {
        localDataSource = mock(LocalDataSourceBreedDetails::class.java)
        remoteDataSource = mock(RemoteDataSourceBreedDetails::class.java)
        repository = BreedDetailsRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `getBreedDetails from local data source success`() = runBlocking {
        val breedOrSubBreed = "Breed"
        val dogBreedsDB = BreedDetailsDB(
            breedOrSubBreed = breedOrSubBreed,
            status = "Success",
            message = listOf("SubBreed1", "SubBreed2")
        )
        val dogBreeds = BreedDetails(
            status = "Success",
            message = listOf("SubBreed1", "SubBreed2")
        )

        doReturn(dogBreedsDB).`when`(localDataSource).getBreedDetails(breedOrSubBreed)

        val result = repository.getBreedDetails(breedOrSubBreed).toList().first()
        val expected = Resource.Success(dogBreeds)

        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat(result.data).isEqualTo(expected.data)

        verify(localDataSource).getBreedDetails(breedOrSubBreed)

        verifyNoMoreInteractions(localDataSource)
        verifyNoMoreInteractions(remoteDataSource)
    }

    @Test
    fun `getBreedDetails from local data source no data, use remote data source success`() =
        runBlocking {
            val breedOrSubBreed = "Breed"
            val dogBreedsDB = BreedDetailsDB(
                breedOrSubBreed = breedOrSubBreed,
                status = "Success",
                message = listOf("SubBreed1", "SubBreed2")
            )
            val dogBreeds = BreedDetails(
                status = "Success",
                message = listOf("SubBreed1", "SubBreed2")
            )

            doReturn(null).`when`(localDataSource).getBreedDetails(breedOrSubBreed)
            doReturn(Response.success(dogBreeds)).`when`(remoteDataSource).getBreedDetails(
                breed = breedOrSubBreed, numberPhotos = 10
            )

            val result = repository.getBreedDetails(breedOrSubBreed).toList().first()
            val expected = Resource.Success(dogBreeds)

            assertThat(result).isInstanceOf(Resource.Success::class.java)
            assertThat(result.data).isEqualTo(expected.data)

            verify(localDataSource).getBreedDetails(breedOrSubBreed)
            verify(remoteDataSource).getBreedDetails(breed = breedOrSubBreed, numberPhotos = 10)
            verify(localDataSource).insertBreedDetails(dogBreedsDB)

            verifyNoMoreInteractions(localDataSource)
            verifyNoMoreInteractions(remoteDataSource)
        }

    @Test
    fun `getBreedDetails from local data source no data, use remote data source error`() =
        runBlocking {
            val breedOrSubBreed = "Breed"
            val errorResponse = Response.error<String>(
                400,
                "".toResponseBody("application/json".toMediaTypeOrNull())
            )

            doReturn(null).`when`(localDataSource).getBreedDetails(breedOrSubBreed)
            doReturn(errorResponse).`when`(remoteDataSource).getBreedDetails(
                breed = breedOrSubBreed, numberPhotos = 10
            )

            val result = repository.getBreedDetails(breedOrSubBreed).toList().first()

            assertThat(result).isInstanceOf(Resource.Error::class.java)
            assertThat(result.message).isEqualTo(Error.GENERIC.error)

            verify(localDataSource).getBreedDetails(breedOrSubBreed)
            verify(remoteDataSource).getBreedDetails(breed = breedOrSubBreed, numberPhotos = 10)

            verifyNoMoreInteractions(localDataSource)
            verifyNoMoreInteractions(remoteDataSource)
        }

    @Test
    fun `getSubBreedDetails from local data source success`() = runBlocking {
        val breed = "Breed"
        val subBreed = "SubBreed"
        val dogBreedsDB = BreedDetailsDB(
            breedOrSubBreed = breed,
            status = "Success",
            message = listOf("SubBreed1", "SubBreed2")
        )
        val dogBreeds = BreedDetails(
            status = "Success",
            message = listOf("SubBreed1", "SubBreed2")
        )

        doReturn(dogBreedsDB).`when`(localDataSource).getBreedDetails(subBreed)

        val result = repository.getSubBreedDetails(breed, subBreed).toList().first()
        val expected = Resource.Success(dogBreeds)

        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat(result.data).isEqualTo(expected.data)

        verify(localDataSource).getBreedDetails(subBreed)

        verifyNoMoreInteractions(localDataSource)
        verifyNoMoreInteractions(remoteDataSource)
    }

    @Test
    fun `getSubBreedDetails from local data source no data, use remote data source success`() =
        runBlocking {
            val breed = "Breed"
            val subBreed = "SubBreed"
            val dogBreedsDB = BreedDetailsDB(
                breedOrSubBreed = subBreed,
                status = "Success",
                message = listOf("SubBreed1", "SubBreed2")
            )
            val dogBreeds = BreedDetails(
                status = "Success",
                message = listOf("SubBreed1", "SubBreed2")
            )

            doReturn(null).`when`(localDataSource).getBreedDetails(subBreed)
            doReturn(Response.success(dogBreeds)).`when`(remoteDataSource).getSubBreedDetails(
                subBreed = subBreed, breed = breed, numberPhotos = 10
            )

            val result =
                repository.getSubBreedDetails(subBreed = subBreed, breed = breed).toList().first()
            val expected = Resource.Success(dogBreeds)

            assertThat(result).isInstanceOf(Resource.Success::class.java)
            assertThat(result.data).isEqualTo(expected.data)

            verify(localDataSource).getBreedDetails(subBreed)
            verify(remoteDataSource).getSubBreedDetails(
                subBreed = subBreed,
                breed = breed,
                numberPhotos = 10
            )
            verify(localDataSource).insertBreedDetails(dogBreedsDB)

            verifyNoMoreInteractions(localDataSource)
            verifyNoMoreInteractions(remoteDataSource)
        }

    @Test
    fun `getSubBreedDetails from local data source no data, use remote data source error`() =
        runBlocking {
            val breed = "Breed"
            val subBreed = "SubBreed"
            val errorResponse = Response.error<String>(
                400,
                "".toResponseBody("application/json".toMediaTypeOrNull())
            )

            doReturn(null).`when`(localDataSource).getBreedDetails(subBreed)
            doReturn(errorResponse).`when`(remoteDataSource).getSubBreedDetails(
                subBreed = subBreed, breed = breed, numberPhotos = 10
            )

            val result =
                repository.getSubBreedDetails(subBreed = subBreed, breed = breed).toList().first()

            assertThat(result).isInstanceOf(Resource.Error::class.java)
            assertThat(result.message).isEqualTo(Error.GENERIC.error)

            verify(localDataSource).getBreedDetails(subBreed)
            verify(remoteDataSource).getSubBreedDetails(
                subBreed = subBreed,
                breed = breed,
                numberPhotos = 10
            )

            verifyNoMoreInteractions(localDataSource)
            verifyNoMoreInteractions(remoteDataSource)
        }
}