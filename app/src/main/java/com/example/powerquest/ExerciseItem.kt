package com.example.powerquest

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExerciseItem(
    val title: String,
    val animationRes: Int,
    var reps: String
) : Parcelable




