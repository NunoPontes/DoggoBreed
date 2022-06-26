package com.nunop.doggobreed.breedDetails.data.local.dao

import androidx.room.*
import com.nunop.doggobreed.breedDetails.data.local.entities.BreedDetailsDB

@Dao
interface BreedDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreedDetails(
        dogBreeds: BreedDetailsDB
    )

    @Transaction //To ensure this happens atomically
    @Query("SELECT * FROM breeddetailsdb WHERE breedOrSubBreed= :breed")
    suspend fun getBreedDetails(breed: String): BreedDetailsDB?
}