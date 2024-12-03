package com.example.powerquest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DaysAdapter(
    private val days: List<String>,
    private val onDaySelected: (String) -> Unit
) : RecyclerView.Adapter<DaysAdapter.DayViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_list, parent, false)
        return DayViewHolder(view)
    }

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) {
        val day = days[position]
        holder.bind(day)

        holder.itemView.setOnClickListener {
            onDaySelected(day)
        }
    }

    override fun getItemCount() = days.size

    class DayViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tTitle: TextView = view.findViewById(R.id.tTitle)

        fun bind(day: String) {
            tTitle.text = day
        }
    }
}

