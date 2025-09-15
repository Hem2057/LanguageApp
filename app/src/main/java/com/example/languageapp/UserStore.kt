package com.example.languageapp

import android.content.Context

object UserStore {
    private const val PREFS = "user_prefs"
    private const val KEY_NAME = "name"
    private const val KEY_ROLE = "role"
    private const val KEY_ONBOARDED = "onboarded"

    fun saveUser(context: Context, name: String, role: String) {
        val p = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        p.edit().putString(KEY_NAME, name)
            .putString(KEY_ROLE, role)
            .putBoolean(KEY_ONBOARDED, true)
            .apply()
    }

    fun clear(context: Context) {
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit().clear().apply()
    }

    fun isOnboarded(context: Context): Boolean =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getBoolean(KEY_ONBOARDED, false)

    fun name(context: Context): String =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY_NAME, "Guest") ?: "Guest"

    fun role(context: Context): String =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY_ROLE, "Student") ?: "Student"
}
