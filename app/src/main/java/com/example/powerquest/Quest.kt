package com.example.powerquest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class Quest : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_quest, container, false)

        // Referensi komponen dari XML
        val tabDaily: TextView = view.findViewById(R.id.tab_daily)
        val tabCustom: TextView = view.findViewById(R.id.tab_custom)
        val contentContainer: ViewGroup = view.findViewById(R.id.content_container)

        // Fungsi untuk mengganti layout dalam FrameLayout
        fun switchContent(layoutResId: Int) {
            val newView = LayoutInflater.from(requireContext()).inflate(layoutResId, contentContainer, false)
            contentContainer.removeAllViews() // Hapus konten lama
            contentContainer.addView(newView) // Tambahkan konten baru
        }

        // Set default tab sebagai Daily
        switchContent(R.layout.fragment_daily)

        // Atur onClickListener untuk tab Daily
        tabDaily.setOnClickListener {
            switchContent(R.layout.fragment_daily)

            // Tambahkan gaya untuk menandai tab aktif
            tabDaily.setTextColor(resources.getColor(R.color.white, null))
            tabCustom.setTextColor(resources.getColor(R.color.purple, null))
        }

        // Atur onClickListener untuk tab Custom
        tabCustom.setOnClickListener {
            switchContent(R.layout.fragment_custom)

            // Tambahkan gaya untuk menandai tab aktif
            tabCustom.setTextColor(resources.getColor(R.color.white, null))
            tabDaily.setTextColor(resources.getColor(R.color.purple, null))
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = Quest()
    }
}
