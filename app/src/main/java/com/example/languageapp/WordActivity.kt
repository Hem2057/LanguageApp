package com.example.languageapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.languageapp.databinding.ActivityWordBinding
import androidx.core.widget.addTextChangedListener

class WordActivity : AppCompatActivity() {

    private lateinit var b: ActivityWordBinding
    private lateinit var adapter: WordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityWordBinding.inflate(layoutInflater)
        setContentView(b.root)

        // Top app bar title
        setSupportActionBar(b.toolbar)
        supportActionBar?.title = "Tiwi Language Learning Mobile App"

        // Sample data – replace with your repository later if you want
        val words = listOf(
            Word("Yirrikapayi", "Hello/Greeting"),
            Word("Pwanga", "Water"),
            Word("Wuta", "Sun"),
            Word("Kulama", "Traditional ceremony"),
            Word("Jingi", "Fish"),
            Word("Tupuni", "Fire"),
            Word("Mirta", "Moon"),
            Word("Ampirri", "Star"),
            Word("Kapitiya", "Tree"),
            Word("Pima", "House")
        )

        adapter = WordAdapter(words.toMutableList())

        b.recycler.apply {
            layoutManager = LinearLayoutManager(this@WordActivity)
            adapter = this@WordActivity.adapter
        }

        // Search filter
        b.tietSearch.addTextChangedListener { text ->
            adapter.filter(text?.toString().orEmpty())
        }

        // Logout action (adjust to your flow)
        b.btnLogout.setOnClickListener {
            finish() // or navigate wherever you want
        }
    }
}
