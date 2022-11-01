package com.app.gitHubUserList.adapter

import coil.load
import com.app.gitHubUserList.R
import com.app.gitHubUserList.databinding.ItemUserListBinding
import com.app.gitHubUserList.model.GetUserListItem

class GitHubUserListAdapter : BaseRecyclerViewAdapter<GetUserListItem, ItemUserListBinding>() {

    override fun getLayout() = R.layout.item_user_list

    // creating a variable for array list and context.
    private var courseModelArrayList: ArrayList<GetUserListItem>? = null

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ItemUserListBinding>,
        position: Int
    ) {
        holder.binding.getUserListItem = items[position]
        holder.binding.root.setOnClickListener {
            listener?.invoke(items[position])
        }
    }

    fun filterList(filterlist: ArrayList<GetUserListItem>) {
        addItems(filterlist)
    }
}