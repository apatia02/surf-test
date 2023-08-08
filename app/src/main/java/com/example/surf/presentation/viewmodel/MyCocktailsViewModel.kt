package com.example.surf.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surf.domain.repository.CocktailRepositoryRoom
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyCocktailsViewModel @Inject constructor(cocktailRepositoryRoom: CocktailRepositoryRoom) :
    ViewModel() {
     val cocktails =
        cocktailRepositoryRoom.getCocktails().stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L), emptyList()
        )

}