package com.ulud.laporan_ewarga.ui.laporanWarga.lampiranLaporan

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulud.laporan_ewarga.data.repository.UserPreferencesRepository
import com.ulud.laporan_ewarga.domain.repository.ImageRepository
import com.ulud.laporan_ewarga.domain.useCase.ValidateAttachmentCountUseCase
import com.ulud.laporan_ewarga.ui.theme.UserRole
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
class LampiranLaporanViewModel @Inject constructor(
    private val validateAttachmentCountUseCase: ValidateAttachmentCountUseCase,
    private val imageRepository: ImageRepository,
    userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LampiranLaporanState())
    val state: StateFlow<LampiranLaporanState> = _state.asStateFlow()

    val userRole: StateFlow<UserRole?> = userPreferencesRepository.userRoleFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)


    fun initialize(initialPhotos: List<String>) {
        _state.update { it.copy(photos = initialPhotos) }
    }

    fun onPhotoCamera(uri: Uri) {
        if (validateAttachmentCountUseCase(state.value.photos)) {
            val updatedPhotos = state.value.photos + uri.toString()
            _state.update { it.copy(photos = updatedPhotos) }
        } else {
            _state.update { it.copy(errorMessage = "Maksimal 5 foto tercapai") }
        }
    }

    fun onPhotoGallery(uri: Uri) {
        if (validateAttachmentCountUseCase(state.value.photos)) {
            viewModelScope.launch {
                val internalUri = imageRepository.copyImageToInternalStorage(uri)
                if (internalUri != null) {
                    val updatedPhotos = state.value.photos + internalUri.toString()
                    _state.update { it.copy(photos = updatedPhotos) }
                } else {
                    _state.update { it.copy(errorMessage = "Gagal menyimpan gambar dari galeri") }
                }
            }
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
        if (validateAttachmentCountUseCase(state.value.photos)) {
            _state.update { it.copy(showBottomSheet = true) }
        } else {
            _state.update { it.copy(errorMessage = "Maksimal 5 foto tercapai") }
        }
    }

    fun onDismissBottomSheet() {
        _state.update { it.copy(showBottomSheet = false) }
    }

    fun errorMessageShown() {
        _state.update { it.copy(errorMessage = null) }
    }
}