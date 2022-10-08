package com.lnight.foodProjectV2.presentation.coin_list.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lnight.foodProjectV2.common.Constants.popBack
import com.lnight.foodProjectV2.common.Resource
import com.lnight.foodProjectV2.common.collectLifecycleFlow
import com.lnight.foodProjectV2.data.remote.dto.Recipe
import com.lnight.foodProjectV2.databinding.FragmentMealBinding
import com.lnight.foodProjectV2.domain.model.Resent
import com.lnight.foodProjectV2.presentation.coin_list.CoinListAdapter
import com.lnight.foodProjectV2.presentation.coin_list.MealListViewModel
import com.lnight.foodProjectV2.presentation.coin_list.OnSearchClick
import dagger.hilt.android.AndroidEntryPoint
import java.lang.System.currentTimeMillis


@AndroidEntryPoint
class MealFragment : Fragment(), OnSearchClick {

    private var _binding: FragmentMealBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MealListViewModel by viewModels()

    private val args: MealFragmentArgs by navArgs()

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
        observeGetMeals()
        observeGetResents()

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

        if(args.resent.isNotBlank()) {
            binding.etSearchText.setText(args.resent)
            viewModel.getMeals(args.resent)
        }

    }

    private fun observeGetMeals(){
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

                    viewModel.getResents()
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

    private fun observeGetResents() {
        collectLifecycleFlow(viewModel.getResentsResult) {
            if(it is Resource.Success) {

                if(it.data?.isNotEmpty() == true) {
                    val resent = Resent(
                        title = binding.etSearchText.text.toString(),
                        timeStamp = currentTimeMillis()
                    )
                    Log.e("TAG", "it.data.first.title -> ${it.data.first().title}, last -> ${it.data.last().title}, resent -> ${resent.title}")
                    if(it.data.last().title != resent.title) {
                        viewModel.addResent(resent)
                    }
                } else {
                    val resent = Resent(
                        title = binding.etSearchText.text.toString(),
                        timeStamp = currentTimeMillis()
                    )
                    viewModel.addResent(resent)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(recipe: Recipe) {
        popBack = false
        val action = MealFragmentDirections.actionMealFragmentToWebViewFragment(recipe)
        findNavController().navigate(action)
    }

}