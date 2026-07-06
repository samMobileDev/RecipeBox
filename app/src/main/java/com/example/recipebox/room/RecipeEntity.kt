package com.example.recipebox.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_recipes")
data class SavedRecipe(

    @PrimaryKey
    val id: Int,

    val title: String,

    val image: String,

    val readyInMinutes: Int,

    val ingredients: String,

    val instructions: String
)