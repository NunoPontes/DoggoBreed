package com.nunop.doggobreed.breeds.data.local.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.nunop.doggobreed.breeds.data.local.entities.AllDogBreedsDB
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
class AllDogBreedsDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: DoggoDatabase

    private lateinit var dao: AllDogBreedsDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.allDogBreedsDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertBreedsDBSuccess() = runTest {
        val map: MutableMap<String, List<String>> = mutableMapOf()
        map["Breed"] = listOf("SubBreed1", "SubBreed2")

        val dogBreeds = AllDogBreedsDB(
            status = "Success",
            message = map
        )
        dao.insertBreeds(dogBreeds)

        val allDogBreeds = dao.getAllDogBreeds()

        assertThat(allDogBreeds).isNotNull()
        assertThat(allDogBreeds?.message?.size).isEqualTo(1)
        assertThat(allDogBreeds).isEqualTo(dogBreeds)
    }

    @Test
    fun insertBreedsDBDuplicatedSuccess() = runTest {
        val map: MutableMap<String, List<String>> = mutableMapOf()
        map["Breed"] = listOf("SubBreed1", "SubBreed2")

        val dogBreeds = AllDogBreedsDB(
            status = "Success",
            message = map
        )
        val dogBreeds2 = AllDogBreedsDB(
            status = "Success",
            message = map
        )
        dao.insertBreeds(dogBreeds)
        dao.insertBreeds(dogBreeds2)

        val allDogBreeds = dao.getAllDogBreeds()

        assertThat(allDogBreeds).isNotNull()
        assertThat(allDogBreeds?.message?.size).isEqualTo(1)
        assertThat(allDogBreeds).isEqualTo(dogBreeds)
    }

    @Test
    fun insertBreedsDBMultipleSuccess() = runTest {
        val map: MutableMap<String, List<String>> = mutableMapOf()
        map["Breed"] = listOf("SubBreed1", "SubBreed2")
        val map2: MutableMap<String, List<String>> = mutableMapOf()
        map2["Breed2"] = listOf("SubBreed3", "SubBreed4")

        val dogBreeds = AllDogBreedsDB(
            status = "Success",
            message = map
        )
        val dogBreeds2 = AllDogBreedsDB(
            status = "Failed",
            message = map2
        )
        dao.insertBreeds(dogBreeds)
        dao.insertBreeds(dogBreeds2)

        val allDogBreeds = dao.getAllDogBreeds()

        assertThat(allDogBreeds).isNotNull()
        assertThat(allDogBreeds?.message?.size).isEqualTo(1)
        assertThat(allDogBreeds).isEqualTo(dogBreeds)
    }

    @Test
    fun getBreedsDBEmpty() = runTest {
        val allDogBreeds = dao.getAllDogBreeds()

        assertThat(allDogBreeds).isEqualTo(null)
    }
}