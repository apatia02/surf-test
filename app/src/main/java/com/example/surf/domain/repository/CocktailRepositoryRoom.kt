package com.example.surf.domain.repository

import com.example.surf.domain.entity.Cocktail
import kotlinx.coroutines.flow.Flow

interface CocktailRepositoryRoom {

    fun getCocktails(): Flow<List<Cocktail>>

    suspend fun newCocktail(cocktail: Cocktail)

    suspend fun deleteCocktail(cocktail: Cocktail)

    suspend fun editCocktail(cocktail: Cocktail)
}