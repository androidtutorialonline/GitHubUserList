package com.app.gitHubUserList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gitHubUserList.data.api.MainRepository
import com.app.gitHubUserList.data.api.Resource
import com.app.gitHubUserList.model.GetUserListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val apiHelper: MainRepository
) : ViewModel() {

    val userInfo = MutableLiveData<Resource<List<GetUserListItem>>>()

    fun getUserList() = viewModelScope.launch {
        userInfo.postValue(Resource.loading(null))
        apiHelper.getUserList()
            .catch { e ->
                userInfo.postValue(Resource.error(e.toString(), null))
            }
            .collect {
                userInfo.postValue(Resource.success(it))
            }
    }
}