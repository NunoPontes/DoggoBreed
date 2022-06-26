package com.nunop.doggobreed.breedDetails.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.nunop.doggobreed.breedDetails.data.repository.FakeBreedDetailsRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetBreedDetailsTest {

    private lateinit var getBreedDetails: GetBreedDetails

    @Before
    fun setUp() {
        getBreedDetails = GetBreedDetails(FakeBreedDetailsRepository())
    }

    @Test
    fun `Get Breed Details`() = runBlocking {
        val breed = "breed"

        val result = getBreedDetails(breed = breed)

        assertThat(result.toList()[0].data?.message?.get(0)).isEqualTo("Breed1")
        assertThat(result.toList()[0].data?.message?.size).isEqualTo(2)
    }

    @Test
    fun `Get Sub Breed Details`() = runBlocking {
        val breed = "breed"
        val subBreed = "subBreed"

        val result = getBreedDetails(breed = breed, subBreed = subBreed)

        assertThat(result.toList()[0].data?.message?.get(0)).isEqualTo("SubBreed1")
        assertThat(result.toList()[0].data?.message?.size).isEqualTo(3)
    }
}