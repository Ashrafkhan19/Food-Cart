package com.everynation.church.foodcart.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromJson(json: String?): List<FoodItemEntity> {
        if (json == null) {
            return emptyList()
        }

        val type = object : TypeToken<List<FoodItemEntity>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun toJson(list: List<FoodItemEntity>?): String {
        return Gson().toJson(list)
    }
}