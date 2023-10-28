package com.everynation.church.foodcart.data.local

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity
data class FoodItemEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val icon: String,
    val price: Double,
    val categoryName: String,
    val categoryId: Int, // Foreign key to link items with categories,
    val isFav: Boolean = false,
    val isOnCart: Boolean = false
)

@Entity
data class IsFirstTime(
    @PrimaryKey
    val id: Int? = null,
    val isFirstTime: Boolean? = false
)

