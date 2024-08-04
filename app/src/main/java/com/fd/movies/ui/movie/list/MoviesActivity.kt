package com.fd.movies.ui.movie.list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.fd.movies.databinding.ActivityMainBinding
import com.fd.movies.ui.common.CustomLoadStateAdapter
import com.fd.movies.utils.PermissionUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel : MoviesViewModel by viewModels()
    private val adapter: MoviesAdapter = MoviesAdapter()
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchView.setOnClickListener {
            binding.searchView.onActionViewExpanded()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { performSearch(it) }
                return true
            }

            override fun onQueryTextChange(searchText: String?): Boolean {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    searchText?.let {
                        delay(1000) // Debounce time
                        performSearch(searchText)
                    }
                }
                return true
            }
        })

        viewModel.genreUiState.observe(this) {
            binding.progressBar.isVisible = it.isLoading
            binding.searchView.isVisible = false
            binding.recyclerView.isVisible = false
            binding.tvError.isVisible = false
            if (it.isSuccess) {
                binding.searchView.isVisible = true
                binding.recyclerView.isVisible = true
                binding.searchView.setQuery("dragon", false)
                binding.searchView.clearFocus()
            } else {
                binding.tvError.isVisible = true
                binding.tvError.text = it.errorMessage
            }
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.adapter = adapter.withLoadStateFooter(
            footer = CustomLoadStateAdapter { adapter.retry() }
        )

        PermissionUtil.requestNotificationPermission(this)
    }

    private fun performSearch(query: String) {
        lifecycleScope.launch {
            viewModel.searchMovies(query).observe(this@MoviesActivity) { movies ->
                adapter.submitData(this@MoviesActivity.lifecycle, movies)
            }
        }
    }
}