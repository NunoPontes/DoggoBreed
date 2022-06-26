package com.nunop.doggobreed.breedDetails.data.repository

import com.nunop.doggobreed.breedDetails.data.local.LocalDataSourceBreedDetails
import com.nunop.doggobreed.breedDetails.data.mapper.toBreedDetails
import com.nunop.doggobreed.breedDetails.data.mapper.toBreedDetailsDB
import com.nunop.doggobreed.breedDetails.data.remote.RemoteDataSourceBreedDetails
import com.nunop.doggobreed.breedDetails.data.remote.dto.BreedDetails
import com.nunop.doggobreed.breedDetails.domain.repository.BreedDetailsRepository
import com.nunop.doggobreed.core.utils.Error
import com.nunop.doggobreed.core.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.net.UnknownHostException
import javax.inject.Inject

class BreedDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSourceBreedDetails: RemoteDataSourceBreedDetails,
    private val localDataSourceBreedDetails: LocalDataSourceBreedDetails
) : BreedDetailsRepository {

    override suspend fun getBreedDetails(
        breed: String,
    ): Flow<Resource<BreedDetails>> {

        val breedDetailsDb = localDataSourceBreedDetails.getBreedDetails(breed)
        if (breedDetailsDb != null) {
            return flow {
                val allDogBreeds = breedDetailsDb.toBreedDetails()
                emit(Resource.Success(allDogBreeds))
            }.flowOn(Dispatchers.IO)
        }
        return flow {
            try {
                val breedDetails =
                    remoteDataSourceBreedDetails.getBreedDetails(
                        breed = breed,
                        numberPhotos = 10
                    )
                if (!breedDetails.isSuccessful) {
                    emit(Resource.Error(Error.GENERIC.error))
                } else {
                    breedDetails.body()?.toBreedDetailsDB(breed)
                        ?.let { localDataSourceBreedDetails.insertBreedDetails(it) }
                    // Emit the list to the stream
                    emit(Resource.Success(breedDetails.body()))
                }
            } catch (e: UnknownHostException) {
                emit(Resource.Error(Error.NOINTERNET.error))
            }

        }.flowOn(Dispatchers.IO) // Use the IO thread for this Flow
    }

    override suspend fun getSubBreedDetails(
        breed: String,
        subBreed: String
    ): Flow<Resource<BreedDetails>> {

        val breedDetailsDb = localDataSourceBreedDetails.getBreedDetails(subBreed)
        if (breedDetailsDb != null) {
            return flow {
                val allDogBreeds = breedDetailsDb.toBreedDetails()
                emit(Resource.Success(allDogBreeds))
            }.flowOn(Dispatchers.IO)
        }
        return flow {
            try {
                val breedDetails =
                    remoteDataSourceBreedDetails.getSubBreedDetails(
                        breed = breed,
                        subBreed = subBreed,
                        numberPhotos = 10
                    )
                if (!breedDetails.isSuccessful) {
                    emit(Resource.Error(Error.GENERIC.error))
                } else {
                    breedDetails.body()?.toBreedDetailsDB(subBreed)
                        ?.let { localDataSourceBreedDetails.insertBreedDetails(it) }
                    // Emit the list to the stream
                    emit(Resource.Success(breedDetails.body()))
                }
            } catch (e: UnknownHostException) {
                emit(Resource.Error(Error.NOINTERNET.error))
            }

        }.flowOn(Dispatchers.IO) // Use the IO thread for this Flow


    }
}