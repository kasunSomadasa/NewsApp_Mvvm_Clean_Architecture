package com.krs.news.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.krs.news.R
import com.krs.news.databinding.FragmentInfoBinding
import com.krs.news.presentation.viewmodel.NewsViewModel

class InfoFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentInfoBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        // get selected news article object from nav args
        val args: InfoFragmentArgs by navArgs()
        val article = args.selectedArticle
        val isSavedArticle = args.isSavedArticle

        if (isSavedArticle)
            binding.fabSave.visibility = View.GONE

        binding.webView.apply {
            webViewClient = CustomWebViewClient()
            if (article != null && article.url != null) {
                if (article.url.length > 0) {
                    loadUrl(article.url)
                }
            }
        }

        // save news article object in local db
        binding.fabSave.setOnClickListener {
            if (article != null) {
                viewModel.saveNews(article)
                viewModel.savedNewsLiveData.observe(viewLifecycleOwner, Observer {
                    if (it > 0) {
                        Snackbar.make(
                            view,
                            getString(R.string.save_successFully),
                            Snackbar.LENGTH_LONG
                        ).show()
                    } else {
                        Snackbar.make(
                            view,
                            getString(R.string.something_went_wrong),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                })

            }
        }
    }

    inner class CustomWebViewClient : WebViewClient() {

        // load the URL
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return false
        }

        // progressBar will disappear once page is loaded
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            binding.webView.visibility = View.VISIBLE
            binding.progressBar.visibility = View.GONE
        }
    }

}