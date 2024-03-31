package com.md29.husein.mygithubapps.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.md29.husein.core.domain.model.User
import com.md29.husein.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val useCase: UserUseCase,
) : ViewModel() {

    suspend fun getUserDetail(q: String) = useCase.getUserDetail(q).asLiveData()

    fun setFavoriteUser(user: User, newStatus: Boolean) =
        useCase.setFavoriteUser(user, newStatus)
}