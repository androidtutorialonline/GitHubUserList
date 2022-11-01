package com.app.gitHubUserList.data.api

import com.app.gitHubUserList.model.GetUserListItem
import retrofit2.http.GET

interface ApiService {

    @GET("/users")
    suspend fun getUserList(): List<GetUserListItem>

}