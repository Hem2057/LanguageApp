package com.example.languageapp

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

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
        val ivPhoto: ImageView = itemView.findViewById(R.id.ivPhoto)
        val btnRecord: ImageButton = itemView.findViewById(R.id.btnRecord)
        val btnPlay: ImageButton = itemView.findViewById(R.id.btnPlay)
        val btnCamera: ImageButton = itemView.findViewById(R.id.btnCamera)
        val btnFavorite: ImageButton = itemView.findViewById(R.id.btnFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_word, parent, false)
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

        val ctx = holder.itemView.context
        val imgFile = File(ctx.filesDir, "images/photo_${sanitize(item.word)}.jpg")
        if (imgFile.exists()) {
            holder.ivPhoto.setImageBitmap(BitmapFactory.decodeFile(imgFile.absolutePath))
            holder.ivPhoto.visibility = View.VISIBLE
        } else {
            holder.ivPhoto.visibility = View.GONE
        }

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
            current.addAll(fullList.filter {
                it.word.lowercase().contains(q) || it.meaning.lowercase().contains(q)
            })
        }
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        if (position !in current.indices) return
        val removed = current.removeAt(position)
        fullList.remove(removed)
        notifyItemRemoved(position)
    }

    private fun sanitize(s: String) =
        s.lowercase().replace("[^a-z0-9_]".toRegex(), "_")
}
