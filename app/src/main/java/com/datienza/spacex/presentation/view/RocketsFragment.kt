package com.datienza.spacex.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.datienza.spacex.presentation.router.MainActivityRouter
import com.datienza.spacex.presentation.viewmodel.RocketsViewModel
import com.datienza.spacex.presentation.viewmodel.RocketsViewModel.State
import com.datienza.spacex.presentation.viewmodel.RocketsViewModel.State.Content
import com.datienza.spacex.presentation.viewmodel.RocketsViewModel.State.Loading
import com.test.emptyapplication.R
import com.test.emptyapplication.databinding.FragmentRocketsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_rockets.*
import javax.inject.Inject

@AndroidEntryPoint
class RocketsFragment: Fragment() {

    private companion object {
        const val LOADING_STATE = 0
        const val CONTENT_STATE = 1
        const val ERROR_STATE = 2
    }

    @Inject
    lateinit var router: MainActivityRouter

    lateinit var binding: FragmentRocketsBinding

    private val viewModel: RocketsViewModel by viewModels()

    private lateinit var adapter: RocketsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRocketsBinding.inflate(inflater, container, false)
        setupRecyclerView(binding.root.context)
        setupToolbar()
        observeViewModel(viewLifecycleOwner, binding.root)
        return binding.root
    }

    private fun setupToolbar() {
        binding.toolbar.title = context?.getString(R.string.fragment_rockets_title)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.switchItem.setOnCheckedChangeListener { _, isChecked ->
            viewModel.filterData(isChecked)
        }
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getData()
            binding.swipeRefreshLayout.isRefreshing = false
            binding.switchItem.isChecked = false
        }
        viewModel.getData()
    }

    private fun setupRecyclerView(context: Context) {
        adapter = RocketsAdapter { rocket ->
            router.goToRocketInfo(rocket.id, rocket.description)
        }
        binding.rocketsList.adapter = adapter
        val layoutManager = LinearLayoutManager(context)
        binding.rocketsList.addItemDecoration(DividerItemDecoration(context, layoutManager.orientation))
        binding.rocketsList.layoutManager = LinearLayoutManager(context)

    }

    private fun observeViewModel(lifecycleOwner: LifecycleOwner, view: View) {
        viewModel.viewState.observe(lifecycleOwner, {
            handleViewState(it, view)
        })
    }

    private fun handleViewState(viewState: State, view: View) {
        binding.rocketsVf.displayedChild =
            when (viewState) {
                Loading -> {
                    LOADING_STATE
                }
                is Content -> {
                    switchItem.isEnabled = true
                    adapter.setItems(viewState.rockets)
                    CONTENT_STATE
                }
                else -> {
                    switchItem.isEnabled = false
                    ERROR_STATE
                }
            }
    }
}