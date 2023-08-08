package com.example.surf.presentation.adapter.ingredient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.surf.databinding.ItemIngredientEditBinding
import com.example.surf.presentation.viewmodel.CocktailEditViewModel


class IngredientEditAdapter(private val viewModel: CocktailEditViewModel) :
    ListAdapter<String, IngredientEditHolder>(IngredientEditDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientEditHolder {
        val item = ItemIngredientEditBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientEditHolder(item)
    }

    override fun onBindViewHolder(holder: IngredientEditHolder, position: Int) {
        val item = getItem(position)
        with(holder.itemIngredientEditBinding) {
            ingredientEt.setText(item)
            deleteBtn.setOnClickListener {
                viewModel.deleteIngredient(position)
            }
        }
    }
}

class IngredientEditDiffUtilCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

class IngredientEditHolder(val itemIngredientEditBinding: ItemIngredientEditBinding) :
    RecyclerView.ViewHolder(itemIngredientEditBinding.root)