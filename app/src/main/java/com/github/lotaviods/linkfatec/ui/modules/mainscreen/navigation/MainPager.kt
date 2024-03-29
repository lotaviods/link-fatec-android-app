@file:OptIn(ExperimentalFoundationApi::class)

package com.github.lotaviods.linkfatec.ui.modules.mainscreen.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.github.lotaviods.linkfatec.ui.modules.appliedoffers.AppliedOffersScreen
import com.github.lotaviods.linkfatec.ui.modules.appliedoffers.viewmodel.AppliedOffersViewModel
import com.github.lotaviods.linkfatec.ui.modules.notifications.AppNotificationScreen
import com.github.lotaviods.linkfatec.ui.modules.notifications.viewmodel.AppNotificationsViewModel
import com.github.lotaviods.linkfatec.ui.modules.opportunities.OpportunitiesScreen
import com.github.lotaviods.linkfatec.ui.modules.opportunities.viewmodel.OpportunitiesViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import org.koin.androidx.compose.koinViewModel

@OptIn(FlowPreview::class)
@Composable
fun MainPager(
    page: Int,
    pagerState: PagerState,
) {
    val opportunitiesViewModel: OpportunitiesViewModel = koinViewModel()
    val appliedOffersViewModel: AppliedOffersViewModel = koinViewModel()
    val appNotificationsViewModel: AppNotificationsViewModel = koinViewModel()
    var currentPage by remember { mutableStateOf(-1) }

    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .debounce(200)
            .drop(1) // Skip the initial emission
            .collectLatest { offsetPage ->
                when (offsetPage) {
                    0 -> {
                        if (currentPage == 0) return@collectLatest

                        opportunitiesViewModel.getAvailableJobs()
                        currentPage = 0
                    }

                    1 -> {
                        if (currentPage == 1) return@collectLatest
                        appliedOffersViewModel.reloadOrLoadAppliedJob()
                        currentPage = 1
                    }

                    2 -> {
                        if (currentPage == 2) return@collectLatest
                        appNotificationsViewModel.loadNotifications()
                        currentPage = 2
                    }
                }
            }
    }
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        when (page) {
            0 -> {
                OpportunitiesScreen(
                    Modifier.background(Color(0xFFE6E6E6)),
                    opportunitiesViewModel,
                    pagerState
                )
            }

            1 -> {
                AppliedOffersScreen(
                    Modifier.background(Color(0xFFE6E6E6)),
                    appliedOffersViewModel
                )
            }

            2 -> {
                AppNotificationScreen(
                    Modifier.background(Color(0xFFE6E6E6)),
                    appNotificationsViewModel
                )
            }
        }
    }
}

private const val TAG = "MainPager"