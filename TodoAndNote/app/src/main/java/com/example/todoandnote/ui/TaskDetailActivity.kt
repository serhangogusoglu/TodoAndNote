package com.example.todoandnote.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.todoandnote.R
import com.example.todoandnote.databinding.ActivityAddNoteBinding
import com.example.todoandnote.databinding.ActivityTaskDetailBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskDetailActivity : AppCompatActivity() {
    private lateinit var binding:ActivityTaskDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTaskDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val title = intent.getStringExtra("title") ?: ""
        val description = intent.getStringExtra("description") ?: ""
        val timestamp = intent.getLongExtra("timestamp", 0L)

        val formattedTime = SimpleDateFormat("dd MMM yyyy - HH:mm", Locale.getDefault()).format(Date(timestamp))

        binding.tvTaskTitle.text = title
        binding.tvTaskDescription.text = description
        binding.tvTaskTime.text = "Zaman: $formattedTime"
    }
}