package com.md29.husein.favorite

import android.content.Context
import com.md29.husein.mygithubapps.di.FavoriteModuleDepedencies
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [FavoriteModuleDepedencies::class])
interface FavoriteComponent {

    fun inject(fragment: FavoriteUserFragment)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDepedencies: FavoriteModuleDepedencies):Builder
        fun build(): FavoriteComponent
    }
}