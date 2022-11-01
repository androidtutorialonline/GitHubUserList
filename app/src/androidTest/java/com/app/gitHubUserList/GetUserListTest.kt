package com.app.gitHubUserList

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.app.gitHubUserList.model.GetUserListItem
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class GetUserListTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    private lateinit var viewModel: MainActivityViewModel


    @Test
    fun testGetUserList() {
        viewModel.getUserList()
        //val result = viewModel.userInfo.getOrAwaitValue()

    }

    @Test
    fun testFilterTheUserList() {
        val list = listOf(
            GetUserListItem(
                "", "", "", "", "",
                "", "", 0, "mojombo", ""
            ),
            GetUserListItem(), GetUserListItem()
        )

        viewModel.filter("mojom", list)
        val result = viewModel.filterUserList.getOrAwaitValue().find {
            it.login == "mojom"
        }
        Truth.assertThat(result != null).isTrue()
    }
}