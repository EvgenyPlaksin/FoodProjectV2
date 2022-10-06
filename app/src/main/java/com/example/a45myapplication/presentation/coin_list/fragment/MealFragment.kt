package com.example.a45myapplication.presentation.coin_list.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.a45myapplication.common.Resource
import com.example.a45myapplication.common.collectLifecycleFlow
import com.example.a45myapplication.databinding.FragmentMealBinding
import com.example.a45myapplication.presentation.coin_list.CoinListAdapter
import com.example.a45myapplication.presentation.coin_list.MealListViewModel
import com.example.a45myapplication.presentation.coin_list.OnSearchClick
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MealFragment : Fragment(), OnSearchClick {

    private var _binding: FragmentMealBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MealListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMealBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerGetMeals()

        binding.etSearchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val text = binding.etSearchText.text.toString()
                if (text.isNotBlank()) {
                    viewModel.getMeals(text)
                }
            }

        })
    }

    private fun observerGetMeals(){
        collectLifecycleFlow(viewModel.getMealsResult) {
            when(it) {
                is Resource.Success ->  {
                    binding.rvRecipes.visibility = View.VISIBLE
                    binding.loading.root.isVisible = false
                    binding.errorText.isVisible = false
                    val adapter = CoinListAdapter(
                       requireContext(),
                       it.data?.recipes ?: emptyList(),
                       this
                   )
                    binding.rvRecipes.adapter = adapter
                    binding.rvRecipes.setHasFixedSize(true)
                }

                is Resource.Error ->  {
                    binding.rvRecipes.visibility = View.INVISIBLE
                    binding.loading.root.isVisible = false
                    binding.errorText.isVisible = true
                    binding.errorText.text = it.message ?: "Unknown error"
                }
                is Resource.Loading ->  {
                    binding.rvRecipes.visibility = View.INVISIBLE
                    binding.errorText.isVisible = false
                    binding.loading.root.isVisible = true
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(recipeUrl: String) {
        val action = MealFragmentDirections.actionMealFragmentToWebViewFragment(recipeUrl)
        findNavController().navigate(action)
    }

}