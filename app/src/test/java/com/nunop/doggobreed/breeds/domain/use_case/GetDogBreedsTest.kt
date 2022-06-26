package com.nunop.doggobreed.breeds.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.nunop.doggobreed.breeds.data.repository.FakeDogBreedRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetDogBreedsTest {

    private lateinit var getDogBreeds: GetDogBreeds

    @Before
    fun setUp() {
        getDogBreeds = GetDogBreeds(FakeDogBreedRepository())
    }

    @Test
    fun `Get All Dog Breed `() = runBlocking {
        val result = getDogBreeds()

        assertThat(result.toList()[0].data?.message?.get("Breed")?.get(0)).isEqualTo("SubBreed1")
        assertThat(result.toList()[0].data?.message?.get("Breed")?.get(1)).isEqualTo("SubBreed2")
        assertThat(result.toList()[0].data?.message?.get("Breed")?.size).isEqualTo(2)
    }

}