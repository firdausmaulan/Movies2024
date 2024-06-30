package com.fd.movies.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.fd.movies.presentation.adapter.MovieAdapter
import com.fd.movies.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel : MovieViewModel by viewModels()
    private val adapter: MovieAdapter = MovieAdapter()
    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = adapter

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { performSearch(it) }
                return true
            }

            override fun onQueryTextChange(searchText: String?): Boolean {
                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    searchText?.let {
                        delay(500) // Debounce time
                        performSearch(searchText)
                    }
                }
                return true
            }
        })

        viewModel.genres.observe(this) {
            adapter.setGenres(it)
            performSearch("dragonball")
        }
    }

    private fun performSearch(query: String) {
        lifecycleScope.launch {
            viewModel.searchMovies(query).observe(this@MoviesActivity) { movies ->
                adapter.submitData(this@MoviesActivity.lifecycle, movies)
            }
        }
    }
}