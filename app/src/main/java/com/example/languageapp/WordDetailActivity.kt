package com.example.languageapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WordDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_detail)

        val word = intent.getStringExtra("word") ?: ""
        val gloss = intent.getStringExtra("gloss") ?: ""
        val example = intent.getStringExtra("example") ?: ""

        findViewById<TextView>(R.id.tvDetailWord).text = word
        findViewById<TextView>(R.id.tvDetailGloss).text = gloss
        findViewById<TextView>(R.id.tvDetailExample).text = example

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            // back to Welcome (as per assignment part 1)
            val i = Intent(this, WelcomeActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
            finish()
        }
    }
}
