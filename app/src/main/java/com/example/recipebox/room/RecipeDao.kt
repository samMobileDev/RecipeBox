package com.example.recipebox.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: SavedRecipe)

    @Delete
    suspend fun deleteRecipe(recipe: SavedRecipe)

    @Query("SELECT EXISTS(SELECT 1 FROM saved_recipes WHERE id = :recipeId)")
    suspend fun isSaved(recipeId: Int): Boolean

    @Query("DELETE FROM saved_recipes WHERE id = :id")
    suspend fun deleteRecipeById(id: Int)

    @Query("SELECT * FROM saved_recipes")
    suspend fun getAllRecipes(): List<SavedRecipe>
}