package com.fd.movies.ui.base

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    private val viewModel: BaseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.getUser() == null) {
            openLoginPage()
        }
    }

    private fun openLoginPage() {
        /*val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()*/
    }

    // Sample implementation : https://bitbucket.org/firdaus91/dansmultipro/src/main/ (private repo)
}