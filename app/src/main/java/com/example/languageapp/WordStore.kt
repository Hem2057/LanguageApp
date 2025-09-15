package com.example.languageapp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object WordStore {
    private const val PREFS = "word_prefs"
    private const val KEY = "words"
    private val gson = Gson()

    fun load(context: Context): MutableList<Word> {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY, null) ?: return mutableListOf()
        val type = object : TypeToken<MutableList<Word>>() {}.type
        return gson.fromJson(json, type)
    }

    fun save(context: Context, words: List<Word>) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY, gson.toJson(words)).apply()
    }
}
