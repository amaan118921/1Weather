package com.example.a1weather.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a1weather.R
import com.example.a1weather.domain.models.WeatherResponse
import kotlinx.android.synthetic.main.item_view_weather.view.*
import kotlin.math.roundToInt

class HomeAdapter: ListAdapter<WeatherResponse, HomeAdapter.HomeViewHolder>(DiffCallback) {
    class HomeViewHolder(view: View): RecyclerView.ViewHolder(view)

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<WeatherResponse>() {
            override fun areItemsTheSame(
                oldItem: WeatherResponse,
                newItem: WeatherResponse
            ): Boolean {
                return oldItem.name==newItem.name
            }

            override fun areContentsTheSame(
                oldItem: WeatherResponse,
                newItem: WeatherResponse
            ): Boolean {
                return oldItem.name==newItem.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context).inflate(R.layout.item_view_weather, parent, false)
        return HomeViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.apply {
            item.apply {
                tvCity.text = "$name, $country"
                tvTemp.text = "${((feelsLike ?: 273.15) - 273.15).roundToInt()}°C"
                tvWeather.text = weatherMain
            }
        }
    }
}