package com.example.todoandnote.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todoandnote.R
import com.example.todoandnote.databinding.ActivityNoteDetailBinding
import com.example.todoandnote.databinding.ActivityTaskDetailBinding

class NoteDetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityNoteDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val title = intent.getStringExtra("title") ?: ""
        val content = intent.getStringExtra("content") ?: ""

        binding.tvNoteTitle.text = title
        binding.tvNoteContent.text = content
    }
}