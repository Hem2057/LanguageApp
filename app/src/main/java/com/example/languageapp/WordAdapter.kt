package com.example.languageapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordAdapter(
    private val words: List<Word>,
    private val onClick: (Word) -> Unit
) : RecyclerView.Adapter<WordAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvWord: TextView = view.findViewById(R.id.tvWord)
        val tvMeaning: TextView = view.findViewById(R.id.tvMeaning)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.word_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = words.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val word = words[position]
        holder.tvWord.text = word.tiwi
        holder.tvMeaning.text = word.english
        holder.itemView.setOnClickListener { onClick(word) }
    }
}
