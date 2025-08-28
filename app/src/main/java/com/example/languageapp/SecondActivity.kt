package com.example.languageapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val tvGreeting = findViewById<TextView>(R.id.tvGreeting)
        val tvLines    = findViewById<TextView>(R.id.tvLines)
        val btnLogout  = findViewById<Button>(R.id.btnLogout)

        val username = intent.getStringExtra("username") ?: "Guest"
        val role     = intent.getStringExtra("role") ?: "Student"
        val count    = intent.getIntExtra("lineCount", 5)

        tvGreeting.text = "Hello $username ($role)"
        tvLines.text = LineUtils.buildLinesAsBlock(count)

        // Requirement: Logout goes back to front page
        btnLogout.setOnClickListener {
            finish() // simply close this Activity to return to MainActivity
        }
    }
}
