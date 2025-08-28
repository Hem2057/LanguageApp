package com.example.languageapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WordDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_detail)

        val word = intent.getSerializableExtra("WORD") as Word
        findViewById<TextView>(R.id.tvWordDetail).text = word.tiwi
        findViewById<TextView>(R.id.tvMeaningDetail).text = word.english
        findViewById<TextView>(R.id.tvExampleDetail).text = word.example
    }
}
