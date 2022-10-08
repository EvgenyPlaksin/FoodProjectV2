package com.lnight.foodProjectV2.di

import com.lnight.foodProjectV2.data.repository.local.DbMealsRepositoryImpl
import com.lnight.foodProjectV2.domain.repository.local.DbMealsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DbMealsRepositoryModule {

    @Singleton
    @Binds
    fun bindDbMealsRepository(
        repositoryImpl: DbMealsRepositoryImpl
    ) : DbMealsRepository

}