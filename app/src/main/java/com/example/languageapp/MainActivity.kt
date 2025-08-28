package com.example.languageapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.languageapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val role = Role.fromId(binding.rgRole.checkedRadioButtonId)
            val numberOfLines = 5

            startActivity(
                Intent(this, SecondActivity::class.java).apply {
                    putExtra("username", username)
                    putExtra("role", role.label)
                    putExtra("numberOfLines", numberOfLines)
                }
            )
        }
    }
}
