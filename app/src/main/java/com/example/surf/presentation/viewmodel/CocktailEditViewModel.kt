package com.example.surf.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surf.domain.entity.Cocktail
import com.example.surf.domain.repository.CocktailRepositoryRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailEditViewModel @Inject constructor(private val cocktailRepositoryRoom: CocktailRepositoryRoom) :
    ViewModel() {
    val cocktails =
        cocktailRepositoryRoom.getCocktails().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L), emptyList()
        )
    var ingredients = mutableListOf<String>()
    private var _ingredientsChange = MutableStateFlow(false)
    val ingredientsChange = _ingredientsChange.asStateFlow()

    fun deleteIngredient(index: Int) {
        ingredients.removeAt(index)
        _ingredientsChange.value = !_ingredientsChange.value


    }
    fun changeAllIngredients(ingredients:List<String>){
        this.ingredients =  ingredients.toMutableList()
        _ingredientsChange.value = !_ingredientsChange.value
    }


    fun addIngredient(ingredient:String) {
        ingredients.add(ingredient)
        _ingredientsChange.value = !_ingredientsChange.value
    }
    fun updateCocktail(cocktail: Cocktail){
        viewModelScope.launch {
            cocktailRepositoryRoom.editCocktail(cocktail)
        }
    }
    fun deleteCocktail(cocktail: Cocktail){
        viewModelScope.launch {
            cocktailRepositoryRoom.deleteCocktail(cocktail)
        }
    }
    fun addCocktail(cocktail: Cocktail){
        viewModelScope.launch {
            cocktailRepositoryRoom.newCocktail(cocktail)
        }
    }
}