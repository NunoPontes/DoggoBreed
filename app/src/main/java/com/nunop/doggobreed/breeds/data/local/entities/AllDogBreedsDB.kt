package com.nunop.doggobreed.breeds.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AllDogBreedsDB(
    @PrimaryKey
    val status: String,
    val message: Map<String, List<String>>
)
