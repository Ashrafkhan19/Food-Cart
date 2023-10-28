package com.everynation.church.foodcart.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.everynation.church.foodcart.data.local.FoodItemEntity
import com.everynation.church.foodcart.domain.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val foodRepository: FoodRepository
): ViewModel() {



    val data = foodRepository.getAllFoodsByCategory()
        .map { foodItemEntities -> foodItemEntities.groupBy { it.categoryName } }
        .stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyMap()
    )

    val favData = foodRepository.getAllItemsWithFav()
        .stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList()
        )

    val cartData = foodRepository.getAllItemsOnCart()
        .stateIn(
            viewModelScope, SharingStarted.WhileSubscribed(5000L), emptyList()
        )

    fun onFavClick( foodItem: FoodItemEntity) {

        viewModelScope.launch {
            val newFoodItem = foodItem.copy(isFav = !foodItem.isFav)
            foodRepository.updateFoodItem(newFoodItem)
        }
    }

    fun saveToCart(foodItem: FoodItemEntity) {
        viewModelScope.launch {
            val newFoodItem = foodItem.copy(isOnCart = !foodItem.isOnCart)
            foodRepository.updateFoodItem(newFoodItem)
        }
    }

    init {
        viewModelScope.launch {
            foodRepository.getAllFoodsByCategory().collectLatest {
                Log.i("TAG", ": $it ")
            }

        }
    }
}