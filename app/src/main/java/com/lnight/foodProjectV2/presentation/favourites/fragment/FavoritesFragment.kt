package com.lnight.foodProjectV2.presentation.favourites.fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.lnight.foodProjectV2.R
import com.lnight.foodProjectV2.common.Constants.popBack
import com.lnight.foodProjectV2.common.Constants.saveList
import com.lnight.foodProjectV2.common.Resource
import com.lnight.foodProjectV2.common.collectLifecycleFlow
import com.lnight.foodProjectV2.data.remote.dto.Recipe
import com.lnight.foodProjectV2.databinding.FragmentFavoritesBinding
import com.lnight.foodProjectV2.presentation.favourites.FavoritesAdapter
import com.lnight.foodProjectV2.presentation.favourites.FavoritesViewModel
import com.lnight.foodProjectV2.presentation.favourites.OnFavoriteClick
import com.lnight.foodProjectV2.presentation.favourites.OnRemoveClick
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(), OnRemoveClick, OnFavoriteClick {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        observeGetFavorites()
        viewModel.getFavorites()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.loadingLayout.rootLoading.setBackgroundColor(resources.getColor(R.color.gray_dark, requireActivity().theme))
        } else {
            binding.loadingLayout.rootLoading.setBackgroundColor(resources.getColor(R.color.gray_dark))
        }

        binding.tryAgainBtn.setOnClickListener {
            viewModel.getFavorites()
        }
        if(saveList.isNotEmpty()) {
            binding.rvRecipes.adapter = FavoritesAdapter(saveList, this, this)
            binding.rvRecipes.setHasFixedSize(true)
            viewModel.getFavorites()
        } else {
            binding.rvRecipes.isVisible = false
            binding.loadingLayout.root.isVisible = false
            binding.retrySection.isVisible = true
            binding.errorText.text = "Nothing here"
        }
    }

    private fun observeGetFavorites() {
        collectLifecycleFlow(viewModel.getFavoritesResult) {
            when(it) {
                is Resource.Success -> {
                    if(!it.data.isNullOrEmpty()) {
                        binding.rvRecipes.isVisible = true
                        binding.loadingLayout.root.isVisible = false
                        binding.retrySection.isVisible = false

                        recipesList = it.data as MutableList<Recipe>
                        val adapter = FavoritesAdapter(recipesList, this, this)
                        binding.rvRecipes.adapter = adapter
                        adapter.notifyDataSetChanged()
                        binding.rvRecipes.setHasFixedSize(true)
                    } else {
                        binding.rvRecipes.isVisible = false
                        binding.loadingLayout.root.isVisible = false
                        binding.retrySection.isVisible = true
                        binding.errorText.text = "Nothing here"
                    }
                }
                is Resource.Error -> {
                    binding.rvRecipes.isVisible = false
                    binding.loadingLayout.root.isVisible = false
                    binding.retrySection.isVisible = true
                    binding.errorText.text = it.message ?: "Unknown error"
                }
                is Resource.Loading -> {
                    binding.rvRecipes.isVisible = false
                    binding.loadingLayout.root.isVisible = true
                    binding.retrySection.isVisible = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        saveList = recipesList
        _binding = null
    }

    override fun removeItem(recipe: Recipe) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Remove this item?")
            .setMessage("Are you sure to remove this item from favorites? You'll can't undo this")
            .setCancelable(false)
            .setNegativeButton("No") { _, _ ->

            }
            .setPositiveButton("Yes") { _, _ ->
                recipesList.remove(recipe)
                binding.rvRecipes.adapter?.notifyDataSetChanged()
                viewModel.removeItem(recipe)

                if(recipesList.isEmpty()) {
                    binding.rvRecipes.isVisible = false
                    binding.loadingLayout.root.isVisible = false
                    binding.retrySection.isVisible = true
                    binding.errorText.text = "Nothing here"
                }
            }
            .show()
    }

    override fun onItemClick(recipe: Recipe) {
        popBack = false
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToWebViewFragment(recipe)
        findNavController().navigate(action)
    }

    companion object {
        var recipesList = mutableListOf<Recipe>()
    }

}