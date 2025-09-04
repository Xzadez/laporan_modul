package com.ulud.laporan_ewarga.di

import android.content.Context
import com.ulud.laporan_ewarga.data.local.LaporanLocalDataSource
import com.ulud.laporan_ewarga.data.repository.ImageRepositoryImpl
import com.ulud.laporan_ewarga.data.repository.LaporanRepositoryImpl
import com.ulud.laporan_ewarga.domain.repository.ImageRepository
import com.ulud.laporan_ewarga.domain.repository.LaporanRepository
import com.ulud.laporan_ewarga.domain.useCase.AddLaporanUseCase
import com.ulud.laporan_ewarga.domain.useCase.GetMyLaporanUseCase
import com.ulud.laporan_ewarga.domain.useCase.GetPublicLaporanUseCase
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

    // --- Repository ---

    @Provides
    @Singleton
    fun provideImageRepository(@ApplicationContext context: Context): ImageRepository {
        return ImageRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideLaporanLocalDataSource(): LaporanLocalDataSource {
        return LaporanLocalDataSource()
    }

    @Provides
    @Singleton
    fun provideLaporanRepository(localDataSource: LaporanLocalDataSource): LaporanRepository {
        return LaporanRepositoryImpl(localDataSource)
    }

    // --- Use Cases ---

    @Provides
    @Singleton
    fun provideValidateAttachmentCountUseCase(): ValidateAttachmentCountUseCase {
        return ValidateAttachmentCountUseCase()
    }

    @Provides
    @Singleton
    fun provideGetPublicLaporanUseCase(repository: LaporanRepository): GetPublicLaporanUseCase {
        return GetPublicLaporanUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetMyLaporanUseCase(repository: LaporanRepository): GetMyLaporanUseCase {
        return GetMyLaporanUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAddLaporanUseCase(repository: LaporanRepository): AddLaporanUseCase {
        return AddLaporanUseCase(repository)
    }
}