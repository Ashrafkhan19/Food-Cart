package com.everynation.church.foodcart.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<FoodItemEntity>)

    @Query("SELECT * FROM fooditementity")
    fun getAllItems(): Flow<List<FoodItemEntity>>

    @Update
    suspend fun updateItems(item: FoodItemEntity)

    @Query("SELECT * FROM fooditementity WHERE isFav=:isFav")
    fun getAllItemsWithFav(isFav: Boolean = true): Flow<List<FoodItemEntity>>



    // You can add more queries or methods based on your requirements
}

@Dao
interface FoodItemDao {




    // You can add more queries or methods based on your requirements
}

@Dao
interface IsFirstTimeDao{
    @Insert
    fun insert(value: IsFirstTime): Long
    @Query("SELECT * FROM isfirsttime")
    fun getIsFirstTime(): IsFirstTime?

}