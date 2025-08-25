package com.example.languageapp

enum class Role(val label: String) {
    STUDENT("Student"),
    TEACHER("Teacher"),
    IT_SUPPORT("IT Support");

    companion object {
        fun fromId(id: Int): Role = when (id) {
            R.id.rbTeacher   -> TEACHER
            R.id.rbItSupport -> IT_SUPPORT
            else             -> STUDENT
        }
    }
}
