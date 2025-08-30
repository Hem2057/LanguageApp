package com.example.languageapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WordAdapter(
    private val fullList: MutableList<Word>,
    private val favorites: MutableSet<String>,
    private val callbacks: Callbacks
) : RecyclerView.Adapter<WordAdapter.VH>() {

    interface Callbacks {
        fun onRecord(word: Word)
        fun onPlay(word: Word)
        fun onCamera(word: Word)
        fun onToggleFavorite(word: Word, nowFavorite: Boolean)
    }

    private val current = mutableListOf<Word>().apply { addAll(fullList) }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvWord: TextView = itemView.findViewById(R.id.tvWord)
        val tvMeaning: TextView = itemView.findViewById(R.id.tvMeaning)
        val btnRecord: ImageButton = itemView.findViewById(R.id.btnRecord)
        val btnPlay: ImageButton = itemView.findViewById(R.id.btnPlay)
        val btnCamera: ImageButton = itemView.findViewById(R.id.btnCamera)
        val btnFavorite: ImageButton = itemView.findViewById(R.id.btnFavorite)
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

        val isFav = favorites.contains(item.word)
        holder.btnFavorite.setImageResource(
            if (isFav) android.R.drawable.btn_star_big_on
            else android.R.drawable.btn_star_big_off
        )

        holder.btnRecord.setOnClickListener { callbacks.onRecord(item) }
        holder.btnPlay.setOnClickListener { callbacks.onPlay(item) }
        holder.btnCamera.setOnClickListener { callbacks.onCamera(item) }
        holder.btnFavorite.setOnClickListener {
            val nowFav = !favorites.contains(item.word)
            if (nowFav) favorites.add(item.word) else favorites.remove(item.word)
            callbacks.onToggleFavorite(item, nowFav)
            notifyItemChanged(holder.bindingAdapterPosition)
        }
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

    /** Swipe-to-delete support */
    fun removeAt(position: Int) {
        if (position !in current.indices) return
        val removedItem = current.removeAt(position)
        fullList.remove(removedItem) // remove the same object from the backing list
        notifyItemRemoved(position)
    }
}
