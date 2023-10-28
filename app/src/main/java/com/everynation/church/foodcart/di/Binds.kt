package com.everynation.church.foodcart.di

import com.everynation.church.foodcart.data.FoodRepositoryImp
import com.everynation.church.foodcart.domain.FoodRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class Binds {

    @Binds
    abstract fun bindFoodRepository(foodRepositoryImp: FoodRepositoryImp): FoodRepository
}