package com.lnight.foodProjectV2.presentation.search_history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.lnight.foodProjectV2.databinding.ItemResentBinding
import com.lnight.foodProjectV2.domain.model.Resent

class HistoryAdapter(
    private val data: List<Resent>,
    private val onHistoryClick: OnHistoryClick,
    private val onRemoveClick: OnRemoveResentClick
) : ListAdapter<Resent, HistoryAdapter.ViewHolder>(DiffCallBack()) {

    inner class ViewHolder(private val binding: ItemResentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentData: Resent) {
            binding.resentTitle.text = currentData.title

            binding.removeResent.setOnClickListener {
                onRemoveClick.removeItem(currentData)
            }
            binding.root.setOnClickListener {
                onHistoryClick.onItemClick(currentData)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemResentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentData = data[position]
        holder.bind(currentData)
    }
}

class DiffCallBack : DiffUtil.ItemCallback<Resent>() {
    override fun areItemsTheSame(oldItem: Resent, newItem: Resent): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Resent, newItem: Resent): Boolean {
        return oldItem.id == newItem.id
    }
}

interface OnRemoveResentClick {
    fun removeItem(resent: Resent)
}

interface OnHistoryClick {
    fun onItemClick(resent: Resent)
}