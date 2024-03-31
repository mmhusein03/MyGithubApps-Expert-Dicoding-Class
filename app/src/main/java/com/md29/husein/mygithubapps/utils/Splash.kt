package com.md29.husein.mygithubapps.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.md29.husein.mygithubapps.R
import com.md29.husein.mygithubapps.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setUpView()

        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
            delay(1000)
            startActivity(Intent(this@Splash, MainActivity::class.java))
            finish()
        }
    }

    @Suppress("DEPRECATION")
    private fun setUpView() {
        this.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }
}