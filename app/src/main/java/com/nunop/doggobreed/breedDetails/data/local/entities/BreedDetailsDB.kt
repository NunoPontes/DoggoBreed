package com.nunop.doggobreed.breedDetails.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BreedDetailsDB(
    @PrimaryKey
    val breedOrSubBreed: String,
    val status: String,
    val message: List<String>
)
