package com.example.languageapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LinesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // If you have a layout for this, set it here. Otherwise this is just a stub.
        val tv = TextView(this).apply {
            text = LineUtils.buildLinesAsBlock(5)
            textSize = 18f
        }
        setContentView(tv)
    }
}
