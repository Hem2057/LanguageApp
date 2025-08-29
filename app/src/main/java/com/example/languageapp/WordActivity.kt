package com.example.languageapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class WordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word)

        val rv = findViewById<RecyclerView>(R.id.rvWords)
        rv.layoutManager = LinearLayoutManager(this)

        // Sample data (later you’ll load from JSON, see Part 6)
        val words = WordRepository.load(this)

        rv.adapter = WordAdapter(words) { w ->
            val i = Intent(this, WordDetailActivity::class.java)
            i.putExtra("word", w.headword)
            i.putExtra("gloss", w.gloss)
            i.putExtra("example", w.example)
            startActivity(i)
        }
    }
}
