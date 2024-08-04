package com.fd.movies.data.local.preference

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPrefManager @Inject constructor(private val pref: SharedPreferences) {

    companion object {
        private const val USER = "user"
    }

    fun saveUser(value: String) {
        pref.edit().putString(USER, value).apply()
    }

    fun getUser(): String? {
        return pref.getString(USER, null)
    }

    fun clear() {
        pref.edit().clear().apply()
    }
}