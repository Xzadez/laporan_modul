package com.ulud.laporan_ewarga.ui.laporanWarga.createLaporan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ulud.laporan_ewarga.data.repository.UserPreferencesRepository
import com.ulud.laporan_ewarga.domain.model.Laporan
import com.ulud.laporan_ewarga.domain.useCase.AddLaporanUseCase
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
class CreateLaporanViewModel @Inject constructor(
    private val addLaporanUseCase: AddLaporanUseCase,
    userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateLaporanUiState())
    val uiState = _uiState.asStateFlow()

    val userRole: StateFlow<UserRole?> = userPreferencesRepository.userRoleFlow
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)


    fun onTitleChange(newTitle: String) {
        _uiState.update { it.copy(title = newTitle) }
    }

    fun onDescriptionChange(newDescription: String) {
        _uiState.update { it.copy(description = newDescription) }
    }

    fun onCategoryChange(newCategory: String) {
        _uiState.update { it.copy(category = newCategory) }
    }

    fun onPhotosAttached(photoUris: List<String>) {
        _uiState.update { it.copy(photos = photoUris) }
    }

    fun saveLaporan(isDraft: Boolean) {
        viewModelScope.launch {
            val currentState = _uiState.value
            val newLaporan = Laporan(
                title = currentState.title,
                category = currentState.category,
                status = if (isDraft) "Draft" else "Antrian",
                photos = currentState.photos,
                description = currentState.description,
                isDraft = isDraft
            )
            addLaporanUseCase(newLaporan)
        }
    }
}