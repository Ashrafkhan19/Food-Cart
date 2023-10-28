package com.everynation.church.foodcart.data

import androidx.compose.runtime.Stable
import com.google.gson.annotations.SerializedName


@Stable
data class ShoppingDataModel(
    @SerializedName("categories")
    val categories: List<Category> = listOf(),
    @SerializedName("error")
    val error: String? = null, // null
    @SerializedName("message")
    val message: String = "", // Product Categories
    @SerializedName("status")
    val status: Boolean = true // true
) {
    data class Category(
        @SerializedName("id")
        val id: Int = 0, // 55
        @SerializedName("items")
        val items: List<FoodItem> = listOf(),
        @SerializedName("name")
        val name: String = "", // Food
    ) {
        data class FoodItem(
            @SerializedName("icon")
            val icon: String = "", // https://cdn-icons-png.flaticon.com/128/2553/2553691.png
            @SerializedName("id")
            val id: Int = 0, // 5501
            @SerializedName("name")
            val name: String = "", // Potato Chips
            @SerializedName("price")
            val price: Double = 0.0 // 40.00
        )
    }
}