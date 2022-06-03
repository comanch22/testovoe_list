package com.comanch.testovie_list.listFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.comanch.testovie_list.dataBase.StarTrackData
import com.comanch.testovie_list.databinding.ListItemBinding

class ItemListener(val clickListener: (itemId: Long) -> Unit) {
     fun onClick(item: StarTrackData) = clickListener(item.starTrackId)
}

class ListItemAdapter(private val clickListener: ItemListener) : ListAdapter<DataItem, RecyclerView.ViewHolder>(
    SleepNightDiffCallback()
) {

    private var mRecyclerView: RecyclerView? = null

    fun setData(list: List<StarTrackData>?) {

        val items = list?.map { DataItem.StarTrackItem(it) }
        submitList(items)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {

        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is ViewHolder -> {
                val item = getItem(position) as DataItem.StarTrackItem
                holder.bind(item.starTrackData, clickListener)
            }
        }
    }

    class ViewHolder private constructor(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater =
                    LayoutInflater.from(parent.context)
                val binding =
                    ListItemBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

        fun bind(item: StarTrackData,
                 clickListener: ItemListener) {

            binding.item = item
            binding.title.text = item.imageTitle
            binding.clickListener = clickListener
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}

class SleepNightDiffCallback : DiffUtil.ItemCallback<DataItem>() {

    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

sealed class DataItem {

    abstract val id: Long

    data class StarTrackItem(val starTrackData: StarTrackData) : DataItem() {
        override val id = starTrackData.starTrackId
    }
}


