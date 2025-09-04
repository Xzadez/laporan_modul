package com.ulud.laporan_ewarga.domain.useCase


class ValidateAttachmentCountUseCase {
    operator fun invoke(currentPhotos: List<String>): Boolean {
        return currentPhotos.size < 5
    }
}