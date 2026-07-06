package com.example.recipebox

data class Recipe(
    val id: Int,
    val title: String,
    val image: String,
    val readyInMinutes: Int
)

data class RecipeResponse(
    val results: List<Recipe>
)


data class Ingredient(
    val original: String,
    val image: String,
    val name: String
)

data class Ingredients(
    val name: String,
    val icon: Int
)



data class RecipeDetail(
    val id: Int,
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    val extendedIngredients: List<Ingredient>,
    val instructions: String?
)

