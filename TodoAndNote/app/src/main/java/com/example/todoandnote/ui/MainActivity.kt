package com.example.todoandnote.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoandnote.R
import com.example.todoandnote.adapter.TaskAdapter
import com.example.todoandnote.data.AppDatabase
import com.example.todoandnote.data.Note
import com.example.todoandnote.data.Task
import com.example.todoandnote.databinding.ActivityMainBinding
import com.example.todoandnote.ui.adapter.NoteAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: AppDatabase
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var noteAdapter: NoteAdapter

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(this)

        // Adapter'ları oluştur
        taskAdapter = TaskAdapter(emptyList()) {task ->
            deleteTask(task)

        }

        // NoteAdapter silme işlemi ile birlikte
        noteAdapter = NoteAdapter(emptyList()) { note ->
            deleteNote(note)
        }

        // Task RecyclerView
        binding.recyclerViewTasks.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewTasks.adapter = taskAdapter

        // Note RecyclerView
        binding.recyclerViewNotes.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewNotes.adapter = noteAdapter

        // FAB menüsü
        binding.fabMenu.setOnClickListener { view ->
            val popup = PopupMenu(this, view)
            popup.menuInflater.inflate(R.menu.menu, popup.menu)
            popup.setForceShowIcon(true)

            popup.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_add_note -> {
                        startActivity(Intent(this, AddNoteActivity::class.java))
                        true
                    }

                    R.id.menu_add_task -> {
                        startActivity(Intent(this, AddTaskActivity::class.java))
                        true
                    }

                    else -> false
                }
            }
            popup.show()
        }
    }

    override fun onResume() {
        super.onResume()
        loadTasks()
        loadNotes()
    }

    private fun loadTasks() {
        Thread {
            val tasks = db.taskDao().getAll()
            runOnUiThread {
                taskAdapter.updateTasks(tasks)
            }
        }.start()
    }

    private fun loadNotes() {
        Thread {
            val notes = db.noteDao().getAll()
            runOnUiThread {
                noteAdapter.updateNotes(notes)
            }
        }.start()
    }

    private fun deleteNote(note: Note) {
        Thread {
            db.noteDao().delete(note)
            val updatedNotes = db.noteDao().getAll()
            runOnUiThread {
                noteAdapter.updateNotes(updatedNotes)
            }
        }.start()
    }

    private fun deleteTask(task: Task) {
        Thread {
            db.taskDao().delete(task)
            val updatedTask = db.taskDao().getAll()
            runOnUiThread {
                taskAdapter.updateTasks(updatedTask)
            }
        }.start()
    }
}
