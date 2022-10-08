package com.lnight.foodProjectV2.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [DbMealsRepositoryModule::class, MealsRepositoryModule::class, RetrofitModule::class, RoomModule::class, UseCaseWrapperModule::class])
@InstallIn(SingletonComponent::class)
object AppModule