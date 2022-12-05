package com.krs.news.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.krs.news.R
import com.krs.news.databinding.FragmentNewsBinding
import com.krs.news.presentation.adapter.NewsAdapter
import com.krs.news.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private var country = "us"
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).newsAdapter
        newsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
                putBoolean("is_saved_article", false)
            }
            findNavController().navigate(R.id.action_newsFragment_to_infoFragment, bundle)
        }
        initRecycleView()
        viewNewsList()
    }

    /**
     * get news list from api and submit it to PagingDataAdapter
     */
    private fun viewNewsList() {
        viewModel.getNews(country, page)
        viewModel.newsLiveData.observe(viewLifecycleOwner, Observer { response ->
            viewLifecycleOwner.lifecycleScope.launch {
                newsAdapter.submitData(response)
            }
        })
    }

    /**
     * initialize recycler view and adapter
     */
    private fun initRecycleView() {
        binding.rvNewsList.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        newsAdapter.addLoadStateListener { loadState ->
            when (loadState.source.refresh) {
                is LoadState.NotLoading -> {
                    hideProgressBar()
                    if (loadState.append.endOfPaginationReached && newsAdapter.itemCount < 1) {
                        binding.tvError.text = getString(R.string.empty_list)
                        binding.tvError.visibility = View.VISIBLE
                    } else {
                        binding.tvError.visibility = View.GONE
                    }
                }
                is LoadState.Loading -> {
                    if (newsAdapter.itemCount < 1)
                        showProgressBar()
                }
            }
            handleError(loadState)
        }
    }

    /**
     * handle errors return from PagingDataAdapter
     */
    private fun handleError(loadState: CombinedLoadStates) {
        hideProgressBar()
        val errorState = when {
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            else -> null
        }
        errorState?.let {
            binding.tvError.text = it.error.localizedMessage
            binding.tvError.visibility = View.VISIBLE
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvNewsList.visibility = View.GONE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
        binding.rvNewsList.visibility = View.VISIBLE
    }

}