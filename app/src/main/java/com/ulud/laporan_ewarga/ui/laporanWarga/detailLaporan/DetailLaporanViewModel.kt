package com.ulud.laporan_ewarga.ui.laporanWarga.detailLaporan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulud.laporan_ewarga.data.repository.UserPreferencesRepository
import com.ulud.laporan_ewarga.domain.model.Laporan
import com.ulud.laporan_ewarga.domain.useCase.DeleteLaporanUseCase
import com.ulud.laporan_ewarga.domain.useCase.UpdateLaporanUseCase
import com.ulud.laporan_ewarga.ui.theme.UserRole
import com.ulud.laporan_ewarga.utils.FormattedDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailLaporanViewModel @Inject constructor(
    private val updateLaporanUseCase: UpdateLaporanUseCase,
    private val deleteLaporanUseCase: DeleteLaporanUseCase,
    userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(DetailLaporanState())
    val state = _state.asStateFlow()

    val userRole: StateFlow<UserRole?> = userPreferencesRepository.userRoleFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun initialize(laporan: Laporan, isPreview: Boolean, canShowMenu: Boolean, userRole: UserRole) {
        _state.update {
            val dateText = when {
                isPreview -> "Preview"
                laporan.status.equals("Draft", ignoreCase = true) -> "Draft"
                else -> FormattedDate(laporan.createdAt)
            }

            it.copy(
                laporan = laporan,
                isPreview = isPreview,
                canShowMenu = canShowMenu,
                dateText = dateText,
            )
        }
    }

    fun onImageClicked(index: Int) {
        _state.update { it.copy(showImageDialog = true, selectedImageIndex = index) }
    }

    fun onDismissImageDialog() {
        _state.update { it.copy(showImageDialog = false) }
    }

    fun onNextImage() {
        _state.update {
            val newIndex = (it.selectedImageIndex + 1) % (it.laporan?.photos?.size ?: 1)
            it.copy(selectedImageIndex = newIndex)
        }
    }

    fun onPreviousImage() {
        _state.update {
            val newIndex = (it.selectedImageIndex - 1 + (it.laporan?.photos?.size
                ?: 1)) % (it.laporan?.photos?.size ?: 1)
            it.copy(selectedImageIndex = newIndex)
        }
    }

    fun onResponLaporan() {
        viewModelScope.launch {
            _state.value.laporan?.let {
                val updatedLaporan = it.copy(status = "Proses")
                updateLaporanUseCase(updatedLaporan)
                _state.update { currentState ->
                    currentState.copy(laporan = updatedLaporan)
                }
            }
        }
    }


    fun onDelete() {
        viewModelScope.launch {
            _state.value.laporan?.let {
                deleteLaporanUseCase(it)
            }
        }
    }

    fun onSubmitDraft() {
        viewModelScope.launch {
            _state.value.laporan?.let {
                val updatedLaporan = it.copy(isDraft = false, status = "Antrian")
                updateLaporanUseCase(updatedLaporan)
            }
        }
    }
}