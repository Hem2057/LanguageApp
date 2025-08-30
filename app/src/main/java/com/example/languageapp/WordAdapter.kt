package com.example.languageapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordAdapter(private val fullList: MutableList<Word>) :
    RecyclerView.Adapter<WordAdapter.VH>() {

    private val current = mutableListOf<Word>().apply { addAll(fullList) }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvWord: TextView = itemView.findViewById(R.id.tvWord)
        val tvMeaning: TextView = itemView.findViewById(R.id.tvMeaning)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_word, parent, false)
        return VH(v)
    }

    override fun getItemCount(): Int = current.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = current[position]
        holder.tvWord.text = item.word
        holder.tvMeaning.text = item.meaning
    }

    fun filter(query: String) {
        val q = query.trim().lowercase()
        current.clear()
        if (q.isEmpty()) {
            current.addAll(fullList)
        } else {
            current.addAll(
                fullList.filter {
                    it.word.lowercase().contains(q) || it.meaning.lowercase().contains(q)
                }
            )
        }
        notifyDataSetChanged()
    }
}
