package com.example.languageapp

import android.content.Context
import org.json.JSONArray

object WordRepository {
    fun load(context: Context): List<Word> {
        val res = context.resources.openRawResource(R.raw.tiwi_words).bufferedReader().use { it.readText() }
        val arr = JSONArray(res)
        val list = ArrayList<Word>()
        for (i in 0 until arr.length()) {
            val o = arr.getJSONObject(i)
            list.add(
                Word(
                    headword = o.getString("headword"),
                    gloss = o.getString("gloss"),
                    pos = o.getString("pos"),
                    example = o.getString("example")
                )
            )
        }
        return list
    }
}
