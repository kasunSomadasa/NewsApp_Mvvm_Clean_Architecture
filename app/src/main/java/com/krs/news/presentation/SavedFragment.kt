package com.krs.news.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.krs.news.R
import com.krs.news.databinding.FragmentSavedBinding
import com.krs.news.presentation.adapter.SavedNewsAdapter
import com.krs.news.presentation.viewmodel.NewsViewModel

class SavedFragment : Fragment() {

    private lateinit var viewModel: NewsViewModel
    private lateinit var binding: FragmentSavedBinding
    private lateinit var savedNewsAdapter: SavedNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSavedBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        savedNewsAdapter = (activity as MainActivity).savedNewsAdapter
        savedNewsAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("selected_article", it)
                putBoolean("is_saved_article", true)
            }
            findNavController().navigate(R.id.action_savedFragment_to_infoFragment, bundle)
        }

        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            // delete saved news object when swipe list item
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = savedNewsAdapter.differ.currentList[position]
                viewModel.deleteNews(article)
                viewModel.deletedNewsLiveData.observe(viewLifecycleOwner, Observer {
                    if (it > 0) {
                        Snackbar.make(
                            view,
                            getString(R.string.delete_successFully),
                            Snackbar.LENGTH_LONG
                        ).apply {
                            setAction(getString(R.string.undo)) {
                                viewModel.saveNews(article)
                            }
                            show()
                        }
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

        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.rvSaveNewsList)
        }

        initRecycleView()
        viewNewsList()
    }

    /**
     * initialize recycler view
     */
    private fun initRecycleView() {
        binding.rvSaveNewsList.apply {
            adapter = savedNewsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    /**
     * get saved news list from db and submit it to adapter
     */
    private fun viewNewsList() {
        viewModel.getSavedNews().observe(viewLifecycleOwner, Observer { it ->
            savedNewsAdapter.differ.submitList(it)
        })
    }

}