package com.lnight.foodProjectV2.presentation.web_view.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.lnight.foodProjectV2.common.Constants.popBack
import com.lnight.foodProjectV2.common.Resource
import com.lnight.foodProjectV2.common.collectLifecycleFlow
import com.lnight.foodProjectV2.databinding.FragmentWebViewBinding
import com.lnight.foodProjectV2.presentation.web_view.WebViewViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebViewFragment : Fragment() {

    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding

    private val viewModel: WebViewViewModel by viewModels()

    private val args: WebViewFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(popBack) {
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWebViewBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popBack = true
        observeGetFavorites()
        viewModel.getFavorites()

        binding?.addToFavorites?.setOnClickListener {
           it.isActivated = !it.isActivated
            try {
                if(args.recipe != null) {
                    if (it.isActivated) {
                        viewModel.insertFavorite(args.recipe!!)
                    } else {
                        viewModel.removeFromFavorites(args.recipe!!)
                    }
                }
            } catch (e: NullPointerException) {
                Toast.makeText(requireContext(), "Unknown error occurred, please try again", Toast.LENGTH_SHORT).show()
            }
        }

        val pmmCookies: CookieManager = CookieManager.getInstance()
        CookieManager.setAcceptFileSchemeCookies(true)
        pmmCookies.setAcceptThirdPartyCookies(binding?.webView, true)
        binding?.webView?.settings?.apply {
            useWideViewPort = true
            javaScriptEnabled = true
            mixedContentMode = 0
            loadWithOverviewMode = true
            allowFileAccess = true
            domStorageEnabled = true
            defaultTextEncodingName = "utf-8"
            databaseEnabled = true
            allowFileAccessFromFileURLs = true
            javaScriptCanOpenWindowsAutomatically = true
        }

        binding?.webView?.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String) {
                binding?.loadingLayout?.root?.visibility = View.INVISIBLE
            }

        }
        args.recipe?.source_url?.let { binding?.webView?.loadUrl(it) }
    }

    private fun observeGetFavorites() {
        collectLifecycleFlow(viewModel.getFavoritesResult) {
            if(it is Resource.Success) {
                var contains = false
                it.data?.forEach { recipe ->
                    if(recipe.recipe_id == args.recipe?.recipe_id) {
                        contains = true
                    }
                }
                if(contains) {
                    binding?.addToFavorites?.isActivated = true
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}