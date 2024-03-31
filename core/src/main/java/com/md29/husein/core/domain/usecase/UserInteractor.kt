package com.md29.husein.core.domain.usecase

import com.md29.husein.core.data.Resource
import com.md29.husein.core.domain.model.Follow
import com.md29.husein.core.domain.model.User
import com.md29.husein.core.domain.model.UserDetail
import com.md29.husein.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInteractor @Inject constructor(private val userRepository: IUserRepository) :
    UserUseCase {
    override fun getAllUser(name: String): Flow<Resource<List<User>>> =
        userRepository.getAllUser(name)

    override fun getFavoriteUser(): Flow<List<User>> =
        userRepository.getFavoriteUser()

    override fun setFavoriteUser(user: User, state: Boolean) =
        userRepository.setFavoriteUser(user, state)

    override suspend fun getUserDetail(name: String): Flow<Resource<UserDetail>> =
        userRepository.getUserDetail(name)

    override suspend fun getFollow(name: String): Flow<Resource<List<Follow>>> =
        userRepository.getFollow(name)

    override suspend fun getFollowing(name: String): Flow<Resource<List<Follow>>> =
        userRepository.getFollowing(name)

}