package com.example.surf.presentation.adapter.ingredient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.surf.databinding.ItemIngredientDetailBinding


class IngredientDetailAdapter(private val size: Int) :
    ListAdapter<String, IngredientDetailHolder>(IngredientDetailDiffUtilCallback()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientDetailHolder {
        val item = ItemIngredientDetailBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientDetailHolder(item)
    }

    override fun onBindViewHolder(holder: IngredientDetailHolder, position: Int) {
        val item = getItem(position)
        holder.itemIngredientDetailBinding.ingredientTv.text = item
        if (size - 1 == position)
            holder.itemIngredientDetailBinding.ingredientLineImg.isGone = true
    }
}

class IngredientDetailDiffUtilCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}

class IngredientDetailHolder(val itemIngredientDetailBinding: ItemIngredientDetailBinding) :
    RecyclerView.ViewHolder(itemIngredientDetailBinding.root)