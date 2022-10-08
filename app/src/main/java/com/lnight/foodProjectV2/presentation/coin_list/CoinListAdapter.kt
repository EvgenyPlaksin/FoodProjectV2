package com.lnight.foodProjectV2.presentation.coin_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.lnight.foodProjectV2.R
import com.lnight.foodProjectV2.data.remote.dto.Recipe

class CoinListAdapter(
    private val context: Context,
    private val data: List<Recipe>,
    private val onSearchClick: OnSearchClick
) : RecyclerView.Adapter<CoinListAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val recipeImage: ImageView = view.findViewById(R.id.recipe_image)
        val recipeTitle: TextView = view.findViewById(R.id.recipe_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentData = data[position]
        holder.recipeImage.load(currentData.image_url) {
            placeholder(R.drawable.ic_loading)
            RoundedCornersTransformation(10f)
            precision(coil.size.Precision.EXACT)
            scale(coil.size.Scale.FILL)
        }
        holder.recipeImage.scaleType = ImageView.ScaleType.FIT_XY

        holder.recipeTitle.text = currentData.title

        holder.itemView.setOnClickListener {
            onSearchClick.onItemClick(currentData)
        }
    }
}

interface OnSearchClick {
  fun onItemClick(recipe: Recipe)
}
