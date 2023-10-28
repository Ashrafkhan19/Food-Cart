package com.everynation.church.foodcart.data

import android.content.Context
import com.everynation.church.foodcart.data.local.CategoryDao
import com.everynation.church.foodcart.data.local.FoodDatabase
import com.everynation.church.foodcart.data.local.FoodItemDao
import com.everynation.church.foodcart.data.local.FoodItemEntity
import com.everynation.church.foodcart.data.local.IsFirstTime
import com.everynation.church.foodcart.data.local.IsFirstTimeDao
import com.everynation.church.foodcart.domain.FoodRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FoodRepositoryImp @Inject constructor(
    @ApplicationContext context: Context,
    private val categoryDao: CategoryDao,
    private val foodItemDao: FoodItemDao,
    private val isFirstTimeDao: IsFirstTimeDao,
): FoodRepository {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            if (isFirstTimeDao.getIsFirstTime()?.isFirstTime == null) {
                isFirstTimeDao.insert(IsFirstTime(isFirstTime = true))
                FoodDatabase.populateDatabase(categoryDao)
            }
        }

    }

    override fun getAllItemsOnCart(): Flow<List<FoodItemEntity>> {
        return categoryDao.getAllItemsOnCart()
    }

    override fun getAllFoodsByCategory(): Flow<List<FoodItemEntity>> {
        return categoryDao.getAllItems()



    }

    override fun getAllItemsWithFav(): Flow<List<FoodItemEntity>> {
       return categoryDao.getAllItemsWithFav()
    }

    override suspend fun updateFoodItem(foodItem: FoodItemEntity) {
        categoryDao.updateItems(foodItem)
    }
}