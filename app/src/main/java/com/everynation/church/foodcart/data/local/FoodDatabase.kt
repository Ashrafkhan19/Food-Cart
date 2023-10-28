package com.everynation.church.foodcart.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.everynation.church.foodcart.data.ShoppingDataModel
import com.everynation.church.foodcart.data.mapper.toEntity
import com.everynation.church.foodcart.data.shoppingData
import com.google.gson.Gson

@Database(entities = [ FoodItemEntity::class, IsFirstTime::class], version = 1, exportSchema = false)
abstract class FoodDatabase() : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun foodItemDao(): FoodItemDao
    abstract fun isFirstTimeDao(): IsFirstTimeDao

    companion object {
        @Volatile
        private var INSTANCE: FoodDatabase? = null

        fun getDatabase(context: Context): FoodDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java,
                    "app_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }

        suspend fun populateDatabase(categoryDao: CategoryDao) {
            // Parse the JSON data and insert it into the database
            val jsonData = shoppingData

            val shoppingDataModel = Gson().fromJson(jsonData, ShoppingDataModel::class.java)

            for (category in shoppingDataModel.categories) {
                val categoryId = categoryDao.insertItems(category.items.map { it.toEntity(category.id,category.name) })
            }
        }
    }




}
