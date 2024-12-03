package com.example.powerquest

import androidx.lifecycle.ViewModel

class CustomViewModel : ViewModel() {
    val selectedExercisesByDay = mutableMapOf<String, MutableList<ExerciseItem>>()
}
