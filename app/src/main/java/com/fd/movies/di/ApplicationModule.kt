package com.fd.movies.di

import android.app.Application
import android.content.SharedPreferences
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.fd.movies.BuildConfig
import com.fd.movies.data.local.preference.SharedPrefManager
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
object ApplicationModule {

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

    @Provides
    @Singleton
    fun provideSharedPreferences(
        sharedPrefModule: SharedPrefModule
    ): SharedPreferences = sharedPrefModule.getSharedPref()

    @Provides
    @Singleton
    fun provideSharedPreferencesManager(sharedPreferences: SharedPreferences): SharedPrefManager {
        return SharedPrefManager(sharedPreferences)
    }
}
