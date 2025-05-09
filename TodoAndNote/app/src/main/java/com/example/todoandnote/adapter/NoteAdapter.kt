package com.example.todoandnote.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoandnote.data.Note
import com.example.todoandnote.databinding.ItemNoteBinding
import com.example.todoandnote.ui.NoteDetailActivity
import com.example.todoandnote.ui.TaskDetailActivity

class NoteAdapter(private var noteList: List<Note>,  private val onDeleteClick: (Note) -> Unit) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = noteList[position]
        holder.binding.tvTitle.text = note.title
        holder.binding.tvContent.text = note.content

        holder.binding.ivDelete.setOnClickListener {
            onDeleteClick(note)
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, NoteDetailActivity::class.java).apply {
                putExtra("title", note.title)
                putExtra("content", note.content)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = noteList.size

    // Notları güncellemek için çağrılır
    fun updateNotes(newNotes: List<Note>) {
        noteList = newNotes
        notifyDataSetChanged()
    }
}
