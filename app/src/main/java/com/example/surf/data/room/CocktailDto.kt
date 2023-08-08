package com.example.surf.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cocktails")
data class CocktailDto (
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    val ingredients: List<String>,
    val recipe: String
)