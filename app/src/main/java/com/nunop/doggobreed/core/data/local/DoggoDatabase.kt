package com.nunop.doggobreed.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nunop.doggobreed.breedDetails.data.local.dao.BreedDetailsDao
import com.nunop.doggobreed.breedDetails.data.local.entities.BreedDetailsDB
import com.nunop.doggobreed.breeds.data.local.dao.AllDogBreedsDao
import com.nunop.doggobreed.breeds.data.local.entities.AllDogBreedsDB

@TypeConverters(StringListMapConverter::class)
@Database(
    entities = [
        AllDogBreedsDB::class,
        BreedDetailsDB::class
    ],
    version = 1,
    exportSchema = false
)
abstract class DoggoDatabase : RoomDatabase() {
    abstract val allDogBreedsDao: AllDogBreedsDao
    abstract val breedDetailsDao: BreedDetailsDao
}