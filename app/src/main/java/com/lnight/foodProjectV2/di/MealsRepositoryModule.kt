package com.lnight.foodProjectV2.di

import com.lnight.foodProjectV2.domain.repository.remote.MealsRepository
import com.lnight.foodProjectV2.data.repository.remote.MealsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface MealsRepositoryModule {

    @Singleton
    @Binds
    fun bindMealsRepository(
        repositoryImpl: MealsRepositoryImpl
    ) : MealsRepository

}