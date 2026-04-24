package com.datienza.spacex.feature.rockets.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.datienza.spacex.core.model.Rocket
import com.datienza.spacex.feature.rockets.R

@Composable
fun RocketsRoute(
    viewModel: RocketsViewModel = hiltViewModel(),
    onNavigateToRocketInfo: (rocketId: String, rocketDescription: String) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.navigationIntent.collect { intent ->
            when (intent) {
                is RocketsViewModel.NavIntent.GoToRocketInfo ->
                    onNavigateToRocketInfo(intent.rocketId, intent.rocketDescription)
            }
        }
    }

    RocketsScreen(state = state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RocketsScreen(
    state: RocketsViewModel.State,
    onEvent: (RocketsViewModel.Event) -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("SpaceX") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            // Filter row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.rockets_list),
                    style = MaterialTheme.typography.titleSmall,
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.active_rockets),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Spacer(Modifier.width(8.dp))
                    Switch(
                        checked = state.activeOnly,
                        onCheckedChange = { onEvent(RocketsViewModel.Event.FilterByActive(it)) },
                        enabled = state.rockets.isNotEmpty(),
                    )
                }
            }

            HorizontalDivider()

            // Content
            PullToRefreshBox(
                isRefreshing = state.isLoading,
                onRefresh = { onEvent(RocketsViewModel.Event.LoadRockets) },
                modifier = Modifier.fillMaxSize(),
            ) {
                when {
                    state.isLoading && state.rockets.isEmpty() -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    state.isError -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = stringResource(R.string.no_rockets_found),
                                style = MaterialTheme.typography.titleMedium,
                            )
                        }
                    }
                    else -> {
                        LazyColumn(
                            state = rememberLazyListState(),
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            items(state.rockets, key = { it.id }) { rocket ->
                                RocketItem(
                                    rocket = rocket,
                                    onClick = { onEvent(RocketsViewModel.Event.RocketClicked(rocket)) },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RocketItem(rocket: Rocket, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
    ) {
        AsyncImage(
            model = rocket.image,
            contentDescription = rocket.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(MaterialTheme.shapes.small),
        )
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                text = rocket.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(Modifier.height(4.dp))
            Text(text = "Country: ${rocket.country}")
            Spacer(Modifier.height(4.dp))
            Text(text = "Engines: ${rocket.numEngines}")
        }
    }
}
