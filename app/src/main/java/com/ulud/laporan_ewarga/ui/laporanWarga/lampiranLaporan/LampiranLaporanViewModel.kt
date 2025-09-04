package com.ulud.laporan_ewarga.ui.laporanWarga.lampiranLaporan

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.ulud.laporan_ewarga.domain.repository.ImageRepository
import com.ulud.laporan_ewarga.domain.useCase.ValidateAttachmentCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LampiranLaporanViewModel @Inject constructor(
    private val validateAttachmentCountUseCase: ValidateAttachmentCountUseCase,
    private val imageRepository: ImageRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(LampiranLaporanState())
    val state: StateFlow<LampiranLaporanState> = _state.asStateFlow()

    fun onAddPhoto(uriString: String) {
        if (validateAttachmentCountUseCase(state.value.photos)) {
            val updatedPhotos = state.value.photos + uriString
            _state.update { it.copy(photos = updatedPhotos) }
        } else {
            _state.update { it.copy(errorMessage = "Maksimal 5 foto tercapai") }
        }
    }

    fun onDeletePhoto(uriString: String) {
        val updatedPhotos = state.value.photos - uriString
        _state.update { it.copy(photos = updatedPhotos) }
    }

    fun createTemporaryImageUri(): Uri? {
        return imageRepository.createTemporaryImageUri()
    }

    fun onAddImageClick() {
        _state.update { it.copy(showBottomSheet = true) }
    }

    fun onDismissBottomSheet() {
        _state.update { it.copy(showBottomSheet = false) }
    }
}

data class LampiranLaporanState(
    val photos: List<String> = emptyList(),
    val showBottomSheet: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)