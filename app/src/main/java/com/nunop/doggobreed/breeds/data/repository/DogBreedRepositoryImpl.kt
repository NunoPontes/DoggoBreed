package com.nunop.doggobreed.breeds.data.repository

import com.nunop.doggobreed.breeds.data.mapper.toAllDogBreeds
import com.nunop.doggobreed.breeds.data.mapper.toAllDogBreedsDB
import com.nunop.doggobreed.breeds.data.remote.RemoteDataSourceBreeds
import com.nunop.doggobreed.breeds.data.remote.dto.AllDogBreeds
import com.nunop.doggobreed.breeds.domain.repository.DogBreedRepository
import com.nunop.doggobreed.breeds.data.local.LocalDataSourceBreeds
import com.nunop.doggobreed.core.utils.Error
import com.nunop.doggobreed.core.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.UnknownHostException
import javax.inject.Inject

class DogBreedRepositoryImpl @Inject constructor(
    private val remoteDataSourceBreeds: RemoteDataSourceBreeds,
    private val localDataSourceBreeds: LocalDataSourceBreeds
) : DogBreedRepository {

    override suspend fun getDogBreeds(): Flow<Resource<AllDogBreeds>> {
        val allBreedsDb = localDataSourceBreeds.getAllBreeds()
        if (allBreedsDb != null) {
            return flow {
                val allDogBreeds = allBreedsDb.toAllDogBreeds()
                emit(Resource.Success(allDogBreeds))
            }.flowOn(Dispatchers.IO)
        }
        return flow {
            try {
                val dogBreeds = remoteDataSourceBreeds.getDogBreeds()
                if (!dogBreeds.isSuccessful) {
                    emit(Resource.Error(Error.GENERIC.error))
                } else {
                    dogBreeds.body()?.toAllDogBreedsDB()?.let { localDataSourceBreeds.insertBreeds(it) }
                    // Emit the list to the stream
                    emit(Resource.Success(dogBreeds.body()))
                }
            } catch (e: UnknownHostException) {
                emit(Resource.Error(Error.NOINTERNET.error))
            }

        }.flowOn(Dispatchers.IO) // Use the IO thread for this Flow
    }
}