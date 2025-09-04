package com.ulud.laporan_ewarga.di

import android.content.Context
import com.ulud.laporan_ewarga.data.repository.ImageRepositoryImpl
import com.ulud.laporan_ewarga.domain.repository.ImageRepository
import com.ulud.laporan_ewarga.domain.useCase.ValidateAttachmentCountUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideImageRepository(@ApplicationContext context: Context): ImageRepository {
        return ImageRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideValidateAttachmentCountUseCase(): ValidateAttachmentCountUseCase {
        return ValidateAttachmentCountUseCase()
    }
}