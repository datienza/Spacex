package com.datienza.spacex.feature.rockets.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.datienza.spacex.core.model.Rocket
import com.datienza.spacex.feature.rockets.databinding.ItemRocketBinding

class RocketsAdapter(
    private val onItemClick: (Rocket) -> Unit,
) : RecyclerView.Adapter<RocketsAdapter.RocketViewHolder>() {

    private val items = mutableListOf<Rocket>()

    fun setItems(newItems: List<Rocket>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketViewHolder =
        RocketViewHolder(
            ItemRocketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount(): Int = items.size

    inner class RocketViewHolder(
        private val binding: ItemRocketBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(rocket: Rocket) {
            val ctx = itemView.context
            Glide.with(itemView)
                .load(rocket.image)
                .apply(RequestOptions().centerCrop())
                .into(binding.rocketIv)
            binding.rocketNameTv.text = rocket.name
            binding.rocketCountryTv.text =
                ctx.getString(R.string.rocket_country, rocket.country)
            binding.rocketNumEnginesTv.text =
                ctx.getString(R.string.rocket_num_engines, rocket.numEngines)
            itemView.setOnClickListener { onItemClick(rocket) }
        }
    }
}
