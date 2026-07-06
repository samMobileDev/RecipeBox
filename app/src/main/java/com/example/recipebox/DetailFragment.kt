package com.example.recipebox

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getColorStateList
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recipebox.databinding.FragmentDetailBinding
import com.example.recipebox.room.SavedRecipe
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class DetailFragment: Fragment(R.layout.fragment_detail) {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val recipeId by lazy {
        arguments?.getInt("id")
    }
    lateinit var recipe: RecipeDetail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDetailBinding.bind(view)

        val dao = RecipeDatabase.getDatabase(requireContext()).recipeDao()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(RecipeService::class.java)



        //check if recipe is saved
        lifecycleScope.launch {

            val id = recipeId ?: return@launch

            if (dao.isSaved(id)) {
                binding.btnSave.setIconResource(R.drawable.saved_icon_detail)
                binding.btnSave.iconTint = getColorStateList(requireContext(), R.color.black)
                binding.btnSave.iconGravity = MaterialButton.ICON_GRAVITY_TEXT_START
            } else {
                binding.btnSave.setIconResource(R.drawable.save_icon_detail)
            }
        }


//lifecycleScope to get more information from table and demonstrate it to xml by id from bundle

        lifecycleScope.launch {
            try {
                val response = api.getRecipeInformation(
                    id = recipeId!!,
                    apiKey = "3c0a9073e9394949a2ace3f0e1a5b069"
                )
                recipe = response


                binding.recipeTitle.text = response.title
                binding.recipeTime.text = "${response.readyInMinutes} minutes"
                binding.recipeInstructions.text =
                    response.instructions ?: "No instructions available"

                Glide.with(requireContext())
                    .load(response.image)
                    .placeholder(R.drawable.salad_png_def)
                    .error(R.drawable.salad_png_def)
                    .fallback(R.drawable.salad_png_def)
                    .centerCrop()
                    .into(binding.recipeImage)


            } catch (e: IOException) {
                binding.wholeFrame.visibility = View.GONE
                binding.wholeLayout.visibility = View.GONE
                binding.errorLayout.visibility = View.VISIBLE
            }catch (e: HttpException){
                binding.wholeFrame.visibility = View.GONE
                binding.wholeLayout.visibility = View.GONE
                binding.errorLayout.visibility = View.VISIBLE

                when (e.code()) {

                    401 -> {
                        binding.errorText.text = "API Key is invalid"
                    }

                    402 -> {
                        binding.errorText.text = "Daily limit reached"
                    }

                    429 -> {
                        binding.errorText.text = "Too many requests. Try again later"
                    }

                    500 -> {
                        binding.errorText.text = "Internal server error"
                    }

                    else -> {
                        binding.errorText.text = "Unknown error"
                    }
                }
            }
        }


//room to save recipe
        binding.btnSave.setOnClickListener {
            lifecycleScope.launch {

                val id = recipeId ?: return@launch

                val isSaved = dao.isSaved(id)


                if (isSaved) {
                    dao.deleteRecipeById(id)
                    binding.btnSave.setIconResource(R.drawable.save_icon_detail)

                } else {
                    val savedRecipe = SavedRecipe(
                        id = recipe.id,
                        title = recipe.title,
                        image = recipe.image,
                        readyInMinutes = recipe.readyInMinutes,
                        ingredients = recipe.extendedIngredients.joinToString("\n") { it.original },
                        instructions = recipe.instructions ?: ""
                    )

                    dao.insertRecipe(savedRecipe)
                    binding.btnSave.setIconResource(R.drawable.saved_icon_detail)
                    binding.btnSave.iconTint = getColorStateList(requireContext(), R.color.black)
                    binding.btnSave.iconGravity = MaterialButton.ICON_GRAVITY_TEXT_START
                }
            }
        }

        //ingredients that we got from table and put to item

        lifecycleScope.launch {
            try {

                val response = api.getRecipeInformation(
                    id = recipeId!!,
                    apiKey = "3c0a9073e9394949a2ace3f0e1a5b069"
                )

                binding.ingredientsRecycler.layoutManager =
                    LinearLayoutManager(requireContext())

                binding.ingredientsRecycler.adapter =
                    DetailIngredientAdapter(response.extendedIngredients)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        //back button
        binding.btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()}






    }
}