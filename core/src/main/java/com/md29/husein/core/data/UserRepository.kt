package com.md29.husein.core.data

import com.md29.husein.core.data.source.local.LocalDataSource
import com.md29.husein.core.data.source.remote.RemoteDataSource
import com.md29.husein.core.data.source.remote.network.ApiResponse
import com.md29.husein.core.domain.model.Follow
import com.md29.husein.core.domain.model.User
import com.md29.husein.core.domain.model.UserDetail
import com.md29.husein.core.domain.repository.IUserRepository
import com.md29.husein.core.utils.AppExecutors
import com.md29.husein.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors,
) : IUserRepository {

    override fun getAllUser(name: String): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())
        val query = "%$name%"
        val dbSource =
            localDataSource.getAllUser(query).map { DataMapper.mapEntitiesToDomain(it) }.first()

        if (dbSource.isEmpty()) {
            when (val apiResponse = remoteDataSource.getAllUser(name).first()) {
                is ApiResponse.Success -> {
                    val userList = DataMapper.mapResponsesToEntities(apiResponse.data)
                    localDataSource.insertUser(userList)
                    emitAll(
                        localDataSource.getAllUser(query)
                            .map { Resource.Success(DataMapper.mapEntitiesToDomain(it)) })
                }

                is ApiResponse.Empty -> {
                    emitAll(
                        localDataSource.getAllUser(query)
                            .map { Resource.Success(DataMapper.mapEntitiesToDomain(it)) })
                }

                is ApiResponse.Error -> {
                    emit(Resource.Error(apiResponse.errorMessage))
                }
            }
        } else {
            emitAll(
                localDataSource.getAllUser(query)
                    .map { Resource.Success(DataMapper.mapEntitiesToDomain(it)) })
        }
    }


    override fun getFavoriteUser(): Flow<List<User>> {
        return localDataSource.getFavoriteUser().map { DataMapper.mapEntitiesToDomain(it) }
    }

    override fun setFavoriteUser(user: User, state: Boolean) {
        val userEntity = DataMapper.mapDomainToEntities(user)
        appExecutors.diskIO().execute { localDataSource.setFavoriteUser(userEntity, state) }
    }

    override suspend fun getUserDetail(name: String): Flow<Resource<UserDetail>> {
        return remoteDataSource.getUserDetail(name)
    }

    override suspend fun getFollow(name: String): Flow<Resource<List<Follow>>> {
        return remoteDataSource.getFollow(name)
    }

    override suspend fun getFollowing(name: String): Flow<Resource<List<Follow>>> {
        return remoteDataSource.getFollowing(name)
    }


}