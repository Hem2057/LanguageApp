package com.example.languageapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity

class RoleSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_role_selection)

        val roleGroup = findViewById<RadioGroup>(R.id.roleGroup)
        val btnContinue = findViewById<Button>(R.id.btnContinue)

        btnContinue.setOnClickListener {
            val selectedRole = when(roleGroup.checkedRadioButtonId) {
                R.id.rbStudent -> "Student"
                R.id.rbTeacher -> "Teacher"
                R.id.rbIT -> "IT Support"
                else -> "Guest"
            }
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("ROLE", selectedRole)
            startActivity(intent)
        }
    }
}
