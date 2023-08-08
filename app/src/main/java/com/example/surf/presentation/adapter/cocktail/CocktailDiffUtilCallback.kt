package com.example.surf.presentation.adapter.cocktail

import androidx.recyclerview.widget.DiffUtil
import com.example.surf.domain.entity.Cocktail

class CocktailDiffUtilCallback : DiffUtil.ItemCallback<Cocktail>() {
    override fun areItemsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Cocktail, newItem: Cocktail): Boolean {
        return oldItem == newItem
    }
}