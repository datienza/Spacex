package com.datienza.spacex.feature.rocketinfo.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.datienza.spacex.core.model.Launch
import com.datienza.spacex.feature.rocketinfo.databinding.ItemLaunchBinding
import com.datienza.spacex.feature.rocketinfo.databinding.ItemYearHeaderBinding
import com.datienza.spacex.feature.rocketinfo.model.LaunchItemContent
import com.datienza.spacex.feature.rocketinfo.model.TYPE_LAUNCH_ITEM_HEADER
import java.text.SimpleDateFormat
import java.util.Locale

class LaunchesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<LaunchItemContent>()

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newItems: List<LaunchItemContent>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int = items[position].type

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_LAUNCH_ITEM_HEADER) {
            HeaderViewHolder(ItemYearHeaderBinding.inflate(inflater, parent, false))
        } else {
            LaunchViewHolder(ItemLaunchBinding.inflate(inflater, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is LaunchItemContent.Header -> (holder as HeaderViewHolder).bind(item.launchYear)
            is LaunchItemContent.Item   -> (holder as LaunchViewHolder).bind(item.launch)
        }
    }

    override fun getItemCount(): Int = items.size

    class HeaderViewHolder(private val binding: ItemYearHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(year: Int) { binding.launchYearHeaderTv.text = year.toString() }
    }

    inner class LaunchViewHolder(private val binding: ItemLaunchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(launch: Launch) {
            val ctx = itemView.context
            binding.launchMissionNameTv.text = launch.missionName
            Glide.with(itemView)
                .load(launch.missionPatch)
                .apply(RequestOptions().centerCrop())
                .into(binding.launchMissionPatchIv)
            binding.launchSuccessTv.text = if (launch.launchSuccess) {
                ctx.getString(R.string.launch_successful)
            } else {
                ctx.getString(R.string.launch_failed)
            }
            binding.launchDateTv.text =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(launch.launchDate)
        }
    }
}
