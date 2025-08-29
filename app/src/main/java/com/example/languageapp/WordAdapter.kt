package com.example.languageapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordAdapter(
    private val items: List<Word>,
    private val onClick: (Word) -> Unit
) : RecyclerView.Adapter<WordAdapter.VH>() {

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val tvWord: TextView = v.findViewById(R.id.tvWord)
        val tvGloss: TextView = v.findViewById(R.id.tvGloss)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_word, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val w = items[position]
        holder.tvWord.text = w.headword
        holder.tvGloss.text = w.gloss
        holder.itemView.setOnClickListener { onClick(w) }
    }

    override fun getItemCount(): Int = items.size
}
