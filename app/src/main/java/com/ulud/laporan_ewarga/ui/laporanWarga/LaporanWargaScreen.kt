package com.ulud.laporan_ewarga.ui.laporanWarga

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.ulud.laporan_ewarga.R
import com.ulud.laporan_ewarga.ui.components.LeadingIconType
import com.ulud.laporan_ewarga.ui.components.TopBarLaporan
import com.ulud.laporan_ewarga.ui.components.TrailingIconType
import com.ulud.laporan_ewarga.ui.laporanWarga.tabs.laporanKuTab.LaporanKuTab
import com.ulud.laporan_ewarga.ui.laporanWarga.tabs.laporanTab.LaporanTab
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LaporanWargaScreen(onMenu: () -> Unit = {}, onCreateLaporan: () -> Unit = {}) {

    Scaffold(
        topBar = {
            TopBarLaporan(
                title = "Laporan Warga",
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                showLeadingIconBorder = true,
                showTrailingIconBorder = true,
                leadingIconType = LeadingIconType.MENU,
                trailingIconType = TrailingIconType.ADD,
                onLeadingIconClick = onMenu,
                onTrailingIconClick = onCreateLaporan
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            val tabs = listOf("Laporan", "LaporanKu")
            val pagerState = rememberPagerState(initialPage = 0) { tabs.size }
            val coroutineScope = rememberCoroutineScope()

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                divider = {
                    HorizontalDivider(color = colorResource(R.color.disabled), thickness = 2.dp)
                },
                contentColor = colorResource(R.color.textPrimary),
                containerColor = colorResource(R.color.white),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        text = { Text(title) }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { page ->
                when (page) {
                    0 -> LaporanTab()
                    1 -> LaporanKuTab()
                }
            }
        }
    }
}

