package com.fd.movies.ui.base

import androidx.lifecycle.ViewModel
import com.fd.movies.data.local.preference.SharedPrefManager
import com.fd.movies.ui.user.model.User
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(private val prefManager: SharedPrefManager) : ViewModel() {

    fun getUser() : User? {
        if (prefManager.getUser() == null) return null
        return Gson().fromJson(prefManager.getUser(), User::class.java)
    }

}