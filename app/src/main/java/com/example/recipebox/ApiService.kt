package com.example.recipebox
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface RecipeService {

    @GET("recipes/complexSearch")
    suspend fun searchRecipes(
        @Query("query") query: String,
        @Query("number") number: Int = 20,
        @Query("apiKey") apiKey: String,
        @Query("maxReadyTime") maxReadyTime: Int? = null,
        @Query("addRecipeInformation") addRecipeInformation: Boolean = true
    ): RecipeResponse


    @GET("recipes/complexSearch")
    suspend fun getRecommendedRecipes(
        @Query("number") number: Int = 20,
        @Query("sort") sort: String = "popularity",
        @Query("apiKey") apiKey: String,
        @Query("addRecipeInformation") addRecipeInformation: Boolean = true
    ): RecipeResponse


    @GET("recipes/findByIngredients")
    suspend fun findRecipesByIngredients(
        @Query("ingredients") ingredients: String,
        @Query("number") number: Int = 20,
        @Query("apiKey") apiKey: String,
        @Query("addRecipeInformation") addRecipeInformation: Boolean = true
    ): List<Recipe>


    @GET("recipes/{id}/information")
    suspend fun getRecipeInformation(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String,
        @Query("addRecipeInformation") addRecipeInformation: Boolean = true
    ): RecipeDetail

}
