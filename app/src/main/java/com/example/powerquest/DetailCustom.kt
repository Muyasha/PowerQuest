package com.example.powerquest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DetailCustom : AppCompatActivity() {

    private lateinit var startCustomButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var editTextView: TextView
    private var selectedExercises: ArrayList<ExerciseItem>? = null

    // Activity Result Launcher untuk DetailEdit
    private val editLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            // Ambil data latihan yang diperbarui dari DetailEdit
            selectedExercises = result.data?.getParcelableArrayListExtra("selected_exercises")
            setupRecyclerView() // Refresh RecyclerView dengan data baru
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_custom)

        recyclerView = findViewById(R.id.list_recycler_view)
        editTextView = findViewById(R.id.textEdit) // Tombol Edit
        startCustomButton = findViewById(R.id.start_custom)

        // Ambil data dari Intent
        selectedExercises = intent.getParcelableArrayListExtra("selected_exercises")

        // Set up RecyclerView
        setupRecyclerView()

        // Navigasi ke DetailEdit saat Edit diklik
        editTextView.setOnClickListener {
            val intent = Intent(this, DetailEdit::class.java)
            intent.putParcelableArrayListExtra("selected_exercises", selectedExercises)
            editLauncher.launch(intent)
        }

        // Navigasi ke StartCustom saat Mulai diklik
        startCustomButton.setOnClickListener {
            val intent = Intent(this, StartCustom::class.java)
            intent.putParcelableArrayListExtra("selected_exercises", selectedExercises)
            intent.putExtra("current_index", 0)
            startActivity(intent)
        }
    }

    // Atur RecyclerView dengan adapter
    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = DetailAdapter(selectedExercises ?: mutableListOf()) { updatedExercise ->
            val index = selectedExercises?.indexOfFirst { it.title == updatedExercise.title }
            if (index != null && index >= 0) {
                selectedExercises!![index] = updatedExercise
                recyclerView.adapter?.notifyItemChanged(index)
            }
        }
    }
}
