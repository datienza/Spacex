package com.datienza.spacex.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.datienza.spacex.domain.model.Launch
import com.datienza.spacex.presentation.router.NavigateBackRouter
import com.datienza.spacex.presentation.viewmodel.RocketInfoViewModel
import com.datienza.spacex.presentation.viewmodel.RocketInfoViewModel.State.*
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.appbar.AppBarLayout
import com.test.emptyapplication.R
import com.test.emptyapplication.databinding.FragmentRocketInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.abs


@AndroidEntryPoint
class RocketInfoFragment: Fragment() {

    private companion object {
        const val LOADING_STATE = 0
        const val CONTENT_STATE = 1
        const val ERROR_STATE = 2
    }

    @Inject
    lateinit var router: NavigateBackRouter

    private val args: RocketInfoFragmentArgs by navArgs()
    private val viewModel: RocketInfoViewModel by viewModels()

    val rocketId: String
        get() = args.rocketId

    val rocketDescription: String
        get() = args.rocketDescription

    lateinit var binding: FragmentRocketInfoBinding
    lateinit var adapter: LaunchesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRocketInfoBinding.inflate(inflater, container, false)
        setupRecyclerView(binding.root.context)
        setupToolbar()
        observeViewModel(viewLifecycleOwner, binding.root)
        return binding.root
    }

    private fun setupToolbar() {
        binding.toolbar.title = context?.getString(R.string.fragments_rocket_info_title)
        binding.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            binding.navGraphLl.alpha = 1.0f - abs(verticalOffset / binding.appBarLayout.totalScrollRange.toFloat())
        })
    }

    private fun setupRecyclerView(context: Context) {
        adapter = LaunchesAdapter()
        val layoutManager = LinearLayoutManager(context)
        binding.launchesList.layoutManager = LinearLayoutManager(context)
        binding.launchesList.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        binding.launchesList.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            router.goBack()
        }
        viewModel.getData(rocketId)
    }

    private fun observeViewModel(lifecycleOwner: LifecycleOwner, view: View) {
        viewModel.viewState.observe(lifecycleOwner, {
            handleViewState(it)
        })
    }

    private fun handleViewState(viewState: RocketInfoViewModel.State) {
        binding.rocketsVf.displayedChild =
            when (viewState) {
                Loading -> LOADING_STATE
                is Content -> {
                    setContent(viewState)
                    CONTENT_STATE
                }
                else -> ERROR_STATE
            }
    }

    private fun setContent(viewState: Content) {
        adapter.setItems(viewState.itemsList)
        setGraph(viewState.launchesPerYearMap)
        binding.rocketDescriptionTv.text = rocketDescription
    }

    private fun setGraph(launchesByYearMap: Map<Int, List<Launch>>){
        val entries = launchesByYearMap.map { Entry(it.key.toFloat(), it.value.size.toFloat()) }

        if (entries.isNotEmpty()) {
            val dataSet = LineDataSet(entries, requireContext().getString(R.string.launches_per_year))
            dataSet.color = ContextCompat.getColor(requireContext(), R.color.blue_700)
            dataSet.valueTextSize = 20f
            dataSet.lineWidth = 2f
            dataSet.setCircleColor(ContextCompat.getColor(requireContext(), R.color.blue_700))

            val xAxis = binding.rocketGraphIv.xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1f
            xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.blue_700)
            xAxis.textSize = 10f
            xAxis.setDrawAxisLine(true)
            xAxis.setDrawGridLines(false)
            xAxis.setDrawLabels(true)
            xAxis.position = XAxis.XAxisPosition.BOTTOM

            val legend: Legend = binding.rocketGraphIv.legend
            legend.form = Legend.LegendForm.CIRCLE
            legend.textSize = 11f

            val description = Description()
            description.isEnabled = false
            binding.rocketGraphIv.description = description

            val yAxisRight = binding.rocketGraphIv.axisRight
            yAxisRight.isEnabled = false

            val yAxisLeft = binding.rocketGraphIv.axisLeft
            yAxisLeft.granularity = 1f
            yAxisLeft.textSize = 10f
            yAxisLeft.setDrawGridLines(false)

            xAxis.valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    return value.toInt().toString()
                }
            }

            val data = LineData(dataSet)
            data.setDrawValues(false)
            binding.rocketGraphIv.data = data
            // refresh
            binding.rocketGraphIv.invalidate()
        }
    }
}