package com.alexiscrack3.spinny.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexiscrack3.spinny.login.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = LoginActivity.getIntent(this)
        startActivity(intent)
    }

    override fun onStart() {
        super.onStart()
        finish()
    }
}
