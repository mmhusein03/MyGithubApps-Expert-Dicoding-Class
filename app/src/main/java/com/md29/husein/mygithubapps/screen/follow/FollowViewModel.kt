package com.md29.husein.mygithubapps.screen.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.md29.husein.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowViewModel @Inject constructor(
    private val useCase: UserUseCase,
) : ViewModel() {

    suspend fun getFollowers(name: String) = useCase.getFollow(name).asLiveData()
    suspend fun getFollowing(name: String) = useCase.getFollowing(name).asLiveData()
}