package com.datienza.spacex.feature.rocketinfo.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.datienza.spacex.core.common.router.NavigateBackRouter
import com.datienza.spacex.core.model.Launch
import com.datienza.spacex.feature.rocketinfo.databinding.FragmentRocketInfoBinding
import com.datienza.spacex.feature.rocketinfo.viewmodel.RocketInfoViewModel
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.abs

@AndroidEntryPoint
class RocketInfoFragment : Fragment() {

    private companion object {
        const val LOADING_STATE = 0
        const val CONTENT_STATE = 1
        const val ERROR_STATE   = 2
    }

    @Inject lateinit var router: NavigateBackRouter

    private val args: RocketInfoFragmentArgs by navArgs()
    private val viewModel: RocketInfoViewModel by viewModels()

    private var _binding: FragmentRocketInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: LaunchesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRocketInfoBinding.inflate(inflater, container, false)
        setupRecyclerView(binding.root.context)
        setupToolbar()
        observeViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { router.goBack() }
        viewModel.getData(args.rocketId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        binding.toolbar.title = getString(R.string.fragments_rocket_info_title)
        binding.appBarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { _, offset ->
                binding.navGraphLl.alpha =
                    1.0f - abs(offset / binding.appBarLayout.totalScrollRange.toFloat())
            }
        )
    }

    private fun setupRecyclerView(context: Context) {
        adapter = LaunchesAdapter()
        val lm = LinearLayoutManager(context)
        binding.launchesList.layoutManager = lm
        binding.launchesList.addItemDecoration(DividerItemDecoration(context, lm.orientation))
        binding.launchesList.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            binding.rocketsVf.displayedChild = when (state) {
                RocketInfoViewModel.State.Loading -> LOADING_STATE
                is RocketInfoViewModel.State.Content -> {
                    adapter.setItems(state.itemsList)
                    setGraph(state.launchesPerYearMap)
                    binding.rocketDescriptionTv.text = args.rocketDescription
                    CONTENT_STATE
                }
                RocketInfoViewModel.State.Error -> ERROR_STATE
            }
        }
    }

    private fun setGraph(launchesByYear: Map<Int, List<Launch>>) {
        val entries = launchesByYear.map { Entry(it.key.toFloat(), it.value.size.toFloat()) }
        if (entries.isEmpty()) return
        val dataSet = LineDataSet(entries, getString(R.string.launches_per_year)).apply {
            color     = ContextCompat.getColor(requireContext(), R.color.blue_700)
            lineWidth = 2f
            valueTextSize = 20f
            setCircleColor(ContextCompat.getColor(requireContext(), R.color.blue_700))
        }
        binding.rocketGraphIv.apply {
            xAxis.apply {
                position      = XAxis.XAxisPosition.BOTTOM
                granularity   = 1f
                textColor     = ContextCompat.getColor(requireContext(), R.color.blue_700)
                textSize      = 10f
                setDrawGridLines(false)
                valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float) = value.toInt().toString()
                }
            }
            axisRight.isEnabled = false
            axisLeft.apply { granularity = 1f; textSize = 10f; setDrawGridLines(false) }
            legend.apply { form = Legend.LegendForm.CIRCLE; textSize = 11f }
            description = Description().apply { isEnabled = false }
            data = LineData(dataSet).apply { setDrawValues(false) }
            invalidate()
        }
    }
}
