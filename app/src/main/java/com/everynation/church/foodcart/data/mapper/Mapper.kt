package com.everynation.church.foodcart.data.mapper

import com.everynation.church.foodcart.data.ShoppingDataModel
import com.everynation.church.foodcart.data.local.FoodItemEntity




fun ShoppingDataModel.Category.FoodItem.toEntity(categoryId: Int, categoryName: String): FoodItemEntity{
    return FoodItemEntity(
        id = id, name = name, icon = icon, price = price, categoryId = categoryId, categoryName = categoryName
    )
}



