package com.nunop.doggobreed.breedDetails.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.nunop.doggobreed.breedDetails.data.local.entities.BreedDetailsDB
import com.nunop.doggobreed.core.data.local.DoggoDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class BreedDetailsDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: DoggoDatabase

    private lateinit var dao: BreedDetailsDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.breedDetailsDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertBreedDetailsDBSuccess() = runTest {
        val breedOrSubBreed = "Breed"
        val dogBreeds = BreedDetailsDB(
            breedOrSubBreed = breedOrSubBreed,
            status = "Success",
            message = listOf("SubBreed1", "SubBreed2")
        )
        dao.insertBreedDetails(dogBreeds)

        val allDogBreeds = dao.getBreedDetails(breedOrSubBreed)

        assertThat(allDogBreeds).isNotNull()
        assertThat(allDogBreeds?.message?.size).isEqualTo(2)
        assertThat(allDogBreeds).isEqualTo(dogBreeds)
    }

    @Test
    fun insertBreedDetailsDBDuplicatedSuccess() = runTest {
        val breedOrSubBreed = "Breed"
        val dogBreeds = BreedDetailsDB(
            breedOrSubBreed = breedOrSubBreed,
            status = "Success",
            message = listOf("SubBreed1", "SubBreed2")
        )
        val dogBreeds2 = BreedDetailsDB(
            breedOrSubBreed = breedOrSubBreed,
            status = "Success",
            message = listOf("SubBreed1", "SubBreed2")
        )

        dao.insertBreedDetails(dogBreeds)
        dao.insertBreedDetails(dogBreeds2)

        val allDogBreeds = dao.getBreedDetails(breedOrSubBreed)

        assertThat(allDogBreeds).isNotNull()
        assertThat(allDogBreeds?.message?.size).isEqualTo(2)
        assertThat(allDogBreeds).isEqualTo(dogBreeds)
    }

    @Test
    fun insertBreedDetailsDBMultipleSuccess() = runTest {
        val breedOrSubBreed = "Breed"
        val breedOrSubBreed2 = "SubBreed"
        val dogBreeds = BreedDetailsDB(
            breedOrSubBreed = breedOrSubBreed,
            status = "Success",
            message = listOf("SubBreed1", "SubBreed2")
        )
        val dogBreeds2 = BreedDetailsDB(
            breedOrSubBreed = breedOrSubBreed2,
            status = "Success",
            message = listOf("SubBreed1", "SubBreed2")
        )

        dao.insertBreedDetails(dogBreeds)
        dao.insertBreedDetails(dogBreeds2)

        val allDogBreeds = dao.getBreedDetails(breedOrSubBreed)
        val allDogBreeds2 = dao.getBreedDetails(breedOrSubBreed2)

        assertThat(allDogBreeds).isNotNull()
        assertThat(allDogBreeds?.message?.size).isEqualTo(2)
        assertThat(allDogBreeds).isEqualTo(dogBreeds)

        assertThat(allDogBreeds2).isNotNull()
        assertThat(allDogBreeds2?.message?.size).isEqualTo(2)
        assertThat(allDogBreeds2).isEqualTo(dogBreeds2)
    }

    @Test
    fun getBreedDetailsDBEmpty() = runTest {
        val allDogBreeds = dao.getBreedDetails("Breed")

        assertThat(allDogBreeds).isEqualTo(null)
    }
}