package com.krs.news.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.krs.news.R
import com.krs.news.databinding.ActivityMainBinding
import com.krs.news.presentation.adapter.NewsAdapter
import com.krs.news.presentation.adapter.NewsSearchAdapter
import com.krs.news.presentation.adapter.SavedNewsAdapter
import com.krs.news.presentation.viewmodel.NewsViewModel
import com.krs.news.presentation.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: NewsViewModelFactory

    @Inject
    lateinit var newsAdapter: NewsAdapter

    @Inject
    lateinit var savedNewsAdapter: SavedNewsAdapter

    @Inject
    lateinit var newsSearchAdapter: NewsSearchAdapter

    lateinit var viewModel: NewsViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        // specify main fragments to show toolbar back button
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.newsFragment,
                R.id.savedFragment,
                R.id.searchFragment
            ),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )

        // connect bottom navigation to nav graph
        NavigationUI.setupWithNavController(binding.bnvNews, navController, false)
        // connect toolbar with navigation
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        viewModel = ViewModelProvider(this, factory).get(NewsViewModel::class.java)
    }

}