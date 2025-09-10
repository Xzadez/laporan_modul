package com.ulud.laporan_ewarga.ui.laporanWarga.responLaporan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulud.laporan_ewarga.data.repository.UserPreferencesRepository
import com.ulud.laporan_ewarga.domain.repository.ImageRepository
import com.ulud.laporan_ewarga.domain.useCase.ValidateAttachmentCountUseCase
import com.ulud.laporan_ewarga.ui.theme.UserRole
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class ResponLaporanViewModel @Inject constructor(
    private val validateAttachmentCountUseCase: ValidateAttachmentCountUseCase,
    private val imageRepository: ImageRepository,
    userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {
    val userRole: StateFlow<UserRole?> = userPreferencesRepository.userRoleFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
}
