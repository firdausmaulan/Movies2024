package com.fd.movies.ui.movie.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fd.movies.utils.Constants
import com.fd.movies.R
import com.fd.movies.data.remote.responses.GenreResponse
import com.fd.movies.ui.movie.model.Movie
import com.fd.movies.databinding.ListItemMovieBinding

class MoviesAdapter : PagingDataAdapter<Movie, MoviesAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ListItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(it) }
    }

    inner class MovieViewHolder(private val binding: ListItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.apply {
                // Load poster image using Glide
                Glide.with(itemView)
                    .load(movie.posterUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(moviePosterImageView)
                movieTitleTextView.text = movie.title
                movieOverviewTextView.text = movie.overview
                movieGenresTextView.text = movie.genres
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}



