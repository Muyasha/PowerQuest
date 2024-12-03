package com.example.powerquest

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable

class StartCustom : AppCompatActivity() {

    private lateinit var titleText: TextView
    private lateinit var repsTextView: TextView
    private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var nextButton: Button

    private var currentIndex = 0
    private var exercises: List<ExerciseItem> = emptyList()
    private var countDownTimer: CountDownTimer? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_custom)

        // Inisialisasi Views
        titleText = findViewById(R.id.titleTextCustom)
        repsTextView = findViewById(R.id.repsTextViewCustom)
        lottieAnimationView = findViewById(R.id.lottieAnimationViewCustom)
        nextButton = findViewById(R.id.nextButtonCustom)

        // Ambil data dari Intent
        exercises = intent.getParcelableArrayListExtra("selected_exercises") ?: emptyList()

        if (exercises.isNotEmpty()) {
            showCurrentExercise()
        } else {
            titleText.text = "Tidak ada latihan"
            nextButton.text = "Kembali"
            nextButton.setOnClickListener { finish() }
        }

        nextButton.setOnClickListener {
            // Hentikan timer jika sedang berjalan
            stopTimer()
            navigateToNextExercise()
        }
    }

    private fun navigateToNextExercise() {
        if (currentIndex < exercises.size - 1) {
            currentIndex++
            showCurrentExercise()
        } else {
            nextButton.text = "Selesai"
            finish() // Kembali ke halaman sebelumnya
        }
    }

    private fun showCurrentExercise() {
        if (currentIndex >= exercises.size) {
            return
        }

        val exercise = exercises[currentIndex]

        titleText.text = exercise.title
        lottieAnimationView.setAnimation(exercise.animationRes)

        when {
            // Latihan berbasis repetisi (dimulai dengan "x")
            exercise.reps.startsWith("x") -> {
                repsTextView.text = exercise.reps
                performReps(exercise.reps)
                nextButton.visibility = View.VISIBLE // Tombol tetap terlihat
            }
            // Latihan berbasis timer (mengandung "Detik")
            exercise.reps.contains("Detik", ignoreCase = true) -> {
                val seconds = exercise.reps.filter { it.isDigit() }.toIntOrNull() ?: 0
                startTimer(seconds)
                nextButton.visibility = View.VISIBLE // Tombol tetap terlihat
            }
            // Format tidak dikenal
            else -> {
                repsTextView.text = "Tidak Valid"
                nextButton.visibility = View.VISIBLE
            }
        }
    }

    private fun startTimer(seconds: Int) {
        if (seconds <= 0) {
            repsTextView.text = "00:00"
            navigateToNextExercise() // Jika waktu nol, lanjutkan otomatis
            return
        }

        countDownTimer = object : CountDownTimer(seconds * 1000L, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = millisUntilFinished / 1000
                val minutes = secondsLeft / 60
                val seconds = secondsLeft % 60
                repsTextView.text = String.format("%02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                repsTextView.text = "00:00"
                navigateToNextExercise() // Lanjutkan otomatis ketika waktu habis
            }
        }.start()
    }

    private fun stopTimer() {
        countDownTimer?.cancel()
        countDownTimer = null
    }

    @SuppressLint("SetTextI18n")
    private fun performReps(reps: String) {
        val totalReps = reps.removePrefix("x").toIntOrNull() ?: 1

        // Animasi terus berulang tanpa batas
        lottieAnimationView.repeatCount = LottieDrawable.INFINITE
        lottieAnimationView.playAnimation()

        // Tampilkan jumlah repetisi tanpa berpindah otomatis
        repsTextView.text = "x$totalReps"
    }

}
