package com.example.languageapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddWordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_word)

        supportActionBar?.title = "Add Word"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val etWord = findViewById<EditText>(R.id.etWord)
        val etMeaning = findViewById<EditText>(R.id.etMeaning)
        val btnSave = findViewById<Button>(R.id.btnSave)

        btnSave.setOnClickListener {
            val w = etWord.text.toString().trim()
            val m = etMeaning.text.toString().trim()
            if (w.isEmpty() || m.isEmpty()) {
                Toast.makeText(this, "Enter both word and meaning", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val list = WordStore.load(this)
            list.add(Word(w, m))
            WordStore.save(this, list)
            Toast.makeText(this, "Word saved", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) { finish(); true } else super.onOptionsItemSelected(item)
    }
}
