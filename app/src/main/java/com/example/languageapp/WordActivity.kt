package com.example.languageapp

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.content.edit
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.languageapp.databinding.ActivityWordBinding
import java.io.File

class WordActivity : AppCompatActivity(), WordAdapter.Callbacks {

    private lateinit var b: ActivityWordBinding
    private lateinit var adapter: WordAdapter

    // Favorites
    private val prefs by lazy { getSharedPreferences("prefs_word", MODE_PRIVATE) }
    private val favoriteSet: MutableSet<String> by lazy {
        prefs.getStringSet("favorites", emptySet())?.toMutableSet() ?: mutableSetOf()
    }

    // Audio
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var isRecording = false

    // Camera
    private var pendingPhotoWord: Word? = null
    private var pendingPhotoUri: Uri? = null

    // Permission launcher (mic/camera)
    private val requestPerms = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { /* user can tap again after granting */ }

    // Take picture launcher
    private val takePicture = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { saved ->
        val w = pendingPhotoWord
        val uri = pendingPhotoUri
        if (saved && w != null && uri != null) {
            Toast.makeText(this, "Photo saved for ${w.word}", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Photo canceled", Toast.LENGTH_SHORT).show()
        }
        pendingPhotoWord = null
        pendingPhotoUri = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityWordBinding.inflate(layoutInflater)
        setContentView(b.root)
        setSupportActionBar(b.toolbar)

        val words = WordRepository.getWords()

        adapter = WordAdapter(words.toMutableList(), favoriteSet, this)
        b.recycler.layoutManager = LinearLayoutManager(this)
        b.recycler.adapter = adapter

        // Search
        b.tietSearch.addTextChangedListener { text ->
            adapter.filter(text?.toString().orEmpty())
        }

        // Swipe-to-delete
        val helper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(rv: RecyclerView, vh: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = false
            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
                adapter.removeAt(vh.bindingAdapterPosition)
            }
        })
        helper.attachToRecyclerView(b.recycler)

        // Optional: request once on start (we also check on demand)
        requestPerms.launch(arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA))
    }

    // ===== Adapter callbacks =====

    override fun onRecord(word: Word) {
        if (!hasPermission(Manifest.permission.RECORD_AUDIO)) {
            requestPerms.launch(arrayOf(Manifest.permission.RECORD_AUDIO))
            Toast.makeText(this, "Please allow Microphone permission to record", Toast.LENGTH_SHORT).show()
            return
        }
        if (!isRecording) startRecording(word) else stopRecording()
    }

    override fun onPlay(word: Word) {
        val file = audioFileFor(word)
        // require a non-tiny file (avoid “instant stop” empties)
        if (!file.exists() || file.length() < 2048L) {
            Toast.makeText(this, "No usable recording for ${word.word}", Toast.LENGTH_SHORT).show()
            return
        }

        stopPlaying()

        try {
            player = MediaPlayer().apply {
                // Always safe (minSdk 24)
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                        .build()
                )
                setDataSource(file.absolutePath)

                setOnPreparedListener { mp ->
                    // Force speaker so it doesn't route to the earpiece
                    val am = getSystemService(AUDIO_SERVICE) as AudioManager
                    am.mode = AudioManager.MODE_NORMAL
                    am.isSpeakerphoneOn = true

                    mp.start()
                }
                setOnCompletionListener { stopPlaying() }
                setOnErrorListener { _, what, extra ->
                    stopPlaying()
                    Toast.makeText(
                        this@WordActivity,
                        "Playback error ($what/$extra)",
                        Toast.LENGTH_SHORT
                    ).show()
                    false
                }

                // Synchronous prepare so errors surface immediately
                prepare()
                start()
            }
        } catch (e: Exception) {
            stopPlaying()
            Toast.makeText(
                this,
                "Could not play recording: ${e.localizedMessage ?: "unknown error"}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCamera(word: Word) {
        if (!hasPermission(Manifest.permission.CAMERA)) {
            requestPerms.launch(arrayOf(Manifest.permission.CAMERA))
            Toast.makeText(this, "Please allow Camera permission to take a photo", Toast.LENGTH_SHORT).show()
            return
        }
        val dir = File(filesDir, "images").apply { if (!exists()) mkdirs() }
        val file = File(dir, "photo_${sanitize(word.word)}.jpg")
        val uri = FileProvider.getUriForFile(this, "${packageName}.fileprovider", file)
        pendingPhotoWord = word
        pendingPhotoUri = uri
        takePicture.launch(uri)
    }

    override fun onToggleFavorite(word: Word, nowFavorite: Boolean) {
        prefs.edit { putStringSet("favorites", favoriteSet) }
        Toast.makeText(
            this,
            if (nowFavorite) "Added to favorites: ${word.word}" else "Removed from favorites: ${word.word}",
            Toast.LENGTH_SHORT
        ).show()
    }

    // ===== Recording helpers (AAC .m4a to filesDir) =====

    private fun startRecording(word: Word) {
        val file = audioFileFor(word) // .../files/audio_<word>.m4a
        stopRecording()
        stopPlaying()

        @Suppress("DEPRECATION")
        recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(this)
        } else {
            MediaRecorder()
        }.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            // Use AAC in MPEG_4 container (m4a) — plays on all modern devices
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setOutputFile(file.absolutePath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setAudioEncodingBitRate(128_000)
            setAudioSamplingRate(44_100)
            prepare()
            start()
        }

        isRecording = true
        Toast.makeText(this, "Recording… ${word.word}", Toast.LENGTH_SHORT).show()
    }

    private fun stopRecording() {
        if (!isRecording) return
        try { recorder?.stop() } catch (_: Exception) { /* short recs can throw */ }
        recorder?.release()
        recorder = null
        isRecording = false
        Toast.makeText(this, "Recording saved", Toast.LENGTH_SHORT).show()
    }

    private fun stopPlaying() {
        try { player?.stop() } catch (_: Exception) { }
        player?.release()
        player = null
    }

    // Save in filesDir (persistent) with .m4a extension
    private fun audioFileFor(word: Word): File =
        File(filesDir, "audio_${sanitize(word.word)}.m4a")

    private fun sanitize(s: String) =
        s.lowercase().replace("[^a-z0-9_]".toRegex(), "_")

    private fun hasPermission(p: String) =
        ActivityCompat.checkSelfPermission(this, p) == PackageManager.PERMISSION_GRANTED

    // ===== Toolbar menu (Logout) =====
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.word_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                val i = Intent(this, RoleSelectionActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(i)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onStop() {
        super.onStop()
        stopRecording()
        stopPlaying()
    }
}
