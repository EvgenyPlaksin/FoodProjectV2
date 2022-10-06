package com.example.a45myapplication.presentation.web_view.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.a45myapplication.R
import com.example.a45myapplication.databinding.FragmentWebViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class WebViewFragment : Fragment() {

    private var _binding: FragmentWebViewBinding? = null
    private val binding get() = _binding

    private val args: WebViewFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNav)
        val selectedItem = bottomNav.selectedItemId
        if(selectedItem != 2131296264) {
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

        binding?.addToFavorites?.setOnClickListener {
           it.isActivated = !it.isActivated
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
        binding?.webView?.loadUrl(args.url)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}