package com.lnight.foodProjectV2.presentation.favourites

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.lnight.foodProjectV2.R
import com.lnight.foodProjectV2.data.remote.dto.Recipe
import com.lnight.foodProjectV2.databinding.ItemFavoritesBinding

class FavoritesAdapter(
    private val data: List<Recipe>,
    private val onFavoriteClick: OnFavoriteClick,
    private val onRemoveClick: OnRemoveClick
) : ListAdapter<Recipe, FavoritesAdapter.ViewHolder>(DiffCallBack()) {

    inner class ViewHolder(private val binding: ItemFavoritesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentData: Recipe) {
            binding.favRecipeImage.load(currentData.image_url) {
                placeholder(R.drawable.ic_loading)
                RoundedCornersTransformation(10f)
                precision(coil.size.Precision.EXACT)
                scale(coil.size.Scale.FILL)
            }
            binding.favRecipeImage.scaleType = ImageView.ScaleType.FIT_XY

            binding.favRecipeTitle.text = currentData.title

            binding.rootLayoutItemFav.setOnClickListener {
                onFavoriteClick.onItemClick(currentData)
            }
            binding.remove.setOnClickListener {
                onRemoveClick.removeItem(currentData)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoritesBinding.inflate(
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

class DiffCallBack : DiffUtil.ItemCallback<Recipe>() {
    override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean {
        return oldItem.recipe_id == newItem.recipe_id
    }
}

interface OnRemoveClick {
    fun removeItem(recipe: Recipe)
}

interface OnFavoriteClick {
    fun onItemClick(recipe: Recipe)
}