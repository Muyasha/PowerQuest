package com.example.powerquest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog

class CustomFragment : Fragment() {

    private lateinit var buttonAdd: Button
    private lateinit var recyclerViewDays: RecyclerView
    private lateinit var layoutDefault: View
    private lateinit var textDefault: TextView

    private val selectedExercisesByDay = mutableMapOf<String, MutableList<ExerciseItem>>()
    private val daysList = mutableListOf<String>()

    companion object {
        private const val REQUEST_CODE_DETAIL_CUSTOM = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_custom, container, false)

        buttonAdd = view.findViewById(R.id.button_add)
        recyclerViewDays = view.findViewById(R.id.recycler_view_days)
        layoutDefault = view.findViewById(R.id.layout_default)
        textDefault = view.findViewById(R.id.text_default)

        recyclerViewDays.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewDays.adapter = DaysAdapter(daysList) { selectedDay ->
            val exercisesForSelectedDay = selectedExercisesByDay[selectedDay] ?: mutableListOf()

            // Navigasi ke DetailCustom dengan latihan untuk hari yang dipilih
            val intent = Intent(requireContext(), DetailCustom::class.java)
            intent.putParcelableArrayListExtra("selected_exercises", ArrayList(exercisesForSelectedDay))
            intent.putExtra("selected_day", selectedDay)
            startActivityForResult(intent, REQUEST_CODE_DETAIL_CUSTOM)
        }

        // Atur visibilitas awal
        recyclerViewDays.visibility = View.GONE
        layoutDefault.visibility = View.VISIBLE

        buttonAdd.setOnClickListener {
            showAddExerciseBottomSheet()
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_DETAIL_CUSTOM && resultCode == Activity.RESULT_OK) {
            // Ambil data yang dikembalikan dari DetailCustom
            val updatedExercises = data?.getParcelableArrayListExtra<ExerciseItem>("selected_exercises")
            val selectedDay = data?.getStringExtra("selected_day")

            if (selectedDay != null && updatedExercises != null) {
                selectedExercisesByDay[selectedDay] = ArrayList(updatedExercises)

                // Refresh RecyclerView untuk hari-hari yang ditampilkan
                recyclerViewDays.adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun showAddExerciseBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.dialog_add_exercise, null)
        bottomSheetDialog.setContentView(view)

        val recyclerViewExercises = view.findViewById<RecyclerView>(R.id.recycler_view_exercises)
        val buttonAddExercise = view.findViewById<Button>(R.id.button_add_exercise)
        val spinnerDay = view.findViewById<Spinner>(R.id.spinner_day)

        val daysArray = resources.getStringArray(R.array.days_array)

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_item,
            daysArray
        )
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item)
        spinnerDay.adapter = spinnerAdapter

        val exercises = listOf(
            ExerciseItem("Box Jump", R.raw.box_jump, "x15"),
            ExerciseItem("Bumper", R.raw.bumper, "x10"),
            ExerciseItem("Burpees", R.raw.burpees, "x15"),
            ExerciseItem("Chair Stand", R.raw.chair_stand, "x10"),
            ExerciseItem("Cobra", R.raw.cobras, "x15"),
            ExerciseItem("Frog Press", R.raw.frog_press, "x10"),
            ExerciseItem("High Knee", R.raw.high_knees, "x15"),
            ExerciseItem("Inchworm", R.raw.inchworm, "10"),
            ExerciseItem("Jumping Jack", R.raw.jumping_jack, "x15"),
            ExerciseItem("Jumping Squats", R.raw.jumping_squats, "x10"),
            ExerciseItem("Leg Up", R.raw.leg_up, "x15"),
            ExerciseItem("Press Up", R.raw.press_up, "x10"),
            ExerciseItem("Pull Up", R.raw.pull_up, "x15"),
            ExerciseItem("Punches", R.raw.punches, "x10"),
            ExerciseItem("Push Up", R.raw.push_up, "x15"),
            ExerciseItem("Reverse Crunches", R.raw.reverse_crunches, "x10"),
            ExerciseItem("Rope", R.raw.rope, "x15"),
            ExerciseItem("Sprint", R.raw.run, "15 Detik"),
            ExerciseItem("Single Leg Hip", R.raw.single_leg_hip, "30 Detik"),
            ExerciseItem("Sit Up", R.raw.sit_up, "x10"),
            ExerciseItem("Split Jump", R.raw.split_jump, "x15"),
            ExerciseItem("Squat Kick", R.raw.squat_kicks, "x10"),
            ExerciseItem("Squat Reach", R.raw.squat_reach, "x15")
        )

        val selectedExercises = mutableListOf<ExerciseItem>()
        val exerciseAdapter = ExercisesAdapter(exercises) { selectedItem ->
            if (!selectedExercises.contains(selectedItem)) {
                selectedExercises.add(selectedItem)
            }
        }

        recyclerViewExercises.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewExercises.adapter = exerciseAdapter

        buttonAddExercise.setOnClickListener {
            val selectedDay = spinnerDay.selectedItem.toString()

            // Tambahkan latihan ke hari yang sesuai
            val exercisesForDay = selectedExercisesByDay.getOrPut(selectedDay) { mutableListOf() }
            exercisesForDay.addAll(selectedExercises)

            if (!daysList.contains(selectedDay)) {
                daysList.add(selectedDay)
                daysList.sort()
                recyclerViewDays.adapter?.notifyDataSetChanged()
            }

            // Perbarui visibilitas
            recyclerViewDays.visibility = View.VISIBLE
            textDefault.visibility = View.GONE
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }
}
