package com.app.gitHubUserList

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.gitHubUserList.data.api.MainRepository
import com.app.gitHubUserList.data.api.Resource
import com.app.gitHubUserList.model.GetUserListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val apiHelper: MainRepository,
    private val application: MyApplication
) : ViewModel() {

    val userInfo = MutableLiveData<Resource<List<GetUserListItem>>>()
    val filterUserList = MutableLiveData<ArrayList<GetUserListItem>>()
    val isOnLine = MutableLiveData<Boolean>()

    fun getUserList() = viewModelScope.launch {
        if (NetworkConnection().isOnline(application.applicationContext)) {
            isOnLine.value = true
            userInfo.postValue(Resource.loading(null))
            apiHelper.getUserList()
                .catch { e ->
                    userInfo.postValue(Resource.error(e.toString(), null))
                }
                .collect {
                    userInfo.postValue(Resource.success(it))
                }
        } else {
            isOnLine.value = false
            Toast.makeText(application.applicationContext, application.applicationContext.getString(R.string.check_internet_connection), Toast.LENGTH_SHORT).show()
        }
    }

    fun filter(text: String, userArrayList: List<GetUserListItem>) {
        val filteredlist: ArrayList<GetUserListItem> = ArrayList()
        for (item in userArrayList) {
            item.login?.lowercase(Locale.getDefault())?.let {
                if (it.contains(text.lowercase(Locale.getDefault()))) {
                    filteredlist.add(item)
                }
            }
        }
        filterUserList.value = filteredlist
    }
}