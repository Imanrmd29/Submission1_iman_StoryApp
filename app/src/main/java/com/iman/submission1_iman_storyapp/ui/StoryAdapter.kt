package com.iman.submission1_iman_storyapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iman.submission1_iman_storyapp.databinding.ItemStoryUserBinding
import com.iman.submission1_iman_storyapp.Model.ListStoryItem

class StoryAdapter: RecyclerView.Adapter<StoryAdapter.MyViewHolder>() {

    private var oldStoryItem = emptyList<ListStoryItem>()

    class MyViewHolder(private val binding: ItemStoryUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(listStoryItem: ListStoryItem){
            Glide.with(itemView)
                .load(listStoryItem.photoUrl)
                .into(binding.imgProfile)

            binding.profileName.text = listStoryItem.name
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoryAdapter.MyViewHolder =
        MyViewHolder(ItemStoryUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: StoryAdapter.MyViewHolder, position: Int) {
        holder.bind(oldStoryItem[position])
    }

    override fun getItemCount(): Int = oldStoryItem.size

    fun setData(newStoryItem: List<ListStoryItem>) {
        val diffUtil = StoryDiffUtil(oldStoryItem, newStoryItem)
        val diffResult = DiffUtil.calculateDiff(diffUtil)
        oldStoryItem = newStoryItem
        diffResult.dispatchUpdatesTo(this)
    }
}