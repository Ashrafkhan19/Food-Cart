package com.everynation.church.foodcart.domain


import com.everynation.church.foodcart.data.local.FoodItemEntity
import kotlinx.coroutines.flow.Flow

interface FoodRepository {

    fun getAllFoodsByCategory(): Flow<List<FoodItemEntity>>

    suspend fun updateFoodItem(foodItem: FoodItemEntity)
     fun getAllItemsWithFav(): Flow<List<FoodItemEntity>>
     fun getAllItemsOnCart(): Flow<List<FoodItemEntity>>
}