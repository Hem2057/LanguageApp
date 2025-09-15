package com.example.languageapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // If we already have data saved, skip the welcome
        val existing = WordStore.load(this)
        if (existing.isNotEmpty()) {
            startActivity(Intent(this, WordActivity::class.java))
            finish()
            return
        }

        setContentView(R.layout.activity_welcome)

        findViewById<Button>(R.id.btnStart).setOnClickListener {
            // First run: seed with repository words, then go to list
            val seed = WordRepository.getWords().toMutableList()
            WordStore.save(this, seed)
            startActivity(Intent(this, WordActivity::class.java))
            finish()
        }
    }
}
