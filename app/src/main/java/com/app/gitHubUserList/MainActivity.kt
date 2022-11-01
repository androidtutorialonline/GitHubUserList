package com.app.gitHubUserList

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.gitHubUserList.adapter.GitHubUserListAdapter
import com.app.gitHubUserList.data.api.StatusCalled
import com.app.gitHubUserList.databinding.ActivityMainBinding
import com.app.gitHubUserList.model.GetUserListItem
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var userArrayList: List<GetUserListItem> = ArrayList()
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
                    mBinding.progress.visibility = View.GONE
                    it.data?.let { getUserList -> renderList(getUserList) }
                    mBinding.dayListId.visibility = View.VISIBLE
                }
                StatusCalled.LOADING -> {
                    mBinding.progress.visibility = View.VISIBLE
                    mBinding.dayListId.visibility = View.GONE
                }
                StatusCalled.ERROR -> {
                    //Handle Error
                    mBinding.progress.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }

        viewModel.filterUserList.observe(this) {
            if (it.isEmpty()) {
                Toast.makeText(this, getString(R.string.no_data_found), Toast.LENGTH_LONG).show()
            } else {
                mAdapter.filterList(it)
            }
        }
    }

    private fun renderList(userList: List<GetUserListItem>?) {
        userList?.let {
            mAdapter.addItems(it)
            userArrayList = it
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem: MenuItem = menu.findItem(R.id.actionSearch)
        val searchView: SearchView = searchItem.getActionView() as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                viewModel.filter(msg, userArrayList)
                return false
            }
        })
        return true
    }
}