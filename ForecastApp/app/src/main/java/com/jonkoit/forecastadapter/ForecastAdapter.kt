package com.jonkoit.forecastadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jonkoit.dataclasessandmodels.ForecastItem
import com.jonkoit.forecastapp.R

class ForecastAdapter(private val forecastItems: List<ForecastItem>) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.forecast_item, parent, false)
        return ForecastViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val forecastItem = forecastItems[position]
        holder.bind(forecastItem)
    }

    override fun getItemCount(): Int = forecastItems.size

    inner class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTextView: TextView = itemView.findViewById(R.id.textViewDate)
        private val temperatureTextView: TextView = itemView.findViewById(R.id.textViewTemperature)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.textViewDescription)

        fun bind(forecastItem: ForecastItem) {
            dateTextView.text = "Date: ${forecastItem.date}"
            temperatureTextView.text = "Temperature: ${forecastItem.temperature} °C"
            descriptionTextView.text = "Description: ${forecastItem.description}"
        }
    }
}
