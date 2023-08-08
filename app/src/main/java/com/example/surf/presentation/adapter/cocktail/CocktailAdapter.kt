package com.example.surf.presentation.adapter.cocktail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.surf.R
import com.example.surf.databinding.ItemCocktailBinding
import com.example.surf.domain.entity.Cocktail

class CocktailAdapter(private val onClick: (Int) -> Unit ) :
    ListAdapter<Cocktail, CocktailHolder>(
        CocktailDiffUtilCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailHolder {
        val item = ItemCocktailBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return CocktailHolder(item)
    }

    override fun onBindViewHolder(holder: CocktailHolder, position: Int) {
        val item = getItem(position)
        holder.itemCocktailBinding.cocktailImg.setImageResource(R.drawable.place_holder)
        holder.itemCocktailBinding.cocktailNameTv.text = item.name
        holder.itemCocktailBinding.cocktail.setOnClickListener { onClick(item.id) }
    }
}