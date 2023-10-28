package com.everynation.church.foodcart.di

import android.content.Context
import androidx.room.Room
import com.everynation.church.foodcart.data.local.CategoryDao
import com.everynation.church.foodcart.data.local.FoodDatabase
import com.everynation.church.foodcart.data.local.FoodItemDao
import com.everynation.church.foodcart.data.local.IsFirstTimeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): FoodDatabase {
        return Room.databaseBuilder(
            context,
            FoodDatabase::class.java,
            "app_database"
        )
            .build()
    }

    @Provides
    fun provideCategoryDao(database: FoodDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    fun provideFoodItemDao(database: FoodDatabase): FoodItemDao {
        return database.foodItemDao()
    }

    @Provides
    fun provideIsFirstTimeDao(database: FoodDatabase): IsFirstTimeDao {
        return database.isFirstTimeDao()
    }


}
