package com.example.todoandnote.ui

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todoandnote.data.AppDatabase
import com.example.todoandnote.data.Task
import com.example.todoandnote.databinding.ActivityAddTaskBinding
import java.util.*

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding
    private lateinit var db: AppDatabase
    private var selectedHour = -1
    private var selectedMinute = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getDatabase(applicationContext)

        // Zaman seçme işlemi
        binding.etTaskTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, { _, selectedHourOfDay, selectedMinuteOfHour ->
                selectedHour = selectedHourOfDay
                selectedMinute = selectedMinuteOfHour
                val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                binding.etTaskTime.setText(formattedTime)
            }, hour, minute, true)

            timePickerDialog.show()
        }

        binding.btnSaveTask.setOnClickListener {
            val title = binding.etTaskName.text.toString()
            val description = binding.etTaskDescription.text.toString()
            val timeText = binding.etTaskTime.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty() && timeText.isNotEmpty()) {
                val timestamp = getTimeInMillis(selectedHour, selectedMinute)
                if (timestamp != null) {
                    val task = Task(title = title, description = description, timestamp = timestamp)

                    // Veri tabanına kaydetme
                    Thread {
                        db.taskDao().insert(task)
                        runOnUiThread {
                            Toast.makeText(this, "Aktivite Kaydedildi", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }.start()
                } else {
                    Toast.makeText(this, "Zaman seçimi hatalı", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Tüm alanları doldurun", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getTimeInMillis(hour: Int, minute: Int): Long? {
        return try {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, hour)
                set(Calendar.MINUTE, minute)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)

                if (before(Calendar.getInstance())) {
                    add(Calendar.DAY_OF_YEAR, 1)
                }
            }
            calendar.timeInMillis
        } catch (e: Exception) {
            null
        }
    }
}
