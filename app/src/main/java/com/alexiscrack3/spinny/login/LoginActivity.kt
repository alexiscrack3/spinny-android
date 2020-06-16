package com.alexiscrack3.spinny.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexiscrack3.spinny.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun onStart() {
        super.onStart()
        supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        supportActionBar?.show()
    }
}
