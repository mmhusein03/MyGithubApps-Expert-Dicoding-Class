package com.md29.husein.core.data.source.remote.network

import com.md29.husein.core.data.source.remote.response.DetailGitUser
import com.md29.husein.core.data.source.remote.response.ItemsItem
import com.md29.husein.core.data.source.remote.response.UserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getUser(
        @Query("q") username: String,
    ): UserResponse

    @GET("users/{username}")
    suspend fun getDetailUser(
        @Path("username") username: String,
    ): Response<DetailGitUser>

    @GET("users/{username}/{tipe}")
    suspend fun getFollow(
        @Path("username") username: String,
        @Path("tipe") tipe: String,
    ): Response<List<ItemsItem>>
}