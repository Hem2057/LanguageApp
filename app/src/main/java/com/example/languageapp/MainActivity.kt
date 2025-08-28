package com.example.languageapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etName = findViewById<EditText>(R.id.etName)
        val rbStudent = findViewById<RadioButton>(R.id.rbStudent)
        val rbTeacher = findViewById<RadioButton>(R.id.rbTeacher)
        val rbItSupport = findViewById<RadioButton>(R.id.rbItSupport)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val name = etName.text?.toString()?.trim().orEmpty()
            val role = when {
                rbItSupport.isChecked -> "IT Support"
                rbTeacher.isChecked   -> "Teacher"
                else                  -> "Student"
            }

            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra("username", name.ifBlank { "Guest" })
                putExtra("role", role)
                putExtra("lineCount", 5) // requirement: 2nd screen shows N lines (here, 5)
            }
            startActivity(intent)
        }
    }
}
