package com.fd.movies.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fd.movies.Constants
import com.fd.movies.R
import com.fd.movies.data.models.Genre
import com.fd.movies.data.models.Movie
import com.fd.movies.databinding.ListItemMovieBinding

class MovieAdapter : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    private lateinit var genres: List<Genre>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ListItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bind(it) }
    }

    fun setGenres(genres: List<Genre>) {
        this.genres = genres
    }

    inner class MovieViewHolder(private val binding: ListItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.apply {
                // Load poster image using Glide
                val imageUrl = "${Constants.IMAGE_URL}${movie.posterPath}"
                println(imageUrl)
                Glide.with(itemView)
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder)
                    .into(moviePosterImageView)

                movieTitleTextView.text = movie.title
                movieOverviewTextView.text = movie.overview
                movieGenresTextView.text = getGenreText(movie)
            }
        }
    }

    private fun getGenreText(movie: Movie): String {
        return try {
            movie.genreIds.joinToString(", ") { genreId ->
                val genre = genres.find { it.id == genreId }
                genre?.name ?: ""
            }
        } catch (e : NullPointerException) {
            e.printStackTrace()
            "-"
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



