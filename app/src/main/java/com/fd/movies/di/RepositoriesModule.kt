package com.fd.movies.di

import com.fd.movies.data.remote.ApiService
import com.fd.movies.data.repositories.MovieRepository
import com.fd.movies.data.repositories.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun provideMovieRepository(service: ApiService): MovieRepository {
        return MovieRepositoryImpl(service)
    }
}