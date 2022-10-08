package com.lnight.foodProjectV2.presentation.search_history.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.lnight.foodProjectV2.R
import com.lnight.foodProjectV2.common.Constants.saveListResents
import com.lnight.foodProjectV2.common.Resource
import com.lnight.foodProjectV2.common.collectLifecycleFlow
import com.lnight.foodProjectV2.databinding.FragmentHistoryBinding
import com.lnight.foodProjectV2.domain.model.Resent
import com.lnight.foodProjectV2.presentation.search_history.HistoryAdapter
import com.lnight.foodProjectV2.presentation.search_history.HistoryViewModel
import com.lnight.foodProjectV2.presentation.search_history.OnHistoryClick
import com.lnight.foodProjectV2.presentation.search_history.OnRemoveResentClick
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HistoryFragment : Fragment(), OnHistoryClick, OnRemoveResentClick {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        observeGetResents()
        viewModel.getResents()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.loadingLayout.rootLoading.setBackgroundColor(resources.getColor(R.color.gray_dark, requireActivity().theme))
        } else {
            binding.loadingLayout.rootLoading.setBackgroundColor(resources.getColor(R.color.gray_dark))
        }

        binding.tryAgainBtn.setOnClickListener {
            viewModel.getResents()
        }

        if(saveListResents.isNotEmpty()) {
            val adapter = HistoryAdapter(saveListResents, this, this)
            binding.rvResents.adapter = adapter
            binding.rvResents.setHasFixedSize(true)
            viewModel.getResents()
        } else {
            binding.rvResents.isVisible = false
            binding.loadingLayout.root.isVisible = false
            binding.retrySection.isVisible = true
            binding.errorText.text = "Nothing here"
        }
    }

    private fun observeGetResents() {
        collectLifecycleFlow(viewModel.getResentsResult) {
            when(it) {
                is Resource.Success -> {
                    if(!it.data.isNullOrEmpty()) {
                        binding.rvResents.isVisible = true
                        binding.loadingLayout.root.isVisible = false
                        binding.retrySection.isVisible = false

                        resentData = it.data as MutableList<Resent>
                        resentData.reverse()
                        val adapter = HistoryAdapter(resentData, this, this)
                        binding.rvResents.adapter = adapter
                        binding.rvResents.setHasFixedSize(true)
                        adapter.notifyDataSetChanged()
                    } else {
                        binding.rvResents.isVisible = false
                        binding.loadingLayout.root.isVisible = false
                        binding.retrySection.isVisible = true
                        binding.errorText.text = "Nothing here"
                    }
                }
                is Resource.Error -> {
                    binding.rvResents.isVisible = false
                    binding.loadingLayout.root.isVisible = false
                    binding.retrySection.isVisible = true
                    binding.errorText.text = it.message ?: "Unknown error"
                }
                is Resource.Loading -> {
                    binding.rvResents.isVisible = false
                    binding.loadingLayout.root.isVisible = true
                    binding.retrySection.isVisible = false
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        saveListResents = resentData
        _binding = null
    }

    override fun removeItem(resent: Resent) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Remove this item?")
            .setMessage("Are you sure to remove this item from resents? You'll can't undo this")
            .setCancelable(false)
            .setNegativeButton("No") { _, _ ->

            }
            .setPositiveButton("Yes") { _, _ ->
                resentData.remove(resent)
                binding.rvResents.adapter?.notifyDataSetChanged()
                viewModel.removeResent(resent)

                if(resentData.isEmpty()) {
                    binding.rvResents.isVisible = false
                    binding.loadingLayout.root.isVisible = false
                    binding.retrySection.isVisible = true
                    binding.errorText.text = "Nothing here"
                }
            }
            .show()
    }

    override fun onItemClick(resent: Resent) {
       val action = HistoryFragmentDirections.actionHistoryFragmentToMealFragment(resent.title)
        findNavController().navigate(action)
    }

    companion object {
        var resentData = mutableListOf<Resent>()
    }

}