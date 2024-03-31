package com.md29.husein.core.data.source.remote

import android.util.Log
import com.md29.husein.core.data.Resource
import com.md29.husein.core.data.source.remote.network.ApiResponse
import com.md29.husein.core.data.source.remote.network.ApiService
import com.md29.husein.core.data.source.remote.response.ItemsItem
import com.md29.husein.core.domain.model.Follow
import com.md29.husein.core.domain.model.UserDetail
import com.md29.husein.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllUser(name: String): Flow<ApiResponse<List<ItemsItem>>> {
        return flow {
            try {
                val response = apiService.getUser(name)
                val dataArray = response.items
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.items))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e("RemoteDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getUserDetail(query: String): Flow<Resource<UserDetail>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = apiService.getDetailUser(query)
                if (response.isSuccessful) {
                    val detailUser = response.body()
                    if (detailUser != null) {
                        val user = DataMapper.mapResponseDetailToDomain(detailUser)
                        emit(Resource.Success(user))
                    } else {
                        emit(Resource.Error("UserEntity detail not found"))
                    }
                } else {
                    emit(Resource.Error("Failed to fetch user detail: ${response.message()}"))
                }
            } catch (e: Exception) {
                emit(Resource.Error("Exception occurred: ${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollow(name: String): Flow<Resource<List<Follow>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = apiService.getFollow(name, "followers")
                if (response.isSuccessful) {
                    val followers = response.body()
                    if (followers != null) {
                        val user = DataMapper.mapResponsesFollowToDomain(followers)
                        emit(Resource.Success(user))
                    } else {
                        emit(Resource.Error("Followers not found"))
                    }
                } else {
                    emit(Resource.Error("Failed to fetch followers: ${response.message()}"))
                }
            } catch (e: Exception) {
                emit(Resource.Error("Exception occurred: ${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollowing(name: String): Flow<Resource<List<Follow>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = apiService.getFollow(name, "following")
                if (response.isSuccessful) {
                    val following = response.body()
                    if (following != null) {
                        val user = DataMapper.mapResponsesFollowToDomain(following)
                        emit(Resource.Success(user))
                    } else {
                        emit(Resource.Error("Following not found"))
                    }
                } else {
                    emit(Resource.Error("Failed to fetch following: ${response.message()}"))
                }
            } catch (e: Exception) {
                emit(Resource.Error("Exception occurred: ${e.message}"))
            }
        }.flowOn(Dispatchers.IO)
    }

}

