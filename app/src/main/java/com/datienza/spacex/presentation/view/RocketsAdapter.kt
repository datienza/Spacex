package com.datienza.spacex.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.datienza.spacex.domain.model.Rocket
import com.test.emptyapplication.R
import com.test.emptyapplication.databinding.ItemRocketBinding

class RocketsAdapter constructor(
    private val itemClickListener: (rocket: Rocket) -> Unit):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Rocket>()

    fun setItems(items: List<Rocket>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ItemRocketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RocketViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val blog = items[position]
        val blogViewHolder = holder as RocketViewHolder
        blogViewHolder.bind(blog)
    }

    override fun getItemCount(): Int = items.size

    private inner class RocketViewHolder(private val binding: ItemRocketBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(rocket: Rocket) {
            val context = itemView.context
            Glide.with(itemView).load(rocket.image)
                .apply(RequestOptions().centerCrop())
                .into(binding.rocketIv)
            binding.rocketNameTv.text = rocket.name
            binding.rocketCountryTv.text =
                context.getString(R.string.rocket_country, rocket.country)
            binding.rocketNumEnginesTv.text =
                context.getString(R.string.rocket_num_engines, rocket.numEngines)
            itemView.setOnClickListener { itemClickListener(rocket) }
        }
    }

}