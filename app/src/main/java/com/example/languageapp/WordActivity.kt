package com.example.languageapp
import android.util.Log
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.languageapp.databinding.ActivityWordBinding
import androidx.core.widget.addTextChangedListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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


//        val json = intent.getStringExtra("words_json")
//        val type = object : TypeToken<List<Word>>() {}.type
//        val words: List<Word> = if (json != null) {
//            Gson().fromJson(json, type)
//        } else{
//            emptyList()
//
//        }
        val words = WordRepository.getWords()

        //RecylerView

        adapter = WordAdapter(words.toMutableList())
        b.recycler.layoutManager = LinearLayoutManager(this)
        b.recycler.adapter = adapter

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
