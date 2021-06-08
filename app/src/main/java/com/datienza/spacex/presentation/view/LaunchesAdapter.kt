package com.datienza.spacex.presentation.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.datienza.spacex.domain.model.Launch
import com.datienza.spacex.presentation.model.LaunchItemContent
import com.datienza.spacex.presentation.model.TYPE_LAUNCH_ITEM_HEADER
import com.test.emptyapplication.R
import com.test.emptyapplication.databinding.ItemLaunchBinding
import com.test.emptyapplication.databinding.ItemYearHeaderBinding
import java.text.SimpleDateFormat
import java.util.*

class LaunchesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<LaunchItemContent>()

    fun setItems(items: List<LaunchItemContent>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return if (viewType == TYPE_LAUNCH_ITEM_HEADER) {
            val binding = ItemYearHeaderBinding.inflate(layoutInflater, parent, false)
            HeaderViewHolder(binding)
        } else {
            val binding = ItemLaunchBinding.inflate(layoutInflater, parent, false)
            LaunchViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        if (item.type == TYPE_LAUNCH_ITEM_HEADER) {
            val headerItem = item as LaunchItemContent.Header
            (holder as HeaderViewHolder).bind(headerItem.launchYear)
        } else {
            val contentItem = item as LaunchItemContent.Item
            (holder as LaunchViewHolder).bind(contentItem.launch)
        }
    }

    override fun getItemCount(): Int = items.size

    private inner class LaunchViewHolder(private val binding: ItemLaunchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(launch: Launch) {
            val context = itemView.context
            binding.launchMissionNameTv.text = launch.missionName

            Glide.with(itemView).load(launch.mission_patch)
                .apply(RequestOptions().centerCrop())
                .into(binding.launchMissionPatchIv)

            binding.launchSuccessTv.text = if (launch.launchSuccess) {
                context.getString(R.string.launch_successful)
            } else {
                context.getString(R.string.launch_failed)
            }


            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            binding.launchDateTv.text = dateFormat.format(launch.launchDate)
        }
    }

    class HeaderViewHolder(private val binding: ItemYearHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(launchYear: Int) {
            binding.launchYearHeaderTv.text = launchYear.toString()
        }
    }

}