package com.example.surf.data.repository

import com.example.surf.data.room.CocktailDao
import com.example.surf.data.room.CocktailDto
import com.example.surf.domain.entity.Cocktail
import com.example.surf.domain.repository.CocktailRepositoryRoom
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class CocktailRepositoryRoomImpl @Inject constructor(private val cocktailDao: CocktailDao) :
    CocktailRepositoryRoom {
    override fun getCocktails(): Flow<List<Cocktail>> {
        return cocktailDao.getCocktails().map {
            it.map { it1 ->
                Cocktail(
                    it1.id,
                    it1.name,
                    it1.description,
                    it1.ingredients,
                    it1.recipe
                )
            }
        }
    }

    override suspend fun newCocktail(cocktail: Cocktail) {
        runBlocking {
            launch {
                cocktailDao.newCocktail(
                    CocktailDto(
                        cocktail.id,
                        cocktail.name,
                        cocktail.description,
                        cocktail.ingredients,
                        cocktail.recipe
                    )
                )
            }
        }
    }

    override suspend fun deleteCocktail(cocktail: Cocktail) {
        runBlocking {
            launch {
                cocktailDao.deleteCocktail(
                    CocktailDto(
                        cocktail.id,
                        cocktail.name,
                        cocktail.description,
                        cocktail.ingredients,
                        cocktail.recipe
                    )
                )
            }
        }
    }

    override suspend fun editCocktail(cocktail: Cocktail) {
        runBlocking {
            launch {
                cocktailDao.editCocktail(
                    CocktailDto(
                        cocktail.id,
                        cocktail.name,
                        cocktail.description,
                        cocktail.ingredients,
                        cocktail.recipe
                    )
                )
            }
        }
    }

}