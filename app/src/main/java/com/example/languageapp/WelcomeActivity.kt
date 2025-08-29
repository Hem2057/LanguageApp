package com.example.languageapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val btnStart = findViewById<Button>(R.id.btnStart)

        btnStart.setOnClickListener {
            // Go to role selection screen
            startActivity(Intent(this, RoleSelectionActivity::class.java))
        }
    }
}
