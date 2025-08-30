package com.example.languageapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)


        val username = intent.getStringExtra("username") ?: "Guest"
        val role = intent.getStringExtra("role") ?: "Student"
//        val count = intent.getIntExtra("lineCount", 5)

        findViewById<TextView>(R.id.tvUser).text =
            getString(R.string.hello_user, username)
        findViewById<TextView>(R.id.tvRole).text =
            getString(R.string.your_role, role)
//        findViewById<TextView>(R.id.tvLines).text =
//            LineUtils.buildLinesAsBlock(count)
    // get word from repository

        // Go to Words button
        findViewById<Button>(R.id.btnGoToWords).setOnClickListener {
            val words = WordRepository.getWords()
            val json = Gson().toJson(words)
            val intent = Intent(this, WordActivity::class.java)

            intent.putExtra("words_json", json)
            startActivity(intent)
        }

        // Logout button
        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            val toFront = Intent(this, WelcomeActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(toFront)
            finish()
        }
    }
}
