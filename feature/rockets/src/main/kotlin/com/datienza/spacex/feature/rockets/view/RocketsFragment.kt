package com.datienza.spacex.feature.rockets.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.datienza.spacex.core.common.router.MainActivityRouter
import com.datienza.spacex.feature.rockets.databinding.FragmentRocketsBinding
import com.datienza.spacex.feature.rockets.viewmodel.RocketsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RocketsFragment : Fragment() {

    private companion object {
        const val LOADING_STATE = 0
        const val CONTENT_STATE = 1
        const val ERROR_STATE   = 2
    }

    @Inject lateinit var router: MainActivityRouter

    private var _binding: FragmentRocketsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: RocketsViewModel by viewModels()
    private lateinit var adapter: RocketsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRocketsBinding.inflate(inflater, container, false)
        setupRecyclerView(binding.root.context)
        setupToolbar()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        binding.switchItem.setOnCheckedChangeListener { _, checked ->
            viewModel.filterData(checked)
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getData()
            binding.swipeRefreshLayout.isRefreshing = false
            binding.switchItem.isChecked = false
        }
        viewModel.getData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        binding.toolbar.title = getString(R.string.fragment_rockets_title)
    }

    private fun setupRecyclerView(context: Context) {
        adapter = RocketsAdapter { rocket ->
            router.goToRocketInfo(rocket.id, rocket.description)
        }
        val layoutManager = LinearLayoutManager(context)
        binding.rocketsList.layoutManager = layoutManager
        binding.rocketsList.addItemDecoration(
            DividerItemDecoration(context, layoutManager.orientation)
        )
        binding.rocketsList.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            binding.rocketsVf.displayedChild = when (state) {
                RocketsViewModel.State.Loading -> LOADING_STATE
                is RocketsViewModel.State.Content -> {
                    binding.switchItem.isEnabled = true
                    adapter.setItems(state.rockets)
                    CONTENT_STATE
                }
                RocketsViewModel.State.Error -> {
                    binding.switchItem.isEnabled = false
                    ERROR_STATE
                }
            }
        }
    }
}
