package com.example.powerquest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.powerquest.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var animationAdapter: AnimationAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil data dari intent
        val imageResId = intent.getIntExtra("image", -1)
        val title = intent.getStringExtra("title")
        val animations = intent.getIntArrayExtra("animations")?.toList() ?: emptyList()
        val names = intent.getStringArrayExtra("names")?.toList() ?: emptyList()
        val reps = intent.getStringArrayExtra("reps")?.toMutableList() ?: mutableListOf()

        if (imageResId != -1) {
            binding.headerImage.setImageResource(imageResId)
        }
        // Set title dan subtitle
        binding.detailTitle.text = title
        binding.detailSubtitle.text = "${animations.size} Latihan"

        // Setup RecyclerView
        animationAdapter = AnimationAdapter(animations, names, reps)
        binding.exerciseRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.exerciseRecyclerView.adapter = animationAdapter

        // Set listener pada tombol "Mulai"
        binding.startButton.setOnClickListener {
            if (animations.isNotEmpty() && names.isNotEmpty() && reps.isNotEmpty()) {
                // Mulai StartActivity
                val intent = Intent(this, StartActivity::class.java).apply {
                    putExtra("animations", animations.toIntArray())
                    putExtra("names", names.toTypedArray())
                    putExtra("reps", reps.toTypedArray())
                }
                startActivity(intent)
            } else {
                // Tampilkan pesan jika data tidak lengkap
                Toast.makeText(
                    this@DetailActivity,
                    "Data tidak lengkap untuk memulai latihan!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
