package com.app.gitHubUserList.data.api

import com.app.gitHubUserList.model.GetUserListItem
import kotlinx.coroutines.flow.Flow

interface ApiHelper {
    suspend fun getUserList(): Flow<List<GetUserListItem>>
}