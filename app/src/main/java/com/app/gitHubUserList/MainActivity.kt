package com.app.gitHubUserList

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.gitHubUserList.adapter.GitHubUserListAdapter
import com.app.gitHubUserList.data.api.StatusCalled
import com.app.gitHubUserList.databinding.ActivityMainBinding
import com.app.gitHubUserList.model.GetUserListItem
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mAdapter: GitHubUserListAdapter
    private lateinit var mBinding: ActivityMainBinding

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setAdapter(mBinding)

        viewModel.getUserList()

        viewModel.userInfo.observe(this) {
            when (it.status) {
                StatusCalled.SUCCESS -> {
                    //mBinding.progressCircular.visibility = View.GONE
                    it.data?.let { getUserList -> renderList(getUserList) }
                    mBinding.dayListId.visibility = View.VISIBLE
                }
                StatusCalled.LOADING -> {
                    //mBinding.progressCircular.visibility = View.VISIBLE
                    mBinding.dayListId.visibility = View.GONE
                }
                StatusCalled.ERROR -> {
                    //Handle Error
                    //mBinding.progressCircular.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun renderList(userList: List<GetUserListItem>?) {
        userList?.let {
            mAdapter.addItems(it)
        }
    }

    private fun setAdapter(mBinding: ActivityMainBinding) {
        mAdapter = GitHubUserListAdapter()
        mBinding.dayListId.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        mBinding.dayListId.layoutManager = layoutManager
        mBinding.dayListId.adapter = mAdapter
        mAdapter.addItems(ArrayList())
    }

}