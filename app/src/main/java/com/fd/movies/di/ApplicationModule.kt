package com.fd.movies.di

import android.app.Application
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.fd.movies.BuildConfig
import com.fd.movies.data.remote.ApiService
import com.fd.movies.data.remote.AuthenticationInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    fun provideBaseUrl() = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttpClient(application: Application): OkHttpClient {
        val chuckerInterceptor = ChuckerInterceptor.Builder(application)
            .build()

        return OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor(BuildConfig.API_KEY))
            .addInterceptor(chuckerInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        baseUrl: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}
