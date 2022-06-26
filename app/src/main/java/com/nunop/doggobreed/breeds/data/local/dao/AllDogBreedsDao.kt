package com.nunop.doggobreed.breeds.data.local.dao

import androidx.room.*
import com.nunop.doggobreed.breeds.data.local.entities.AllDogBreedsDB

@Dao
interface AllDogBreedsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreeds(
        dogBreeds: AllDogBreedsDB
    )

    @Transaction //To ensure this happens atomically
    @Query("SELECT * FROM alldogbreedsdb")
    suspend fun getAllDogBreeds(): AllDogBreedsDB?
}