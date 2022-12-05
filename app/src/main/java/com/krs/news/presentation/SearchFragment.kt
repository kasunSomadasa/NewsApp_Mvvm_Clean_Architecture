package com.krs.news.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.krs.news.R
import com.krs.news.databinding.FragmentSearchBinding
import com.krs.news.presentation.adapter.NewsSearchAdapter
import com.krs.news.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentSearchBinding
    private lateinit var newsSearchAdapter: NewsSearchAdapter
    private var country = "us"
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsSearchAdapter = (activity as MainActivity).newsSearchAdapter
        newsSearchAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
            }
            findNavController().navigate(R.id.action_newsFragment_to_infoFragment, bundle)
        }
        initRecycleView()
        initSearchView()
    }

    /**
     * initialize recycler view and adapter
     */
    private fun initRecycleView() {
        binding.rvNewsList.apply {
            adapter = newsSearchAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        newsSearchAdapter.addLoadStateListener { loadState ->
            when (loadState.source.refresh) {
                is LoadState.NotLoading -> {
                    hideProgressBar()
                    if (newsSearchAdapter.itemCount < 1) {
                        binding.tvError.text = getString(R.string.empty_list)
                        binding.tvError.visibility = View.VISIBLE
                    } else {
                        binding.tvError.visibility = View.GONE
                    }
                }
                is LoadState.Loading -> {
                    if (newsSearchAdapter.itemCount < 1)
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


    /**
     * search edit text listener
     */
    private fun initSearchView() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                MainScope().launch {
                    if (s.toString().length < 4) {
                        hideProgressBar()
                        newsSearchAdapter.submitData(lifecycle, PagingData.empty())
                        binding.tvError.visibility = View.VISIBLE
                    } else {
                        binding.tvError.visibility = View.GONE
                        viewModel.getSearchedNews(country, s.toString(), page)
                        viewSearchNewsList()
                    }
                }

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }


    /**
     * get searched news list from api and submit it to PagingDataAdapter
     */
    private fun viewSearchNewsList() {
        viewModel.searchNewsLiveData.observe(viewLifecycleOwner, Observer { response ->
            viewLifecycleOwner.lifecycleScope.launch {
                newsSearchAdapter.submitData(response)
            }
        })
    }

}