package com.alexiscrack3.spinny.players

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexiscrack3.spinny.R

class PlayersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
