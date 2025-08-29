package com.example.languageapp

object WordRepository {
    fun getWords(): List<Word> {
        return listOf(
            Word("Yirrikapayi", "Hello/Greeting"),
            Word("Pwanga", "Water"),
            Word("Wuta", "Sun"),
            Word("Kulama", "Traditional ceremony"),
            Word("Jingi", "Fish"),
            Word("Tupuni", "Fire"),
            Word("Mirta", "Moon"),
            Word("Ampirri", "Star"),
            Word("Kapitiya", "Tree"),
            Word("Pima", "House")
        )
    }
}
