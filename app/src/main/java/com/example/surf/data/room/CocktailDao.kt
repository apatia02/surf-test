package com.example.surf.data.room

import androidx.room.*
import androidx.room.OnConflictStrategy.Companion.IGNORE
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {

    @Query("SELECT * FROM cocktails")
    fun getCocktails(): Flow<List<CocktailDto>>

    @Insert(onConflict = IGNORE)
    suspend fun newCocktail(cocktailDto: CocktailDto)

    @Delete
    suspend fun deleteCocktail(cocktailDto: CocktailDto)

    @Update
    suspend fun editCocktail(cocktailDto: CocktailDto)

}