package com.example.languageapp
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.languageapp.databinding.ActivityWordBinding


class WordActivity : AppCompatActivity() {

    private lateinit var b: ActivityWordBinding
    private lateinit var adapter: WordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityWordBinding.inflate(layoutInflater)
        setContentView(b.root)

        // Top app tool bar-title
        setSupportActionBar(b.toolbar)
        supportActionBar?.title = "Tiwi Language Learning Mobile App"

        //FOR JSON FILE
//        val json = intent.getStringExtra("words_json")
//        val type = object : TypeToken<List<Word>>() {}.type
//        val words: List<Word> = if (json != null) {
//            Gson().fromJson(json, type)
//        } else{
//            emptyList()
//
//        }

        //DATA
        val words = WordRepository.getWords()

        //RecylerView

        adapter = WordAdapter(words.toMutableList())
        b.recycler.layoutManager = LinearLayoutManager(this)
        b.recycler.adapter = adapter

        // Search filter
        b.tietSearch.addTextChangedListener { text ->
            adapter.filter(text?.toString().orEmpty())
        }



    // Swipe-to-delete (Touch event)
    val itemTouchHelper = ItemTouchHelper(object :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.adapterPosition
            adapter.removeAt(pos)
        }
    })
    itemTouchHelper.attachToRecyclerView(b.recycler)
}

//TOOLBAR MENU AND LOGOUT
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.word_menu, menu) // make sure word_menu.xml exists
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                val i = Intent(this, WelcomeActivity::class.java).apply {
                    // clear back stack: RoleSelection will be the only activity
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(i)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
