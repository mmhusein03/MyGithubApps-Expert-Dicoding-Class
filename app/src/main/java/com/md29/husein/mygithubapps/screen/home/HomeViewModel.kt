package com.md29.husein.mygithubapps.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.md29.husein.core.domain.usecase.UserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: UserUseCase,
) : ViewModel() {

    fun searchUser(q: String) = useCase.getAllUser(q).asLiveData()
}