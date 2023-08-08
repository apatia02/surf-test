package com.example.surf.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.surf.data.converter.Converters

@Database(entities = [CocktailDto::class], version = 1)
@TypeConverters(Converters::class)
abstract class CocktailAppDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
}