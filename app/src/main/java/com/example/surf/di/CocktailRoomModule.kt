package com.example.surf.di

import android.content.Context
import androidx.room.Room
import com.example.surf.data.room.CocktailAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CocktailRoomModule{

    @Singleton
    @Provides
    fun provideCocktailAppDatabase(@ApplicationContext context: Context) = Room
        .databaseBuilder(context, CocktailAppDatabase::class.java, "cocktails")
        .build()

    @Singleton
    @Provides
    fun provideCocktailDao(db: CocktailAppDatabase) = db.cocktailDao()
}