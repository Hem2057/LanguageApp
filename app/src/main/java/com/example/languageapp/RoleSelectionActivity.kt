package com.example.languageapp

import android.content.Intent
import android.os.Bundle
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.example.languageapp.databinding.ActivityRoleSelectionBinding
import com.google.android.material.card.MaterialCardView

class RoleSelectionActivity : AppCompatActivity() {

    private lateinit var b: ActivityRoleSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityRoleSelectionBinding.inflate(layoutInflater)
        setContentView(b.root)

        // Collect cards & radios in the same order
        val allCards: List<MaterialCardView> = listOf(b.cardStudent, b.cardTeacher, b.cardIt)
        val allRadios: List<RadioButton> = listOf(b.rbStudent, b.rbTeacher, b.rbItSupport)

        // Make cards checkable in code (avoids XML attribute issues)
        allCards.forEach { it.isCheckable = true
        it.checkedIcon =  null // disable the tick mark
        }

        // Prevent recursive triggers when we update programmatically
        var suppress = false
        fun select(index: Int) {
            if (suppress) return
            suppress = true

            allRadios.forEachIndexed { i, r -> r.isChecked = (i == index) }
            allCards.forEachIndexed { i, c -> c.isChecked = (i == index) }

            suppress = false
        }

        // Card taps select matching option
        allCards.forEachIndexed { i, card ->
            card.setOnClickListener { select(i) }
        }

        // Radio changes unselect others
        allRadios.forEachIndexed { i, rb ->
            rb.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) select(i)
            }
        }

        // Continue
        b.btnContinue.setOnClickListener {
            val name = b.etName.text?.toString()?.trim().orEmpty()
            val role = when {
                b.rbItSupport.isChecked -> "IT Support"
                b.rbTeacher.isChecked   -> "Teacher"
                else                    -> "Student"
            }

            val intent = Intent(this, SecondActivity::class.java).apply {
                putExtra("username", name.ifBlank { "Guest" })
                putExtra("role", role)
                putExtra("lineCount", 5)
            }
            startActivity(intent)
        }
    }
}
