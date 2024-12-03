package com.example.powerquest

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView

class StartActivity : AppCompatActivity() {

    private lateinit var animationView: LottieAnimationView
    private lateinit var titleText: TextView
    private lateinit var repsTextView: TextView
    private lateinit var nextButton: Button

    private var animationIndex = 0
    private lateinit var animations: List<Int>
    private lateinit var names: List<String>
    private lateinit var reps: List<String>
    private var activeTimer: CountDownTimer? = null // Reference to active timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        // Bind views
        animationView = findViewById(R.id.lottieAnimationView)
        titleText = findViewById(R.id.titleText)
        repsTextView = findViewById(R.id.repsTextView)
        nextButton = findViewById(R.id.nextButton)

        // Get data from intent
        animations = intent.getIntArrayExtra("animations")?.toList() ?: emptyList()
        names = intent.getStringArrayExtra("names")?.toList() ?: emptyList()
        reps = intent.getStringArrayExtra("reps")?.toList() ?: emptyList()

        // Validate if data is available
        if (animations.isEmpty() || names.isEmpty() || reps.isEmpty()) {
            finishWithError("Data tidak ditemukan! Pastikan semua data dikirim dengan benar.")
            return
        }

        // Initialize first animation
        showAnimation(animationIndex)

        // Set Next Button click listener
        nextButton.setOnClickListener {
            // Cancel any active timer before moving to the next animation
            activeTimer?.cancel()

            if (animationIndex < animations.size - 1) {
                animationIndex++
                showAnimation(animationIndex)
            } else {
                Toast.makeText(this, "Semua latihan selesai!", Toast.LENGTH_LONG).show()
                finish() // End activity when all animations are shown
            }
        }
    }

    private fun showAnimation(index: Int) {
        if (index < 0 || index >= animations.size) {
            finishWithError("Indeks animasi tidak valid! Indeks: $index, Ukuran list: ${animations.size}")
            return
        }

        // Update UI for the current animation
        animationView.setAnimation(animations[index])
        titleText.text = names[index]
        animationView.playAnimation()

        val currentReps = reps[index]
        if (currentReps.contains("Detik")) {
            val duration = currentReps.replace(" Detik", "").toInt() * 1000L
            startCountdown(duration)
        } else if (currentReps.startsWith("x")) {
            val repetitions = currentReps.replace("x", "").toInt()
            showRepetitionInstruction(repetitions)
        }
    }

    private fun startCountdown(duration: Long) {
        repsTextView.visibility = View.VISIBLE

        // Cancel any previous timer to prevent overlap
        activeTimer?.cancel()

        // Start new timer
        activeTimer = object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                repsTextView.text = "${millisUntilFinished / 1000} Detik"
            }

            override fun onFinish() {
                repsTextView.text = "Selesai"
            }
        }.start()
    }

    private fun showRepetitionInstruction(repetitions: Int) {
        repsTextView.visibility = View.VISIBLE

        // Cancel any previous timer since no countdown is needed for repetitions
        activeTimer?.cancel()

        repsTextView.text = "Lakukan sebanyak $repetitions kali"
    }

    private fun finishWithError(message: String) {
        // Log error and show toast if needed
        android.util.Log.e("StartActivity", message)
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        finish() // Close activity
    }
}
