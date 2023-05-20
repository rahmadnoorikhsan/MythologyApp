package com.ikhsan.compose.mythology.di

import com.ikhsan.compose.mythology.data.MythologyRepository
import com.ikhsan.compose.mythology.data.MythologyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun provideMythologyRepository(mythologyRepositoryImpl: MythologyRepositoryImpl) : MythologyRepository
}