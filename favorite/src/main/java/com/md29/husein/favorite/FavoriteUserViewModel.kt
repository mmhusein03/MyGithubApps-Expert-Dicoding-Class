package com.md29.husein.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.md29.husein.core.domain.usecase.UserUseCase

class FavoriteUserViewModel(
    useCase: UserUseCase,
) : ViewModel() {

    val getFavoriteUser = useCase.getFavoriteUser().asLiveData()

}