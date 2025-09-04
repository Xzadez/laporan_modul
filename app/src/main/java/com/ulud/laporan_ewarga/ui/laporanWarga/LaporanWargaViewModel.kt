package com.ulud.laporan_ewarga.ui.laporanWarga

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulud.laporan_ewarga.data.repository.UserPreferencesRepository
import com.ulud.laporan_ewarga.domain.model.Laporan
import com.ulud.laporan_ewarga.domain.useCase.AddLaporanUseCase
import com.ulud.laporan_ewarga.domain.useCase.GetMyLaporanUseCase
import com.ulud.laporan_ewarga.domain.useCase.GetPublicLaporanUseCase
import com.ulud.laporan_ewarga.ui.laporanWarga.tabs.TabType
import com.ulud.laporan_ewarga.ui.theme.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaporanWargaViewModel @Inject constructor(
    getPublicLaporanUseCase: GetPublicLaporanUseCase,
    getMyLaporanUseCase: GetMyLaporanUseCase,
    private val userPreferencesRepository: UserPreferencesRepository,
    private val addLaporanUseCase: AddLaporanUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LaporanWargaState())
    val uiState: StateFlow<LaporanWargaState> = _uiState.asStateFlow()

    private val _publicLaporan = getPublicLaporanUseCase()
    private val _myLaporan = getMyLaporanUseCase()

    val userRole: StateFlow<UserRole> = userPreferencesRepository.userRoleFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserRole.WARGA)

    fun toggleUserRole() {
        viewModelScope.launch {
            val newRole = if (userRole.value == UserRole.WARGA) {
                UserRole.PENGURUS
            } else {
                UserRole.WARGA
            }
            userPreferencesRepository.saveUserRole(newRole)
        }
    }

    val filteredPublicLaporan: StateFlow<List<Laporan>> = combine(
        _publicLaporan,
        _uiState
    ) { list, state ->
        val tabState = state.laporanTab
        list.filter {
            it.title.contains(tabState.searchText, ignoreCase = true)
        }.filter {
            tabState.selectedStatus == null || it.status == tabState.selectedStatus
        }.let {
            when (tabState.selectedSort) {
                "Terbaru" -> it.sortedByDescending { l -> l.createdAt }
                "Terlama" -> it.sortedBy { l -> l.createdAt }
                else -> it
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val filteredMyLaporan: StateFlow<List<Laporan>> = combine(
        _myLaporan,
        _uiState
    ) { list, state ->
        val tabState = state.laporanKuTab
        list.filter {
            it.title.contains(tabState.searchText, ignoreCase = true)
        }.filter {
            tabState.selectedStatus == null ||
                    (if (tabState.selectedStatus == "Draft") it.isDraft else it.status == tabState.selectedStatus)
        }.let {
            when (tabState.selectedSort) {
                "Terbaru" -> it.sortedByDescending { l -> l.createdAt }
                "Terlama" -> it.sortedBy { l -> l.createdAt }
                else -> it
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onSearchChanged(text: String, tabType: TabType) {
        _uiState.update { currentState ->
            when (tabType) {
                TabType.LAPORAN_PUBLIK -> currentState.copy(laporanTab = currentState.laporanTab.copy(searchText = text))
                TabType.LAPORAN_KU -> currentState.copy(laporanKuTab = currentState.laporanKuTab.copy(searchText = text))
            }
        }
    }

    fun onStatusSelected(status: String?, tabType: TabType) {
        _uiState.update { currentState ->
            when (tabType) {
                TabType.LAPORAN_PUBLIK -> currentState.copy(laporanTab = currentState.laporanTab.copy(selectedStatus = status, isStatusSheetVisible = false))
                TabType.LAPORAN_KU -> currentState.copy(laporanKuTab = currentState.laporanKuTab.copy(selectedStatus = status, isStatusSheetVisible = false))
            }
        }
    }

    fun onSortSelected(sort: String?, tabType: TabType) {
        _uiState.update { currentState ->
            when (tabType) {
                TabType.LAPORAN_PUBLIK -> currentState.copy(laporanTab = currentState.laporanTab.copy(selectedSort = sort, isSortSheetVisible = false))
                TabType.LAPORAN_KU -> currentState.copy(laporanKuTab = currentState.laporanKuTab.copy(selectedSort = sort, isSortSheetVisible = false))
            }
        }
    }

    fun onClearFilters(tabType: TabType) {
        _uiState.update { currentState ->
            when (tabType) {
                TabType.LAPORAN_PUBLIK -> currentState.copy(laporanTab = currentState.laporanTab.copy(selectedStatus = null, selectedSort = null))
                TabType.LAPORAN_KU -> currentState.copy(laporanKuTab = currentState.laporanKuTab.copy(selectedStatus = null, selectedSort = null))
            }
        }
    }

    fun onFilterClick(isStatus: Boolean, tabType: TabType) {
        _uiState.update { currentState ->
            when (tabType) {
                TabType.LAPORAN_PUBLIK -> currentState.copy(laporanTab = currentState.laporanTab.copy(
                    isStatusSheetVisible = isStatus,
                    isSortSheetVisible = !isStatus
                ))
                TabType.LAPORAN_KU -> currentState.copy(laporanKuTab = currentState.laporanKuTab.copy(
                    isStatusSheetVisible = isStatus,
                    isSortSheetVisible = !isStatus
                ))
            }
        }
    }

    fun onDismissBottomSheet(tabType: TabType) {
        _uiState.update { currentState ->
            when (tabType) {
                TabType.LAPORAN_PUBLIK -> currentState.copy(laporanTab = currentState.laporanTab.copy(isStatusSheetVisible = false, isSortSheetVisible = false))
                TabType.LAPORAN_KU -> currentState.copy(laporanKuTab = currentState.laporanKuTab.copy(isStatusSheetVisible = false, isSortSheetVisible = false))
            }
        }
    }
}