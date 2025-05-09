package com.example.todoandnote.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todoandnote.R
import com.example.todoandnote.data.AppDatabase
import com.example.todoandnote.data.Note
import com.example.todoandnote.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddNoteBinding
    private lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        db = AppDatabase.getDatabase(applicationContext)

        binding.btnSaveNote.setOnClickListener {
            val title = binding.etNoteTitle.text.toString()
            val content = binding.etNoteContent.text.toString()

            if(title.isNotEmpty() && content.isNotEmpty()) {
                val note = Note(id = 0, title=title, content = content)

                Thread {
                    db.noteDao().insert(note)
                    runOnUiThread {
                        Toast.makeText(this, "Not Kaydedildi", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }.start()
            }else {
                Toast.makeText(this, "Tüm alanları doldurun", Toast.LENGTH_SHORT).show()
            }
        }
    }
}