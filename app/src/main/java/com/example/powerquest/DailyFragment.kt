package com.example.powerquest

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DailyFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imageList: Array<Int>
    private lateinit var titleList: Array<String>
    private lateinit var animationData: Map<String, List<Int>>
    private lateinit var myAdapter: AdapterClass

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_daily, container, false)

        // Hubungkan RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)

        // Inisialisasi data kategori
        imageList = arrayOf(
            R.drawable.perut,
            R.drawable.lengan,
            R.drawable.kaki,
            R.drawable.dada,
            R.drawable.punggungdanbahu
        )

        titleList = arrayOf(
            "Otot Perut",
            "Otot Lengan",
            "Otot Kaki",
            "Otot Dada",
            "Otot Punggung dan Bahu"
        )

        // Data animasi JSON berdasarkan kategori
        val animationData = mapOf(
            "Otot Perut" to listOf(
                R.raw.frog_press to ("Frog" to "30 Detik"),
                R.raw.leg_up to ("Leg up" to "x25"),
                R.raw.press_up to ("Press Up" to "x25"),
                R.raw.reverse_crunches to ("Reverse Crunches" to "x20"),
                R.raw.sit_up to ("Sit Up" to "x15"),
                R.raw.side_plank to ("Side Plank" to "30 Detik"),
                R.raw.cobras to ("Cobra" to "30 Detik"),
            ),

            "Otot Lengan" to listOf(
                R.raw.push_up to ("Push Up" to "x10"),
                R.raw.punches to ("Punches" to "x30"),
                R.raw.pull_up to ("Pull Up" to "x16"),
                R.raw.burpees to ("Bumper" to "x15"),
                R.raw.inchworm to ("Inchworm" to "x20"),
            ),

            "Otot Kaki" to listOf(
                R.raw.jumping_squats to ("Jumping Squats" to "x25"),
                R.raw.jumping_jack to ("Jumping Jack" to "x25"),
                R.raw.burpees to ("Bumper" to "x15"),
                R.raw.squat_kicks to ("Squat Kicks" to "x15"),
                R.raw.box_jump to ("Box Jump" to "x15"),
                R.raw.chair_stand to("Chair Stand" to "x20"),
                R.raw.run to("Run" to "30 Detik"),
                R.raw.bumper to ("Free Body" to "x15"),
                R.raw.frog_press to ("Frog" to "30 Detik"),
                R.raw.high_knees to ("High Knees" to "x20"),
                R.raw.rope to ("Rope" to "x25"),
                R.raw.run to ("Sprint" to "15 Detik"),
                R.raw.single_leg_hip to("Single Leg Hip" to "x20"),
                R.raw.split_jump to("Split Jump" to "x20"),
                R.raw.squat_reach to("Squat Reach" to "x20"),
            ),
            "Otot Dada" to listOf(
                R.raw.press_up to ("Press Up" to "x20"),
                R.raw.pull_up to ("Pull Up" to "x10"),
                R.raw.push_up to ("Push Up" to "x10"),
            ),
            "Otot Punggung dan Bahu" to listOf(
                R.raw.press_up to ("Press Up" to "x20"),
                R.raw.pull_up to ("Pull Up" to "x10"),
                R.raw.push_up to ("Push Up" to "x10"),
                R.raw.side_plank to ("Side Plank" to "x10"),
                R.raw.cobras to ("Cobra" to "30 Detik"),

                )
        )

        myAdapter = AdapterClass(imageList, titleList) { position ->
            val animations = animationData[titleList[position]]?.map { it.first }?.toIntArray()
            val names = animationData[titleList[position]]?.map { it.second.first }?.toTypedArray()
            val reps = animationData[titleList[position]]?.map { it.second.second }?.toTypedArray()

            android.util.Log.d("com.example.powerquest.DailyFragment", "Navigating to DetailActivity")
            val intent = Intent(requireContext(), DetailActivity::class.java).apply {
                putExtra("image", imageList[position])
                putExtra("title", titleList[position])
                putExtra("animations", animations)
                putExtra("names", names)
                putExtra("reps", reps)
            }
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = myAdapter

        return view
    }
}