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

        // If already onboarded, skip straight to words
        if (UserStore.isOnboarded(this)) {
            startActivity(Intent(this, WordActivity::class.java))
            finish()
            return
        }

        b = ActivityRoleSelectionBinding.inflate(layoutInflater)
        setContentView(b.root)

        // Collect cards & radios
        val allCards: List<MaterialCardView> = listOf(b.cardStudent, b.cardTeacher, b.cardIt)
        val allRadios: List<RadioButton> = listOf(b.rbStudent, b.rbTeacher, b.rbItSupport)

        // Make cards checkable, disable default tick
        allCards.forEach { it.isCheckable = true; it.checkedIcon = null }

        var suppress = false
        fun select(index: Int) {
            if (suppress) return
            suppress = true
            allRadios.forEachIndexed { i, r -> r.isChecked = (i == index) }
            allCards.forEachIndexed { i, c -> c.isChecked = (i == index) }
            suppress = false
        }

        allCards.forEachIndexed { i, card ->
            card.setOnClickListener { select(i) }
        }
        allRadios.forEachIndexed { i, rb ->
            rb.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) select(i)
            }
        }

        // Continue button → save user + go to WordActivity
        b.btnContinue.setOnClickListener {
            val name = b.etName.text?.toString()?.trim().ifNullOrBlank { "Guest" }
            val role = when {
                b.rbItSupport.isChecked -> "IT Support"
                b.rbTeacher.isChecked   -> "Teacher"
                else                    -> "Student"
            }

            UserStore.saveUser(this, name, role)

            val intent = Intent(this, WordActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            startActivity(intent)
            finish()
        }
    }

    private fun String?.ifNullOrBlank(fallback: () -> String): String =
        if (this.isNullOrBlank()) fallback() else this
}
